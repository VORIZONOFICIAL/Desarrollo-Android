package com.example.horza_one.models;

public class Dispositivo {
    private Integer idDispositivo;
    private Area area;
    private String nombreDispositivo;
    private String descripcionDispositivo;
    private String activoDispositivo;

    // Constructor sin argumentos
    public Dispositivo() {
    }

    // Constructor con todos los argumentos
    public Dispositivo(Integer idDispositivo, Area area, String nombreDispositivo,
                       String descripcionDispositivo, String activoDispositivo) {
        this.idDispositivo = idDispositivo;
        this.area = area;
        this.nombreDispositivo = nombreDispositivo;
        this.descripcionDispositivo = descripcionDispositivo;
        this.activoDispositivo = activoDispositivo;
    }

    // Getters y Setters

    public Integer getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(Integer idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
}
