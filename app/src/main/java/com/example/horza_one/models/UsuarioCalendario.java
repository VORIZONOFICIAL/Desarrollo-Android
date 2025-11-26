package com.example.horza_one.models;

import java.io.Serializable;

public class UsuarioCalendario {
    private UsuarioCalendarioId id;
    private Usuario usuario;
    private Calendario calendario;

    // Constructor sin argumentos
    public UsuarioCalendario() {
    }

    // Constructor con todos los argumentos
    public UsuarioCalendario(UsuarioCalendarioId id, Usuario usuario, Calendario calendario) {
        this.id = id;
        this.usuario = usuario;
        this.calendario = calendario;
    }

    // Getters y Setters

    public UsuarioCalendarioId getId() {
        return id;
    }

    public void setId(UsuarioCalendarioId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
}