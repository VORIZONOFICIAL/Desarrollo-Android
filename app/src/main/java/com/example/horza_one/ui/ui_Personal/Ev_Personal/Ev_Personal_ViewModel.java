package com.example.horza_one.ui.ui_Personal.Ev_Personal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Ev_Personal_ViewModel extends ViewModel {

    private final MutableLiveData<Integer> porcentaje = new MutableLiveData<>(72);
    private final MutableLiveData<Integer> puntaje    = new MutableLiveData<>(15);

    private final MutableLiveData<float[]> metricas =
            new MutableLiveData<>(new float[]{3f, 15f, 2f, 5f});

    public LiveData<Integer> getPorcentaje() { return porcentaje; }
    public LiveData<Integer> getPuntaje()    { return puntaje; }
    public LiveData<float[]> getMetricas()   { return metricas; }

    public void setPorcentaje(int p) {
        if (p < 0) p = 0; if (p > 100) p = 100;
        porcentaje.setValue(p);
    }

    public void setPuntaje(int s) {
        if (s < 0) s = 0;
        puntaje.setValue(s);
    }

    public void setMetricas(float retardos, float asistencias, float faltas, float permisos) {
        metricas.setValue(new float[]{retardos, asistencias, faltas, permisos});
    }
}

