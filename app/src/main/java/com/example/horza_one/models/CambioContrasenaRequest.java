package com.example.horza_one.models;

public class CambioContrasenaRequest {
    private Integer matricula;
    private String contrasenaActual;
    private String contrasenaNueva;

    public CambioContrasenaRequest() {
    }

    public CambioContrasenaRequest(Integer matricula, String contrasenaActual, String contrasenaNueva) {
        this.matricula = matricula;
        this.contrasenaActual = contrasenaActual;
        this.contrasenaNueva = contrasenaNueva;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }
}
