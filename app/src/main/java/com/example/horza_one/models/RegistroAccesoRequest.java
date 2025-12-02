package com.example.horza_one.models;

import com.google.gson.annotations.SerializedName;

public class RegistroAccesoRequest {
    @SerializedName("matricula")
    private Integer matricula;

    @SerializedName("idDispositivo")
    private Integer idDispositivo;

    @SerializedName("tipoRegistro")
    private String tipoRegistro; // "Entrada" o "Salida"

    // Constructor sin argumentos
    public RegistroAccesoRequest() {
    }

    // Constructor con argumentos
    public RegistroAccesoRequest(Integer matricula, Integer idDispositivo, String tipoRegistro) {
        this.matricula = matricula;
        this.idDispositivo = idDispositivo;
        this.tipoRegistro = tipoRegistro;
    }

    // Getters y Setters
    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
