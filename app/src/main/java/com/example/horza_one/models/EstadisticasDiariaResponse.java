// Archivo: EstadisticasDiariaResponse.java
package com.example.horza_one.models;

import java.util.List;

public class EstadisticasDiariaResponse {
    private String fecha;
    private Integer totalTrabajadores;
    private Integer totalEntradas;
    private Integer totalSalidas;
    private Integer entradasPuntuales;
    private Integer retardos;
    private Integer inasistencias;
    private Integer permisos;
    private Double porcentajePuntuales;
    private Double porcentajeRetardos;
    private Double porcentajeInasistencias;
    private Double porcentajePermisos;

    // Getters y Setters
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public Integer getTotalTrabajadores() { return totalTrabajadores; }
    public void setTotalTrabajadores(Integer totalTrabajadores) { this.totalTrabajadores = totalTrabajadores; }

    public Integer getTotalEntradas() { return totalEntradas; }
    public void setTotalEntradas(Integer totalEntradas) { this.totalEntradas = totalEntradas; }

    public Integer getTotalSalidas() { return totalSalidas; }
    public void setTotalSalidas(Integer totalSalidas) { this.totalSalidas = totalSalidas; }

    public Integer getEntradasPuntuales() { return entradasPuntuales; }
    public void setEntradasPuntuales(Integer entradasPuntuales) { this.entradasPuntuales = entradasPuntuales; }

    public Integer getRetardos() { return retardos; }
    public void setRetardos(Integer retardos) { this.retardos = retardos; }

    public Integer getInasistencias() { return inasistencias; }
    public void setInasistencias(Integer inasistencias) { this.inasistencias = inasistencias; }

    public Integer getPermisos() { return permisos; }
    public void setPermisos(Integer permisos) { this.permisos = permisos; }

    public Double getPorcentajePuntuales() { return porcentajePuntuales; }
    public void setPorcentajePuntuales(Double porcentajePuntuales) { this.porcentajePuntuales = porcentajePuntuales; }

    public Double getPorcentajeRetardos() { return porcentajeRetardos; }
    public void setPorcentajeRetardos(Double porcentajeRetardos) { this.porcentajeRetardos = porcentajeRetardos; }

    public Double getPorcentajeInasistencias() { return porcentajeInasistencias; }
    public void setPorcentajeInasistencias(Double porcentajeInasistencias) { this.porcentajeInasistencias = porcentajeInasistencias; }

    public Double getPorcentajePermisos() { return porcentajePermisos; }
    public void setPorcentajePermisos(Double porcentajePermisos) { this.porcentajePermisos = porcentajePermisos; }
}

