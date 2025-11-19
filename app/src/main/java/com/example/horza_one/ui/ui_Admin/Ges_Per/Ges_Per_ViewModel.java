package com.example.horza_one.ui.ui_Admin.Ges_Per;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Ges_Per_ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Ges_Per_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento de Gestion Personal");
    }

    public LiveData<String> getText() {
        return mText;
    }
}