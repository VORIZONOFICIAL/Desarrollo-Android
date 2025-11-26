package com.example.horza_one.models;

public class Bitacora {
    private Integer idBitacora;
    private Usuario usuario;
    private Integer numEntradas;
    private Integer numInasistencias;
    private Integer numRetardos;
    private Integer numEntradasAnticipadas;

    // Constructor sin argumentos
    public Bitacora() {
    }

    // Constructor con todos los argumentos
    public Bitacora(Integer idBitacora, Usuario usuario, Integer numEntradas, Integer numInasistencias,
                    Integer numRetardos, Integer numEntradasAnticipadas) {
        this.idBitacora = idBitacora;
        this.usuario = usuario;
        this.numEntradas = numEntradas;
        this.numInasistencias = numInasistencias;
        this.numRetardos = numRetardos;
        this.numEntradasAnticipadas = numEntradasAnticipadas;
    }

    // Getters y Setters

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getNumEntradas() {
        return numEntradas;
    }

    public void setNumEntradas(Integer numEntradas) {
        this.numEntradas = numEntradas;
    }

    public Integer getNumInasistencias() {
        return numInasistencias;
    }

    public void setNumInasistencias(Integer numInasistencias) {
        this.numInasistencias = numInasistencias;
    }

    public Integer getNumRetardos() {
        return numRetardos;
    }

    public void setNumRetardos(Integer numRetardos) {
        this.numRetardos = numRetardos;
    }

    public Integer getNumEntradasAnticipadas() {
        return numEntradasAnticipadas;
    }

    public void setNumEntradasAnticipadas(Integer numEntradasAnticipadas) {
        this.numEntradasAnticipadas = numEntradasAnticipadas;
    }
}
