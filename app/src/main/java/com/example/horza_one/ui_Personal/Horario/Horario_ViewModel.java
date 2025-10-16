package com.example.horza_one.ui_Personal.Horario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Horario_ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Horario_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento ajustes");
    }

    public LiveData<String> getText(){return mText;}
}
