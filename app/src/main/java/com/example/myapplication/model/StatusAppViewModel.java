package com.example.myapplication.model;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.NetworkUtil;

public class StatusAppViewModel extends ViewModel {

    private MutableLiveData<String> retryMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> internetAvailable = new MutableLiveData<>();

    // Getter pentru retryMessage
    public MutableLiveData<String> getRetryMessage() {
        return retryMessage;
    }

    // Getter pentru internetAvailable
    public MutableLiveData<Boolean> getInternetAvailable() {
        return internetAvailable;
    }

    // Metoda pentru verificarea conexiunii la internet
    public void checkInternetConnection(Context context) {
        boolean isConnected = NetworkUtil.isConnectedToInternet(context);  // Verificarea conexiunii
        if (isConnected) {
            retryMessage.setValue("Internet connection found, navigating...");
            internetAvailable.setValue(true);
        } else {
            retryMessage.setValue("Still no internet connection, retrying...");
            internetAvailable.setValue(false);
        }
    }
}
