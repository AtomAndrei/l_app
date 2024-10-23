package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.myapplication.Event;
import com.example.myapplication.NavigationTarget;
import com.example.myapplication.R;
import com.example.myapplication.model.LoadAppViewModel;

public class LoadAppFragment extends Fragment {

    private ProgressBar progressBar;
    private LoadAppViewModel viewModel;

    public LoadAppFragment() {
        super(R.layout.fragment_load_app);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);

        // Obținem ViewModel-ul
        viewModel = new ViewModelProvider(this).get(LoadAppViewModel.class);

        // Observăm progresul și actualizăm bara de progres
        viewModel.getProgress().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer progress) {
                progressBar.setProgress(progress);
            }
        });

        // Observăm evenimentele de navigare
        viewModel.getNavigationEvent().observe(getViewLifecycleOwner(), new Observer<Event<NavigationTarget>>() {
            @Override
            public void onChanged(Event<NavigationTarget> event) {
                if (event != null) {
                    NavigationTarget target = event.getContentIfNotHandled();
                    if (target != null) {
                        NavController navController = Navigation.findNavController(view);
                        switch (target) {
                            case HOME_FRAGMENT:
                                navController.navigate(R.id.action_loadAppFragment_to_homeFragment);
                                break;
                            case STATUS_APP_FRAGMENT:
                                navController.navigate(R.id.action_loadAppFragment_to_statusAppFragment);
                                break;
                        }
                    }
                }
            }
        });
    }
}
