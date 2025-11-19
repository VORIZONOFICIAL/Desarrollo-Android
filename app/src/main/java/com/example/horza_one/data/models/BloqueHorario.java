package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class BloqueHorario {
    @SerializedName("id")
    private Integer id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("hora_inicio")
    private String horaInicio;

    @SerializedName("hora_fin")
    private String horaFin;

    @SerializedName("area_id")
    private Integer areaId;

    public BloqueHorario() {}

    public BloqueHorario(String nombre, String horaInicio, String horaFin, Integer areaId) {
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.areaId = areaId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public Integer getAreaId() { return areaId; }
    public void setAreaId(Integer areaId) { this.areaId = areaId; }
}
