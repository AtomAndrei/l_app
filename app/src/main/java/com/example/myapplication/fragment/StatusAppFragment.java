package com.example.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.myapplication.R;
import com.example.myapplication.model.StatusAppViewModel;

public class StatusAppFragment extends Fragment {

    private TextView statusTextView;
    private Button retryButton;
    private StatusAppViewModel viewModel;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status_app, container, false);
        statusTextView = view.findViewById(R.id.statusTextView);
        retryButton = view.findViewById(R.id.retryButton);

        // Inițializare ViewModel
        viewModel = new ViewModelProvider(this).get(StatusAppViewModel.class);

        // Observă mesajul de reîncercare
        viewModel.getRetryMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                statusTextView.setText(message);
            }
        });

        // Observă statusul conexiunii la internet
        viewModel.getInternetAvailable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {
                if (isConnected) {
                    // Navighează către LoadAppFragment dacă avem internet
                    NavController navController = Navigation.findNavController(requireView());
                    navController.navigate(R.id.action_statusAppFragment_to_loadAppFragment);
                } else {
                    retryButton.setEnabled(true);  // Reenable butonul dacă nu este internet
                }
            }
        });

        // Setare ClickListener pentru butonul de reîncercare
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryButton.setEnabled(false);
                // Apelează metoda din ViewModel pentru a verifica conexiunea la internet
                viewModel.checkInternetConnection(requireContext());  // Pasăm Context-ul aici
            }
        });

        return view;
    }
}
