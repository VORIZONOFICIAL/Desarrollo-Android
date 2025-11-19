package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Horario {
    @SerializedName("id")
    private Integer id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("bloques")
    private List<AsignacionBloque> bloques;

    public Horario() {}

    public Horario(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<AsignacionBloque> getBloques() { return bloques; }
    public void setBloques(List<AsignacionBloque> bloques) { this.bloques = bloques; }
}