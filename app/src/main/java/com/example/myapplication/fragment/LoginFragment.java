package com.example.myapplication.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication.fragment.FragmentController;
import com.example.myapplication.NavigationTarget;
import com.example.myapplication.R;
import com.example.myapplication.model.LoginViewModel;

public class LoginFragment extends Fragment {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private LoginViewModel loginViewModel;
    private static final String TAG = "LoginFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "LoginFragment created");

        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        registerTextView = view.findViewById(R.id.registerTextView);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Restaurăm starea din Bundle dacă este necesar
        if (savedInstanceState != null) {
            emailEditText.setText(savedInstanceState.getString("email"));
            passwordEditText.setText(savedInstanceState.getString("password"));
        }

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Email and password are required", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Email or password is empty");
            } else {
                try {
                    loginViewModel.login(email, password);
                } catch (Exception e) {
                    Log.e(TAG, "Error during login", e);
                }
            }
        });

        registerTextView.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            FragmentController fragmentController = new FragmentController(navController);
            fragmentController.handleNavigation(NavigationTarget.HOME_FRAGMENT); // Navigate to home
            Log.d(TAG, "Navigating to HomeFragment");
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email", emailEditText.getText().toString());
        outState.putString("password", passwordEditText.getText().toString());
    }
}
