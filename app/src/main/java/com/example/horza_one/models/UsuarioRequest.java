package com.example.horza_one.models;

import com.google.gson.annotations.SerializedName;

public class UsuarioRequest {
    private Integer matricula;
    
    @SerializedName("idRol")
    private Integer idRol;
    
    @SerializedName("idCalendario")
    private Integer idCalendario;
    
    private String rfc;
    private String curp;
    
    @SerializedName("fechaAlta")
    private String fechaAlta; // formato yyyy-MM-dd
    
    @SerializedName("nombreUsuario")
    private String nombreUsuario;
    
    @SerializedName("apellidoPaternoUsuario")
    private String apellidoPaternoUsuario;
    
    @SerializedName("apellidoMaternoUsuario")
    private String apellidoMaternoUsuario;
    
    private String telefono;
    
    @SerializedName("tipoContrato")
    private String tipoContrato;
    
    private String correo;
    private String activo; // "Activo" o "Inactivo"
    
    @SerializedName("cpUsuario")
    private String cpUsuario;
    
    @SerializedName("calleUsuario")
    private String calleUsuario;
    
    @SerializedName("contrasena")
    private String contrasenia; // Backend espera "contrasena" en JSON

    // Constructor vacío
    public UsuarioRequest() {
    }

    // Constructor completo
    public UsuarioRequest(Integer matricula, Integer idRol, Integer idCalendario, String rfc, String curp, 
                         String fechaAlta, String nombreUsuario, String apellidoPaternoUsuario, 
                         String apellidoMaternoUsuario, String telefono, String tipoContrato, 
                         String correo, String activo, String cpUsuario, String calleUsuario, 
                         String contrasenia) {
        this.matricula = matricula;
        this.idRol = idRol;
        this.idCalendario = idCalendario;
        this.rfc = rfc;
        this.curp = curp;
        this.fechaAlta = fechaAlta;
        this.nombreUsuario = nombreUsuario;
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
        this.telefono = telefono;
        this.tipoContrato = tipoContrato;
        this.correo = correo;
        this.activo = activo;
        this.cpUsuario = cpUsuario;
        this.calleUsuario = calleUsuario;
        this.contrasenia = contrasenia;
    }

    // Getters y Setters
    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoPaternoUsuario() {
        return apellidoPaternoUsuario;
    }

    public void setApellidoPaternoUsuario(String apellidoPaternoUsuario) {
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
    }

    public String getApellidoMaternoUsuario() {
        return apellidoMaternoUsuario;
    }

    public void setApellidoMaternoUsuario(String apellidoMaternoUsuario) {
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getCpUsuario() {
        return cpUsuario;
    }

    public void setCpUsuario(String cpUsuario) {
        this.cpUsuario = cpUsuario;
    }

    public String getCalleUsuario() {
        return calleUsuario;
    }

    public void setCalleUsuario(String calleUsuario) {
        this.calleUsuario = calleUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
