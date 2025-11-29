package com.example.horza_one.models;

public class Horario {

    private Integer idHorario;
    private Calendario calendario;
    private String nombreHorario;
    private String descripcion;
    private String activoHorario;

    // Constructor sin argumentos
    public Horario() {
    }

    // Constructor con todos los argumentos
    public Horario(Integer idHorario, Calendario calendario, String nombreHorario,
                   String descripcion, String activoHorario) {
        this.idHorario = idHorario;
        this.calendario = calendario;
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.activoHorario = activoHorario;
    }

    // Getters y Setters

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public String getNombreHorario() {
        return nombreHorario;
    }

    public void setNombreHorario(String nombreHorario) {
        this.nombreHorario = nombreHorario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivoHorario() {
        return activoHorario;
    }

    public void setActivoHorario(String activoHorario) {
        this.activoHorario = activoHorario;
    }
}
