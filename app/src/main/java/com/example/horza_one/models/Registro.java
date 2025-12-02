package com.example.horza_one.models;

import com.google.gson.annotations.SerializedName;

public class Registro {
    @SerializedName("idRegistro")
    private Integer idRegistro;

    @SerializedName("matricula")
    private Integer matricula;

    @SerializedName("idBitacora")
    private Integer idBitacora;

    @SerializedName("idDispositivo")
    private Integer idDispositivo;

    @SerializedName("idArea")
    private Integer idArea;

    @SerializedName("tipoRegistro")
    private String tipoRegistro;

    @SerializedName("fecha")
    private String fecha; // formato LocalDate yyyy-MM-dd

    @SerializedName("hora")
    private String hora; // formato LocalTime HH:mm:ss

    @SerializedName("observacion")
    private String observacion;

    @SerializedName("estadoRegistro")
    private String estadoRegistro;

    // Constructor sin argumentos
    public Registro() {
    }

    // Getters y Setters
    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
}
