package com.example.wspinomierz.ui.calc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalcViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalcViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kalkulator skal wspinaczkowych");
    }

    public LiveData<String> getText() {
        return mText;
    }
}