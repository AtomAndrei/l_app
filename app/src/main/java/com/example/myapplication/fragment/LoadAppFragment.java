package com.example.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG = "LoadAppFragment";
    private NavController navController;
    private FragmentController fragmentController;

    public LoadAppFragment() {
        super(R.layout.fragment_load_app);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called.");

        progressBar = view.findViewById(R.id.progressBar);
        navController = Navigation.findNavController(view);
        fragmentController = new FragmentController(navController);

        // Obținem ViewModel-ul
        viewModel = new ViewModelProvider(this).get(LoadAppViewModel.class);
        Log.d(TAG, "ViewModel obtained.");

        // Resetăm progresul pentru a începe de la 0
        resetProgress();

        // Observăm progresul și actualizăm bara de progres
        viewModel.getProgress().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer progress) {
                if (progress != null) {
                    Log.d(TAG, "Progress updated in fragment: " + progress + "%");
                    progressBar.setProgress(progress);
                }
            }
        });

        // Observăm evenimentele de navigare
        viewModel.getNavigationEvent().observe(getViewLifecycleOwner(), new Observer<Event<NavigationTarget>>() {
            @Override
            public void onChanged(Event<NavigationTarget> event) {
                if (event != null) {
                    NavigationTarget target = event.getContentIfNotHandled();
                    if (target != null) {
                        Log.d(TAG, "Navigation event received in fragment: " + target.name());
                        try {
                            if (isAdded() && getActivity() != null && navController.getCurrentDestination() != null) {
                                Log.d(TAG, "Fragment is added and activity is not null. Proceeding with navigation.");
                                fragmentController.handleNavigation(target);
                            } else {
                                Log.w(TAG, "Fragment not added, activity is null, or current destination is null. Cannot navigate.");
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Failed to navigate to " + target.name(), e);
                        }
                    }
                }
            }
        });
    }

    // Funcția pentru a reseta progresul
    private void resetProgress() {
        Log.d(TAG, "Resetting progress to 0 and restarting load.");
        viewModel.resetProgress();  // În ViewModel adăugăm o metodă care resetează progresul la 0 și repornește procesul
    }
}
