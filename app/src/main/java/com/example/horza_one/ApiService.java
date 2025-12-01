package com.example.horza_one;

import com.example.horza_one.models.Area;
import com.example.horza_one.models.BajaResponse;
import com.example.horza_one.models.BajaUsuarioRequest;
import com.example.horza_one.models.Calendario;
import com.example.horza_one.models.CambioContrasenaRequest;
import com.example.horza_one.models.CambioContrasenaResponse;
import com.example.horza_one.models.Dispositivo;
import com.example.horza_one.models.LoginRequest;
import com.example.horza_one.models.LoginResponse;
import com.example.horza_one.models.Rol;
import com.example.horza_one.models.Usuario;
import com.example.horza_one.models.UsuarioRequest;
import com.google.errorprone.annotations.Var;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    //modificar la direccion del host

    //Mikel
    //String BASE_URL = "http://10.0.2.2:8080/";

    //Greco
    String BASE_URL = "http://192.168.1.71:8080/";

//    Ejemplo
//    Endpoints de Usuarios
//    @GET("api/usuarios")
//    Call<Usuario>> obtenerUsuarios();
//
//    @GET("api/usuarios/{id}")
//    Call<Usuario> obtenerUsuario(@Path("id") Long id);
//
//    @POST("api/usuarios")
//    Call<Usuario> guardarUsuario(@Body Usuario usuario);
//
//    @DELETE("api/usuarios/{id}")
//    Call<Void> eliminarUsuario(@Path("id") Long id);

    // Endpoint de Login
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    // Endpoints de Usuarios
    @GET("/api/usuarios")
    Call<List<Usuario>> obtenerUsuarios();

    // Endpoints de Roles
    @GET("/api/roles")
    Call<List<Rol>> obtenerRoles();

    // Endpoints de Calendarios
    @GET("/api/calendarios")
    Call<List<Calendario>> obtenerCalendarios();

    // Endpoint POST para crear usuario
    @POST("/api/usuarios")
    Call<Usuario> crearUsuario(@Body UsuarioRequest usuarioRequest);

    // Endpoint POST para eliminar usuario con validación
    @POST("/api/usuarios/eliminar-con-validacion")
    Call<BajaResponse> eliminarUsuarioConValidacion(@Body BajaUsuarioRequest bajaRequest);

    // Endpoint PUT para actualizar usuario
    @PUT("/api/usuarios/{matricula}")
    Call<Usuario> actualizarUsuario(@Path("matricula") Integer matricula, @Body UsuarioRequest usuarioRequest);

    // Endpoints de Áreas
    @GET("/api/areas")
    Call<List<Area>> obtenerAreas();

    @GET("/api/areas/{id}")
    Call<Area> obtenerAreaPorId(@Path("id") Integer id);

    @POST("/api/areas")
    Call<Area> crearArea(@Body Area area);

    @PUT("/api/areas/{id}")
    Call<Area> actualizarArea(@Path("id") Integer id, @Body Area area);

    @DELETE("/api/areas/{id}")
    Call<Void> eliminarArea(@Path("id") Integer id);

    // Endpoint para cambiar contraseña
    @POST("/api/usuarios/cambiar-contrasena")
    Call<CambioContrasenaResponse> cambiarContrasena(@Body CambioContrasenaRequest request);

    // Endpoints de Dispositivos
    @GET("/api/dispositivos")
    Call<List<Dispositivo>> obtenerDispositivos();

    @GET("/api/dispositivos/{id}")
    Call<Dispositivo> obtenerDispositivoPorId(@Path("id") Integer id);

    @GET("/api/dispositivos/area/{idArea}")
    Call<List<Dispositivo>> obtenerDispositivosPorArea(@Path("idArea") Integer idArea);

    @POST("/api/dispositivos")
    Call<Dispositivo> crearDispositivo(@Body Dispositivo dispositivo);

    @PUT("/api/dispositivos/{id}")
    Call<Dispositivo> actualizarDispositivo(@Path("id") Integer id, @Body Dispositivo dispositivo);

    @DELETE("/api/dispositivos/{id}")
    Call<Void> eliminarDispositivo(@Path("id") Integer id);
}
