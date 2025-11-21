package com.example.horza_one.models;

public class Rol {

    private Integer idRol;
    private String nombreRol;

    // Constructor sin argumentos
    public Rol() {
    }

    // Constructor con todos los argumentos
    public Rol(Integer idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
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
}
