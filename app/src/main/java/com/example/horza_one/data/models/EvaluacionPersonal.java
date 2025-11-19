package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class EvaluacionPersonal {
    @SerializedName("id")
    private Integer id;

    @SerializedName("usuario_id")
    private Integer usuarioId;

    @SerializedName("periodo")
    private String periodo;

    @SerializedName("retardos")
    private Integer retardos;

    @SerializedName("asistencias")
    private Integer asistencias;

    @SerializedName("faltas")
    private Integer faltas;

    @SerializedName("permisos")
    private Integer permisos;

    @SerializedName("porcentaje")
    private Float porcentaje;

    @SerializedName("puntaje")
    private Integer puntaje;

    public EvaluacionPersonal() {
        this.retardos = 0;
        this.asistencias = 0;
        this.faltas = 0;
        this.permisos = 0;
        this.porcentaje = 0f;
        this.puntaje = 0;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Integer getRetardos() { return retardos; }
    public void setRetardos(Integer retardos) { this.retardos = retardos; }

    public Integer getAsistencias() { return asistencias; }
    public void setAsistencias(Integer asistencias) { this.asistencias = asistencias; }

    public Integer getFaltas() { return faltas; }
    public void setFaltas(Integer faltas) { this.faltas = faltas; }

    public Integer getPermisos() { return permisos; }
    public void setPermisos(Integer permisos) { this.permisos = permisos; }

    public Float getPorcentaje() { return porcentaje; }
    public void setPorcentaje(Float porcentaje) { this.porcentaje = porcentaje; }

    public Integer getPuntaje() { return puntaje; }
    public void setPuntaje(Integer puntaje) { this.puntaje = puntaje; }
}
