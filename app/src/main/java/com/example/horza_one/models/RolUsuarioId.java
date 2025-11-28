package com.example.horza_one.models;

import java.io.Serializable;

public class RolUsuarioId implements Serializable {

    private Integer matricula;
    private Integer idRol;

    // Constructor sin argumentos
    public RolUsuarioId() {
    }

    // Constructor con todos los argumentos
    public RolUsuarioId(Integer matricula, Integer idRol) {
        this.matricula = matricula;
        this.idRol = idRol;
    }

    // Getters y Setters

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}
