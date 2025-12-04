package com.example.horza_one.models;

import com.google.gson.annotations.SerializedName;

public class RegistroAccesoResponse {
    @SerializedName("exito")
    private boolean exito;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("estadoRegistro")
    private String estadoRegistro; // "Puntual", "Retardo", "Anticipado"

    @SerializedName("minutosDiferencia")
    private Integer minutosDiferencia; // Diferencia en minutos (positivo=retardo, negativo=anticipado)

    @SerializedName("registro")
    private Registro registro;

    // Constructor sin argumentos
    public RegistroAccesoResponse() {
    }

    // Constructor con argumentos
    public RegistroAccesoResponse(boolean exito, String mensaje, String estadoRegistro, Integer minutosDiferencia, Registro registro) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.estadoRegistro = estadoRegistro;
        this.minutosDiferencia = minutosDiferencia;
        this.registro = registro;
    }

    // Getters y Setters
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Integer getMinutosDiferencia() {
        return minutosDiferencia;
    }

    public void setMinutosDiferencia(Integer minutosDiferencia) {
        this.minutosDiferencia = minutosDiferencia;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }
}
