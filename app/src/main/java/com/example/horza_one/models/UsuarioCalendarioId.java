package com.example.horza_one.models;

import java.io.Serializable;

public class UsuarioCalendarioId implements Serializable {
    private Integer matricula;
    private Integer idCalendario;
    // Constructor sin argumentos
    public UsuarioCalendarioId() {
    }
    // Constructor con todos los argumentos
    public UsuarioCalendarioId(Integer matricula, Integer idCalendario) {
        this.matricula = matricula;
        this.idCalendario = idCalendario;
    }
    // Getters y Setters

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }
}
