package com.example.myapplication.model;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.myapplication.Event;
import com.example.myapplication.NavigationTarget;
import com.example.myapplication.NetworkUtil;

public class LoadAppViewModel extends AndroidViewModel {

    private static final String TAG = "LoadAppViewModel";
    private final MutableLiveData<Integer> progress = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();
    private final MutableLiveData<Event<NavigationTarget>> navigationEvent = new MutableLiveData<>();
    private boolean internetCheckDone = false;
    private boolean isProgressStarted = false;

    public LoadAppViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "ViewModel initialized.");
    }

    // Getter pentru progres
    public LiveData<Integer> getProgress() {
        return progress;
    }

    // Getter pentru evenimentele de navigare
    public LiveData<Event<NavigationTarget>> getNavigationEvent() {
        return navigationEvent;
    }

    // Funcție pentru a reseta progresul și a reporni progresul de la 0
    public void resetProgress() {
        Log.d(TAG, "Progress reset called. Restarting progress simulation from 0.");
        isProgressStarted = false;
        internetCheckDone = false;
        startProgressAndCheckInternet();
    }

    // Începe progresul și verifică conexiunea la internet la 80%
    private void startProgressAndCheckInternet() {
        if (isProgressStarted) return; // Prevenim repornirea multiplă a progresului
        isProgressStarted = true;

        new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                try {
                    // Simulare progres
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Log.e(TAG, "Progress simulation interrupted", e);
                }

                // Actualizare progres
                progress.postValue(i);
                Log.d(TAG, "Progress: " + i + "%");

                // La 80% verificăm conexiunea la internet
                if (i == 80 && !internetCheckDone) {
                    internetCheckDone = true;
                    checkInternet();  // Verificăm conexiunea la internet
                    break;
                }
            }
        }).start();
    }

    // Verifică conexiunea la internet
    private void checkInternet() {
        Log.d(TAG, "Checking internet connectivity...");
        boolean connected = NetworkUtil.isConnectedToInternet(getApplication());

        Log.i(TAG, "Internet connection: " + (connected ? "Connected" : "Not Connected"));
        isConnected.postValue(connected);

        if (connected) {
            Log.d(TAG, "Internet connected. Continuing progress...");
            continueProgress();
        } else {
            Log.w(TAG, "No internet. Navigating to StatusAppFragment.");
            navigateTo(NavigationTarget.STATUS_APP_FRAGMENT);
        }
    }

    // Continuă progresul după verificarea internetului
    private void continueProgress() {
        new Thread(() -> {
            for (int i = 81; i <= 100; i++) {
                try {
                    Thread.sleep(10);  // Continuăm progresul
                } catch (InterruptedException e) {
                    Log.e(TAG, "Progress continuation interrupted", e);
                }
                progress.postValue(i);  // Actualizare progres
            }

            try {
                Thread.sleep(2000);  // Așteptăm 2 secunde înainte de a naviga
            } catch (InterruptedException e) {
                Log.e(TAG, "Waiting interrupted", e);
            }

            // Navigăm la LoginFragment după ce progresul ajunge la 100%
            navigateTo(NavigationTarget.LOGIN_FRAGMENT);
        }).start();
    }

    // Navighează către fragmentul țintă
    private void navigateTo(NavigationTarget target) {
        if (navigationEvent.getValue() == null || navigationEvent.getValue().getContentIfNotHandled() != target) {
            Log.d(TAG, "Navigating to: " + target.name());
            navigationEvent.postValue(new Event<>(target));
        }
    }
}
