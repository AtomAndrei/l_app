package com.example.myapplication.fragment;

import androidx.navigation.NavController;
import android.util.Log;
import com.example.myapplication.NavigationTarget;
import com.example.myapplication.R;

public class FragmentController {

    private static final String TAG = "FragmentController";
    private final NavController navController;

    public FragmentController(NavController navController) {
        this.navController = navController;
    }

    public void handleNavigation(NavigationTarget target) {
        if (navController == null) {
            Log.e(TAG, "NavController is null, cannot navigate.");
            return;
        }

        try {
            switch (target) {
                case STATUS_APP_FRAGMENT:
                    Log.d(TAG, "Navigating to STATUS_APP_FRAGMENT.");
                    navController.navigate(R.id.action_loadAppFragment_to_statusAppFragment);
                    break;
                case LOAD_APP_FRAGMENT:
                    Log.d(TAG, "Navigating to LOAD_APP_FRAGMENT.");
                    // Curățăm stiva înainte de navigare, asigurându-ne că nu sunt conflicte
                    navController.popBackStack(R.id.loadAppFragment, true);
                    navController.navigate(R.id.action_statusAppFragment_to_loadAppFragment);
                    break;
                case LOGIN_FRAGMENT:
                    Log.d(TAG, "Navigating to LOGIN_FRAGMENT.");
                    if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.loadAppFragment) {
                        navController.navigate(R.id.action_loadAppFragment_to_loginFragment);
                    } else {
                        Log.e(TAG, "Current destination is null or not LoadAppFragment. Cannot navigate to LOGIN_FRAGMENT.");
                    }
                    break;
                default:
                    Log.w(TAG, "Unknown navigation target: " + target.name());
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error navigating to " + target.name(), e);
        }
    }
}
