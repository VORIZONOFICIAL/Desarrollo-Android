package com.example.horza_one.ui_Admin.Ajustes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Ajustes_ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public Ajustes_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento ajustes");
    }

    public LiveData<String> getText(){return mText;}
}
