package com.example.legiaakademia.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legiaakademia.R;
import com.example.legiaakademia.adapters.DzieckoAdapter;
import com.example.legiaakademia.api.ApiClient;
import com.example.legiaakademia.models.*;
import com.example.legiaakademia.models.RequestModels.*;
import com.example.legiaakademia.utils.SharedPrefsManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvUserName;
    private Button btnLogout, btnAddChild;
    private RecyclerView rvDzieci;
    private ProgressBar progressBar;
    private TextView tvNoDzieci;
    private LinearLayout llCategories;

    private SharedPrefsManager prefsManager;
    private AuthResponse user;
    private DzieckoAdapter adapter;
    private List<Dziecko> dzieciList = new ArrayList<>();
    private List<Kategoria> kategorieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        prefsManager = new SharedPrefsManager(this);
        user = prefsManager.getUser();

        if (user == null) {
            redirectToLogin();
            return;
        }

        initViews();
        setupRecyclerView();
        setupListeners();
        loadData();
    }

    private void initViews() {
        tvUserName = findViewById(R.id.tvUserName);
        btnLogout = findViewById(R.id.btnLogout);
        btnAddChild = findViewById(R.id.btnAddChild);
        rvDzieci = findViewById(R.id.rvDzieci);
        progressBar = findViewById(R.id.progressBar);
        tvNoDzieci = findViewById(R.id.tvNoDzieci);
        llCategories = findViewById(R.id.llCategories);

        tvUserName.setText(user.getImie() + " " + user.getNazwisko());
    }

    private void setupRecyclerView() {
        adapter = new DzieckoAdapter(this, dzieciList, kategorieList,
                new DzieckoAdapter.OnDzieckoActionListener() {
                    @Override
                    public void onDelete(Dziecko dziecko) {
                        confirmDelete(dziecko);
                    }

                    @Override
                    public void onCategoryChange(Dziecko dziecko, Long kategoriaId) {
                        changeDzieckoCategory(dziecko.getId(), kategoriaId);
                    }
                });

        rvDzieci.setLayoutManager(new LinearLayoutManager(this));
        rvDzieci.setAdapter(adapter);
    }

    private void setupListeners() {
        btnLogout.setOnClickListener(v -> logout());
        btnAddChild.setOnClickListener(v -> showAddChildDialog());
    }

    private void loadData() {
        setLoading(true);

        // Pobierz dzieci
        ApiClient.getApiService().getDzieciRodzica(user.getRodzicId())
                .enqueue(new Callback<List<Dziecko>>() {
                    @Override
                    public void onResponse(Call<List<Dziecko>> call, Response<List<Dziecko>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            dzieciList.clear();
                            dzieciList.addAll(response.body());
                            updateUI();
                        }
                        loadKategorie();
                    }

                    @Override
                    public void onFailure(Call<List<Dziecko>> call, Throwable t) {
                        setLoading(false);
                        Toast.makeText(DashboardActivity.this, R.string.error_loading_data,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadKategorie() {
        ApiClient.getApiService().getKategorie().enqueue(new Callback<List<Kategoria>>() {
            @Override
            public void onResponse(Call<List<Kategoria>> call, Response<List<Kategoria>> response) {
                setLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    kategorieList.clear();
                    kategorieList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    displayCategories();
                }
            }

            @Override
            public void onFailure(Call<List<Kategoria>> call, Throwable t) {
                setLoading(false);
                Toast.makeText(DashboardActivity.this, R.string.error_loading_data,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddChildDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_dziecko, null);

        EditText etImie = dialogView.findViewById(R.id.etImie);
        EditText etNazwisko = dialogView.findViewById(R.id.etNazwisko);
        EditText etDataUrodzenia = dialogView.findViewById(R.id.etDataUrodzenia);
        EditText etPesel = dialogView.findViewById(R.id.etPesel);
        Spinner spinnerKategoria = dialogView.findViewById(R.id.spinnerKategoria);

        // Setup date picker
        etDataUrodzenia.setOnClickListener(v -> showDatePicker(etDataUrodzenia));

        // Setup kategorie spinner
        List<String> kategorieNames = new ArrayList<>();
        kategorieNames.add(getString(R.string.select_category_prompt));
        for (Kategoria k : kategorieList) {
            kategorieNames.add(k.getNazwa());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, kategorieNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategoria.setAdapter(spinnerAdapter);

        new AlertDialog.Builder(this)
                .setTitle(R.string.add_child_title)
                .setView(dialogView)
                .setPositiveButton(R.string.add, (dialog, which) -> {
                    String imie = etImie.getText().toString().trim();
                    String nazwisko = etNazwisko.getText().toString().trim();
                    String dataUrodzenia = etDataUrodzenia.getText().toString().trim();
                    String pesel = etPesel.getText().toString().trim();

                    int kategoriaPos = spinnerKategoria.getSelectedItemPosition();
                    Long kategoriaId = null;
                    if (kategoriaPos > 0) {
                        kategoriaId = kategorieList.get(kategoriaPos - 1).getId();
                    }

                    if (imie.isEmpty() || nazwisko.isEmpty() || dataUrodzenia.isEmpty() || pesel.isEmpty()) {
                        Toast.makeText(this, R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    addDziecko(imie, nazwisko, dataUrodzenia, pesel, kategoriaId);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void showDatePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, y, m, d) -> {
                    String date = String.format("%04d-%02d-%02d", y, m + 1, d);
                    editText.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void addDziecko(String imie, String nazwisko, String dataUrodzenia,
                            String pesel, Long kategoriaId) {
        DzieckoRequest request = new DzieckoRequest(imie, nazwisko, dataUrodzenia,
                pesel, kategoriaId);

        ApiClient.getApiService().dodajDziecko(user.getRodzicId(), request)
                .enqueue(new Callback<Dziecko>() {
                    @Override
                    public void onResponse(Call<Dziecko> call, Response<Dziecko> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DashboardActivity.this, R.string.success_child_added,
                                    Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(DashboardActivity.this, R.string.error_adding_child,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Dziecko> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this, R.string.error_network,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void confirmDelete(Dziecko dziecko) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteDziecko(dziecko))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void deleteDziecko(Dziecko dziecko) {
        ApiClient.getApiService().usunDziecko(dziecko.getId(), user.getRodzicId())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DashboardActivity.this, R.string.success_child_deleted,
                                    Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(DashboardActivity.this, R.string.error_deleting_child,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this, R.string.error_network,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void changeDzieckoCategory(Long dzieckoId, Long kategoriaId) {
        if (kategoriaId == null || kategoriaId == 0) return;

        ApiClient.getApiService().przypiszKategorie(dzieckoId, kategoriaId, user.getRodzicId())
                .enqueue(new Callback<Dziecko>() {
                    @Override
                    public void onResponse(Call<Dziecko> call, Response<Dziecko> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DashboardActivity.this, R.string.success_category_changed,
                                    Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(DashboardActivity.this, R.string.error_changing_category,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Dziecko> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this, R.string.error_network,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayCategories() {
        llCategories.removeAllViews();
        for (Kategoria kategoria : kategorieList) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_kategoria, llCategories, false);

            TextView tvNazwa = itemView.findViewById(R.id.tvKategoriaNazwa);
            TextView tvRoczniki = itemView.findViewById(R.id.tvKategoriaRoczniki);
            TextView tvOpis = itemView.findViewById(R.id.tvKategoriaOpis);

            tvNazwa.setText(kategoria.getNazwa());
            tvRoczniki.setText(kategoria.getRokOd() + " - " + kategoria.getRokDo());
            tvOpis.setText(kategoria.getOpis());

            llCategories.addView(itemView);
        }
    }

    private void updateUI() {
        if (dzieciList.isEmpty()) {
            tvNoDzieci.setVisibility(View.VISIBLE);
            rvDzieci.setVisibility(View.GONE);
        } else {
            tvNoDzieci.setVisibility(View.GONE);
            rvDzieci.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    private void logout() {
        prefsManager.clearAuthData();
        redirectToLogin();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}