package com.example.legiaakademia.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.legiaakademia.models.AuthResponse;
import com.google.gson.Gson;

public class SharedPrefsManager {
    private static final String PREF_NAME = "LegiaAkademiaPrefs";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void saveAuthData(AuthResponse authResponse) {
        editor.putString(KEY_TOKEN, authResponse.getToken());
        editor.putString(KEY_USER, gson.toJson(authResponse));
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public AuthResponse getUser() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, AuthResponse.class);
        }
        return null;
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }

    public void clearAuthData() {
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_USER);
        editor.apply();
    }
}