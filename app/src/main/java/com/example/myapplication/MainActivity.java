package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instalează SplashScreen-ul sistemului
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        Log.d(TAG, "SplashScreen installed");

        // Setează layout-ul activității principale
        setContentView(R.layout.activity_main);

        // Obține NavHostFragment și NavController pentru a porni navigarea
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            Log.d(TAG, "Navigating to LoadAppFragment");
            navController.navigate(R.id.loadAppFragment); // Navighează către LoadAppFragment
        } else {
            Log.e(TAG, "NavHostFragment not found");
            throw new IllegalStateException("NavHostFragment not found");
        }
    }
}
