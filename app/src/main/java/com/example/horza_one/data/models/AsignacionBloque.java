package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class AsignacionBloque {
    @SerializedName("dia_semana")
    private String diaSemana;

    @SerializedName("bloque_id")
    private Integer bloqueId;

    public AsignacionBloque() {}

    public AsignacionBloque(String diaSemana, Integer bloqueId) {
        this.diaSemana = diaSemana;
        this.bloqueId = bloqueId;
    }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public Integer getBloqueId() { return bloqueId; }
    public void setBloqueId(Integer bloqueId) { this.bloqueId = bloqueId; }
}