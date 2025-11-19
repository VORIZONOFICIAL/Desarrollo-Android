package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class Asistencia {
    @SerializedName("id")
    private Integer id;

    @SerializedName("usuario_id")
    private Integer usuarioId;

    @SerializedName("fecha")
    private String fecha;

    @SerializedName("hora_entrada")
    private String horaEntrada;

    @SerializedName("hora_salida")
    private String horaSalida;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("area_actual")
    private String areaActual;

    @SerializedName("observaciones")
    private String observaciones;

    public Asistencia() {}

    public Asistencia(Integer usuarioId, String fecha, String tipo) {
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getAreaActual() { return areaActual; }
    public void setAreaActual(String areaActual) { this.areaActual = areaActual; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
