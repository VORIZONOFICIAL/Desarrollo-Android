package com.example.demo.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bloques_horario")
public class BloqueHorario {
    
    @Id
    @Column(name = "id_bloque")
    private Integer idBloque;
    
    @ManyToOne
    @JoinColumn(name = "id_horario", nullable = false)
    private Horario horario;
    
    @ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;
    
    @Column(name = "nombre_bloque", length = 60)
    private String nombreBloque;
    
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin")
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
