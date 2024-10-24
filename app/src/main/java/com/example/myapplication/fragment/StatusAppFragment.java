package com.example.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication.NavigationTarget;
import com.example.myapplication.R;
import com.example.myapplication.model.StatusAppViewModel;

public class StatusAppFragment extends Fragment {

    private TextView statusTextView;
    private Button retryButton;
    private StatusAppViewModel viewModel;
    private static final String TAG = "StatusAppFragment";
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_app, container, false);

        // Inițializare NavController
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        statusTextView = view.findViewById(R.id.statusTextView);
        retryButton = view.findViewById(R.id.retryButton);

        // Inițializare ViewModel
        viewModel = new ViewModelProvider(this).get(StatusAppViewModel.class);
        Log.d(TAG, "ViewModel for StatusAppFragment initialized.");

        // Observă mesajul de reîncercare
        viewModel.getRetryMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Log.d(TAG, "Retry message updated: " + message);
                statusTextView.setText(message);
            }
        });

        // Observă statusul conexiunii la internet
        viewModel.getInternetAvailable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    Log.d(TAG, "Internet connection is available. Navigating to LoadAppFragment.");
                    try {
                        // Navighează la LoadAppFragment dacă există conexiune
                        navController.navigate(R.id.action_statusAppFragment_to_loadAppFragment);
                    } catch (Exception e) {
                        Log.e(TAG, "Error navigating to LoadAppFragment", e);
                    }
                } else {
                    Log.w(TAG, "No internet connection available.");
                    retryButton.setEnabled(true);  // Re-enable butonul dacă nu este internet
                }
            }
        });

        // Setare ClickListener pentru butonul de reîncercare
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Retry button clicked. Disabling button and checking internet connection.");
                retryButton.setEnabled(false);
                viewModel.checkInternetConnection(requireContext());
            }
        });

        return view;
    }
}
