// ============================================================
// Archivo: EstadisticasMensualResponse.java
package com.example.horza_one.models;

import java.util.List;

public class EstadisticasMensualResponse {
    private Integer mes;
    private Integer anio;
    private String tipoEstadistica;
    private List<EstadisticaDia> estadisticasPorDia;

    // Getters y Setters
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public String getTipoEstadistica() { return tipoEstadistica; }
    public void setTipoEstadistica(String tipoEstadistica) { this.tipoEstadistica = tipoEstadistica; }

    public List<EstadisticaDia> getEstadisticasPorDia() { return estadisticasPorDia; }
    public void setEstadisticasPorDia(List<EstadisticaDia> estadisticasPorDia) { this.estadisticasPorDia = estadisticasPorDia; }

    public static class EstadisticaDia {
        private Integer dia;
        private String fecha;
        private Integer cantidad;

        public Integer getDia() { return dia; }
        public void setDia(Integer dia) { this.dia = dia; }

        public String getFecha() { return fecha; }
        public void setFecha(String fecha) { this.fecha = fecha; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}