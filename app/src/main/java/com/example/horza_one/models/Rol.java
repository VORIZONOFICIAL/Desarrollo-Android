package com.example.horza_one.models;

public class Rol {

    private Integer idRol;
    private String nombreRol;
    private String tipoPermiso;

    // Constructor sin argumentos
    public Rol() {
    }

    // Constructor con todos los argumentos
    public Rol(Integer idRol, String nombreRol, String tipoPermiso) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.tipoPermiso = tipoPermiso;
    }

    // Getters y Setters

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }
}
