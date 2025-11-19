package com.example.horza_one.data.models;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("id")
    private Integer id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("matricula")
    private String matricula;

    @SerializedName("curp")
    private String curp;

    @SerializedName("rfc")
    private String rfc;

    @SerializedName("correo")
    private String correo;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("contraseña")
    private String contrasena;

    @SerializedName("rol")
    private String rol;

    @SerializedName("area_id")
    private Integer areaId;

    @SerializedName("calendario_id")
    private Integer calendarioId;

    @SerializedName("tipo_contrato")
    private String tipoContrato;

    @SerializedName("fecha_alta")
    private String fechaAlta;

    @SerializedName("estado_laboral")
    private String estadoLaboral;

    @SerializedName("activo")
    private Boolean activo;

    // Constructor vacío
    public Usuario() {
        this.activo = true;
    }

    // Constructor con parámetros
    public Usuario(String nombre, String matricula, String curp, String rfc,
                   String correo, String contrasena, String rol) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.curp = curp;
        this.rfc = rfc;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = true;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Integer getAreaId() { return areaId; }
    public void setAreaId(Integer areaId) { this.areaId = areaId; }

    public Integer getCalendarioId() { return calendarioId; }
    public void setCalendarioId(Integer calendarioId) { this.calendarioId = calendarioId; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getEstadoLaboral() { return estadoLaboral; }
    public void setEstadoLaboral(String estadoLaboral) { this.estadoLaboral = estadoLaboral; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}