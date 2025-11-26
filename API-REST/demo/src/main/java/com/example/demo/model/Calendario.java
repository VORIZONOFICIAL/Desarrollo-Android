package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "calendario")
public class Calendario {
    
    @Id
    @Column(name = "id_calendario")
    private Integer idCalendario;
    
    @Column(name = "nombre_calendario", length = 60)
    private String nombreCalendario;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "activo_calendario", columnDefinition = "ENUM('Activo', 'Inactivo')")
    private String activoCalendario;

    // Constructor sin argumentos
    public Calendario() {
    }

    // Constructor con todos los argumentos
    public Calendario(Integer idCalendario, String nombreCalendario, LocalDate fechaInicio, 
                      LocalDate fechaFin, String descripcion, String activoCalendario) {
        this.idCalendario = idCalendario;
        this.nombreCalendario = nombreCalendario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.activoCalendario = activoCalendario;
    }

    // Getters y Setters
    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getNombreCalendario() {
        return nombreCalendario;
    }

    public void setNombreCalendario(String nombreCalendario) {
        this.nombreCalendario = nombreCalendario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActivoCalendario() {
        return activoCalendario;
    }

    public void setActivoCalendario(String activoCalendario) {
        this.activoCalendario = activoCalendario;
    }
}
