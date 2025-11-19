package com.example.horza_one.ui.ui_Admin.Rep_Est;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Rep_Est_ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Rep_Est_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}