package com.example.wspinomierz.ui.addRoute;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class addRouteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public addRouteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}