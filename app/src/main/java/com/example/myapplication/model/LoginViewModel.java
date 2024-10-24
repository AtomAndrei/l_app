package com.example.myapplication.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login(String email, String password) {
        Log.d(TAG, "Attempting to login with email: " + email);

        // Simulare proces de autentificare
        try {
            if (email.equals("test@example.com") && password.equals("password")) {
                Log.i(TAG, "Login successful");
                // Navigare sau actualizare UI cu succes
            } else {
                Log.w(TAG, "Login failed: Invalid credentials");
                // Afișăm un mesaj de eroare către utilizator
            }
        } catch (Exception e) {
            Log.e(TAG, "Login exception occurred", e);
        }
    }
}
