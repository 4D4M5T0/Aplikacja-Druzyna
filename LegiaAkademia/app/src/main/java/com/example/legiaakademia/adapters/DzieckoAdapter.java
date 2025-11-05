package com.example.legiaakademia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legiaakademia.R;
import com.example.legiaakademia.models.Dziecko;
import com.example.legiaakademia.models.Kategoria;

import java.util.ArrayList;
import java.util.List;

public class DzieckoAdapter extends RecyclerView.Adapter<DzieckoAdapter.DzieckoViewHolder> {

    private Context context;
    private List<Dziecko> dzieciList;
    private List<Kategoria> kategorieList;
    private OnDzieckoActionListener listener;

    public interface OnDzieckoActionListener {
        void onDelete(Dziecko dziecko);
        void onCategoryChange(Dziecko dziecko, Long kategoriaId);
    }

    public DzieckoAdapter(Context context, List<Dziecko> dzieciList,
                          List<Kategoria> kategorieList, OnDzieckoActionListener listener) {
        this.context = context;
        this.dzieciList = dzieciList;
        this.kategorieList = kategorieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DzieckoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dziecko, parent, false);
        return new DzieckoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DzieckoViewHolder holder, int position) {
        Dziecko dziecko = dzieciList.get(position);

        holder.tvName.setText(dziecko.getImie() + " " + dziecko.getNazwisko());
        holder.tvBirthDate.setText(context.getString(R.string.birth_date) + " " + dziecko.getDataUrodzenia());
        holder.tvPesel.setText(context.getString(R.string.pesel_label) + " " + dziecko.getPesel());

        // Setup kategorie spinner
        List<String> kategorieNames = new ArrayList<>();
        kategorieNames.add(context.getString(R.string.not_assigned));
        for (Kategoria k : kategorieList) {
            kategorieNames.add(k.getNazwa());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, kategorieNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerKategoria.setAdapter(adapter);

        // Set selected kategoria
        int selectedPos = 0;
        if (dziecko.getKategoriaId() != null) {
            for (int i = 0; i < kategorieList.size(); i++) {
                if (kategorieList.get(i).getId().equals(dziecko.getKategoriaId())) {
                    selectedPos = i + 1;
                    break;
                }
            }
        }
        holder.spinnerKategoria.setSelection(selectedPos);

        // Show assigned category
        if (dziecko.getKategoriaNazwa() != null && !dziecko.getKategoriaNazwa().isEmpty()) {
            holder.tvAssigned.setVisibility(View.VISIBLE);
            holder.tvAssigned.setText(context.getString(R.string.assigned_to) + " " + dziecko.getKategoriaNazwa());
        } else {
            holder.tvAssigned.setVisibility(View.GONE);
        }

        // Spinner listener
        holder.spinnerKategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    Long kategoriaId = kategorieList.get(pos - 1).getId();
                    if (!kategoriaId.equals(dziecko.getKategoriaId())) {
                        listener.onCategoryChange(dziecko, kategoriaId);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Delete button
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(dziecko));
    }

    @Override
    public int getItemCount() {
        return dzieciList.size();
    }

    static class DzieckoViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvBirthDate, tvPesel, tvAssigned;
        Spinner spinnerKategoria;
        Button btnDelete;

        public DzieckoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDzieckoName);
            tvBirthDate = itemView.findViewById(R.id.tvDzieckoDataUrodzenia);
            tvPesel = itemView.findViewById(R.id.tvDzieckoPesel);
            tvAssigned = itemView.findViewById(R.id.tvDzieckoAssigned);
            spinnerKategoria = itemView.findViewById(R.id.spinnerDzieckoKategoria);
            btnDelete = itemView.findViewById(R.id.btnDeleteDziecko);
        }
    }
}