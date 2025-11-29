package com.example.horza_one.models;

public class BajaResponse {
    private boolean success;
    private String mensaje;

    public BajaResponse() {
    }

    public BajaResponse(boolean success, String mensaje) {
        this.success = success;
        this.mensaje = mensaje;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
