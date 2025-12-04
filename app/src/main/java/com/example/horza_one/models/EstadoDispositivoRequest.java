package com.example.horza_one.models;

public class EstadoDispositivoRequest {
    private String estado;

    public EstadoDispositivoRequest() {
    }

    public EstadoDispositivoRequest(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
