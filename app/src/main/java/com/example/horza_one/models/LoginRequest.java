package com.example.horza_one.models;


import com.google.gson.annotations.SerializedName;

public class LoginRequest {
        private String correo;

        @SerializedName("contrasenia")  // se enviara forzozamente "contrasenia"
        private String contrasena;

        public LoginRequest() {
        }

        public LoginRequest(String correo, String contrasena) {
            this.correo = correo;
            this.contrasena = contrasena;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }