package com.example.horza_one.models;

public class RolUsuario {
    private RolUsuarioId id;
    private Usuario usuario;
    private Rol rol;

    // Constructor sin argumentos
    public RolUsuario() {
    }

    // Constructor con todos los argumentos
    public RolUsuario(RolUsuarioId id, Usuario usuario, Rol rol) {
        this.id = id;
        this.usuario = usuario;
        this.rol = rol;
    }

    // Getters y Setters

    public RolUsuarioId getId() {
        return id;
    }

    public void setId(RolUsuarioId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}