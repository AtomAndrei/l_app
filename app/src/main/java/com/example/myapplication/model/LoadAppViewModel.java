package com.example.myapplication.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.myapplication.Event;
import com.example.myapplication.NavigationTarget;
import com.example.myapplication.NetworkUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadAppViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> progress = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();
    private final MutableLiveData<Event<NavigationTarget>> navigationEvent = new MutableLiveData<>();
    private final ExecutorService executorService;

    public LoadAppViewModel(@NonNull Application application) {
        super(application);
        executorService = Executors.newSingleThreadExecutor();
        startProgressAndCheckInternet();
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }

    public LiveData<Boolean> getIsConnected() {
        return isConnected;
    }

    public LiveData<Event<NavigationTarget>> getNavigationEvent() {
        return navigationEvent;
    }

    private void startProgressAndCheckInternet() {
        executorService.submit(() -> {
            // Simulează progresul până la 80%
            for (int i = 0; i <= 80; i++) {
                try {
                    Thread.sleep(40); // Simulare progres
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress.postValue(i);
            }
            // Verificăm conexiunea la internet
            checkInternet();
        });
    }

    private void checkInternet() {
        executorService.submit(() -> {
            boolean connected = NetworkUtil.isConnectedToInternet(getApplication());
            isConnected.postValue(connected);

            if (connected) {
                navigationEvent.postValue(new Event<>(NavigationTarget.HOME_FRAGMENT));
            } else {
                navigationEvent.postValue(new Event<>(NavigationTarget.STATUS_APP_FRAGMENT));
            }
        });
    }
}




