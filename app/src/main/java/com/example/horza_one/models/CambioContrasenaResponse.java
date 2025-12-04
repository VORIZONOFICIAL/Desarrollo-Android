package com.example.horza_one.models;

import com.google.gson.annotations.SerializedName;

public class CambioContrasenaResponse {
    @SerializedName("success")
    private boolean success;
    
    @SerializedName("mensaje")
    private String mensaje;

    public CambioContrasenaResponse() {
    }

    public CambioContrasenaResponse(boolean success, String mensaje) {
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
