package com.example.horza_one.models;

import com.google.android.libraries.places.api.model.LocalTime;

public class BloqueHorario {
    private Integer idBloque;
    private Horario horario;
    private Area area;
    private String nombreBloque;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Constructor sin argumentos
    public BloqueHorario() {
    }

    // Constructor con todos los argumentos
    public BloqueHorario(Integer idBloque, Horario horario, Area area, String nombreBloque,
                         LocalTime horaInicio, LocalTime horaFin) {
        this.idBloque = idBloque;
        this.horario = horario;
        this.area = area;
        this.nombreBloque = nombreBloque;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters

    public Integer getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Integer idBloque) {
        this.idBloque = idBloque;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getNombreBloque() {
        return nombreBloque;
    }

    public void setNombreBloque(String nombreBloque) {
        this.nombreBloque = nombreBloque;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
