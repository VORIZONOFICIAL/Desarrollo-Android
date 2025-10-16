package com.example.horza_one.ui_Personal.Ev_Personal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Ev_Personal_ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public Ev_Personal_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento ajustes");
    }

    public LiveData<String> getText(){return mText;}
}
