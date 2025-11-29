package com.example.horza_one.ui_Admin.Asis_Acc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Asis_Acc_ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Asis_Acc_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento de Asistencia");
    }

    public LiveData<String> getText() {
        return mText;
    }
}