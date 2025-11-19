package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("matricula")
    private String matricula;

    @SerializedName("contraseña")
    private String contrasena;

    public LoginRequest() {}

    public LoginRequest(String matricula, String contrasena) {
        this.matricula = matricula;
        this.contrasena = contrasena;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
