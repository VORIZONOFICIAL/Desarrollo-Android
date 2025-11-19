package com.example.horza_one.ui.ui_Personal.C_Per;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class C_Per_ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public C_Per_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento Configuracion Perfil");
    }

    public LiveData<String> getText(){return mText;}
}
