package com.example.legiaakademia.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.legiaakademia.api.ApiClient;
import com.example.legiaakademia.utils.SharedPrefsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicjalizuj ApiClient
        ApiClient.init(this);

        // Sprawdź czy użytkownik jest zalogowany
        SharedPrefsManager prefsManager = new SharedPrefsManager(this);

        Intent intent;
        if (prefsManager.isLoggedIn()) {
            // Przekieruj do Dashboard
            intent = new Intent(this, DashboardActivity.class);
        } else {
            // Przekieruj do logowania
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}