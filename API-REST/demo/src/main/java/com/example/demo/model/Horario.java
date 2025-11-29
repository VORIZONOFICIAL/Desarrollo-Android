package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "horario")
public class Horario {
    
    @Id
    @Column(name = "id_horario")
    private Integer idHorario;
    
    @ManyToOne
    @JoinColumn(name = "id_calendario", nullable = false)
    private Calendario calendario;
    
    @Column(name = "nombre_horario", length = 60)
    private String nombreHorario;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "activo_horario", columnDefinition = "ENUM('Activo', 'Inactivo')")
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
