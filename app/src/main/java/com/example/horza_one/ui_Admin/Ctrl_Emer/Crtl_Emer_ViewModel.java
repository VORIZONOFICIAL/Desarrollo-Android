package com.example.horza_one.ui_Admin.Ctrl_Emer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Crtl_Emer_ViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public Crtl_Emer_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el fragmento Control Emergencias");
    }

    public LiveData<String> getText(){return mText;}
}
