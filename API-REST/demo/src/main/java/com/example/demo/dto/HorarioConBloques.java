package com.example.demo.dto;

import java.util.List;

public class HorarioConBloques {
    private Integer idHorario;
    private String nombreHorario;
    private String descripcion;
    private List<BloqueHorarioDTO> bloques;

    public HorarioConBloques() {
    }

    public HorarioConBloques(Integer idHorario, String nombreHorario, String descripcion, List<BloqueHorarioDTO> bloques) {
        this.idHorario = idHorario;
        this.nombreHorario = nombreHorario;
        this.descripcion = descripcion;
        this.bloques = bloques;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
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

    public List<BloqueHorarioDTO> getBloques() {
        return bloques;
    }

    public void setBloques(List<BloqueHorarioDTO> bloques) {
        this.bloques = bloques;
    }
}
