package com.example.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> welcomeMessage = new MutableLiveData<>();

    public HomeViewModel() {
        welcomeMessage.setValue("Welcome to the Home Screen!");  // Mesajul de bun venit
    }

    public LiveData<String> getWelcomeMessage() {
        return welcomeMessage;
    }
}
