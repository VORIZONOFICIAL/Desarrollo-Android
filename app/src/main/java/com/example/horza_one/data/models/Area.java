package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("id")
    private Integer id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("ubicacion")
    private String ubicacion;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("estado")
    private String estado;

    public Area() {
        this.estado = "Activo";
    }

    public Area(String nombre, String ubicacion, String descripcion, String estado) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
