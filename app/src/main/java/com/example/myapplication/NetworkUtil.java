package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

/**
 * Utility class for checking network connectivity status.
 */
public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    /**
     * Checks if the device is connected to the internet via Wi-Fi or Cellular.
     *
     * @param context the application context.
     * @return true if connected to the internet, false otherwise.
     */
    public static boolean isConnectedToInternet(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                Network activeNetwork = connectivityManager.getActiveNetwork();
                if (activeNetwork == null) {
                    Log.w(TAG, "No active network found");
                    return false;
                }

                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                if (networkCapabilities == null) {
                    Log.w(TAG, "No network capabilities found");
                    return false;
                }

                boolean isConnected = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);

                Log.d(TAG, "Internet connection status: " + isConnected);
                return isConnected;
            } else {
                Log.e(TAG, "ConnectivityManager is null. Cannot check network status.");
                return false;
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception occurred while checking internet connection", e);
            return false;
        }
    }
}
