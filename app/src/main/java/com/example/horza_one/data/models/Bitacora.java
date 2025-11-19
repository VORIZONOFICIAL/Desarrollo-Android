package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class Bitacora {
    @SerializedName("id")
    private Integer id;

    @SerializedName("usuario_id")
    private Integer usuarioId;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("tipo_actividad")
    private String tipoActividad;

    public Bitacora() {}

    public Bitacora(Integer usuarioId, String fecha, String descripcion, String tipoActividad) {
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipoActividad = tipoActividad;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipoActividad() { return tipoActividad; }
    public void setTipoActividad(String tipoActividad) { this.tipoActividad = tipoActividad; }
}