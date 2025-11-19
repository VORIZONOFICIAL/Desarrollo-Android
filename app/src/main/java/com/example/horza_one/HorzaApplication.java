package com.example.horza_one;

import android.app.Application;
import com.example.horza_one.data.api.RetrofitClient;

public class HorzaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializar Retrofit Client
        RetrofitClient.initialize(this);
    }
}