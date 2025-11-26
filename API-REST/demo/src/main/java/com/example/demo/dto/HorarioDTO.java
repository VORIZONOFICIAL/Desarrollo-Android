package com.example.demo.dto;

public class HorarioDTO {
    private Integer idHorario;
    private Integer idCalendario;
    private String nombreHorario;
    private String descripcion;
    private String activoHorario;

    // Constructor sin argumentos
    public HorarioDTO() {
    }

    // Constructor con todos los argumentos
    public HorarioDTO(Integer idHorario, Integer idCalendario, String nombreHorario, String descripcion, String activoHorario) {
        this.idHorario = idHorario;
        this.idCalendario = idCalendario;
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

    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
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
