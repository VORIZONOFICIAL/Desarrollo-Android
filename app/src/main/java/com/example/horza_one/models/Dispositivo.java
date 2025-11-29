package com.example.horza_one.models;

import com.google.gson.annotations.SerializedName;

public class Dispositivo {
    @SerializedName("idDispositivo")
    private Integer idDispositivo;

    @SerializedName("nombreDispositivo")
    private String nombreDispositivo;

    @SerializedName("descripcionDispositivo")
    private String descripcionDispositivo;

    @SerializedName("activoDispositivo")
    private String activoDispositivo;

    @SerializedName("idArea")
    private Integer idArea;

    @SerializedName("nombreArea")
    private String nombreArea; // Campo adicional que vendrá del backend (JOIN)

    @SerializedName("ubicacionArea")
    private String ubicacionArea; // Ubicación heredada del área (JOIN)

    // Constructors
    public Dispositivo() {
    }

    public Dispositivo(String nombreDispositivo, String descripcionDispositivo, String activoDispositivo, Integer idArea) {
        this.nombreDispositivo = nombreDispositivo;
        this.descripcionDispositivo = descripcionDispositivo;
        this.activoDispositivo = activoDispositivo;
        this.idArea = idArea;
    }

    // Getters and Setters
    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getNombreDispositivo() {
        return nombreDispositivo;
    }

    public void setNombreDispositivo(String nombreDispositivo) {
        this.nombreDispositivo = nombreDispositivo;
    }

    public String getDescripcionDispositivo() {
        return descripcionDispositivo;
    }

    public void setDescripcionDispositivo(String descripcionDispositivo) {
        this.descripcionDispositivo = descripcionDispositivo;
    }

    public String getActivoDispositivo() {
        return activoDispositivo;
    }

    public void setActivoDispositivo(String activoDispositivo) {
        this.activoDispositivo = activoDispositivo;
    }

    public String getUbicacionArea() {
        return ubicacionArea;
    }

    public void setUbicacionArea(String ubicacionArea) {
        this.ubicacionArea = ubicacionArea;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
}
