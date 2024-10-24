package com.example.myapplication.model;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.NetworkUtil;

public class StatusAppViewModel extends ViewModel {

    private final MutableLiveData<String> retryMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> internetAvailable = new MutableLiveData<>();
    private static final String TAG = "StatusAppViewModel";

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
        Log.d(TAG, "Checking internet connection...");
        boolean isConnected = NetworkUtil.isConnectedToInternet(context);  // Verificarea conexiunii

        if (isConnected) {
            // Setăm mesajul pentru retry și actualizăm starea de conectare
            Log.i(TAG, "Internet connection found. Updating UI and navigating.");
            retryMessage.setValue("Internet connection found, navigating...");
            internetAvailable.setValue(true);
        } else {
            // Dacă nu există conexiune, actualizăm mesajul și starea
            Log.w(TAG, "No internet connection. Prompting user to retry.");
            retryMessage.setValue("Still no internet connection, retrying...");
            internetAvailable.setValue(false);
        }
    }
}
