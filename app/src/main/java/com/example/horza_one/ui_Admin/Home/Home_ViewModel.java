package com.example.horza_one.ui_Admin.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Home_ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Home_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento Home de Administrador");
    }

    public LiveData<String> getText(){return mText;}
}
