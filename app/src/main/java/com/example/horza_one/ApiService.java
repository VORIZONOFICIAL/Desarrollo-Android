package com.example.horza_one;

import com.example.horza_one.models.Area;
import com.google.errorprone.annotations.Var;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    String BASE_URL = "http://10.0.2.2:8080/"; //modificar la direccion del host

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


    //Aplicar lo del CRUD a cada modelo
    //Endpint Area
    @GET("/api/areas")
    Call<List<Area>> obtenerTodas();

    @GET("/api/areas/{id}")
    Call<Area> obtenerPorId();

    @POST("/api/areas")
    Call<Area> obtenerPorId(@Body Area area);

    @PUT("/api/areas/{id}")
    Call<Area> actualizar(@Var Integer id, @Body Area area);
}
