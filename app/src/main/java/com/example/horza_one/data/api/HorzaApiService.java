package com.example.horza_one.data.api;

import com.example.horza_one.data.models.ApiResponse;
import com.example.horza_one.data.models.Area;
import com.example.horza_one.data.models.Asistencia;
import com.example.horza_one.data.models.Bitacora;
import com.example.horza_one.data.models.BloqueHorario;
import com.example.horza_one.data.models.Calendario;
import com.example.horza_one.data.models.EvaluacionPersonal;
import com.example.horza_one.data.models.Horario;
import com.example.horza_one.data.models.LoginRequest;
import com.example.horza_one.data.models.LoginResponse;
import com.example.horza_one.data.models.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HorzaApiService {
    @POST("auth/login")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<ApiResponse<Usuario>> register(@Body Usuario usuario);

    @POST("auth/logout")
    Call<ApiResponse<Void>> logout();

    // ==================== USUARIOS ====================
    @GET("usuarios")
    Call<ApiResponse<List<Usuario>>> getUsuarios();

    @GET("usuarios/{id}")
    Call<ApiResponse<Usuario>> getUsuario(@Path("id") int id);

    @POST("usuarios")
    Call<ApiResponse<Usuario>> createUsuario(@Body Usuario usuario);

    @PUT("usuarios/{id}")
    Call<ApiResponse<Usuario>> updateUsuario(@Path("id") int id, @Body Usuario usuario);

    @DELETE("usuarios/{id}")
    Call<ApiResponse<Void>> deleteUsuario(@Path("id") int id);

    @GET("usuarios/search")
    Call<ApiResponse<List<Usuario>>> searchUsuarios(@Query("query") String query);

    // ==================== ÁREAS ====================
    @GET("areas")
    Call<ApiResponse<List<Area>>> getAreas();

    @GET("areas/{id}")
    Call<ApiResponse<Area>> getArea(@Path("id") int id);

    @POST("areas")
    Call<ApiResponse<Area>> createArea(@Body Area area);

    @PUT("areas/{id}")
    Call<ApiResponse<Area>> updateArea(@Path("id") int id, @Body Area area);

    @DELETE("areas/{id}")
    Call<ApiResponse<Void>> deleteArea(@Path("id") int id);

    // ==================== BLOQUES HORARIOS ====================
    @GET("bloques")
    Call<ApiResponse<List<BloqueHorario>>> getBloques();

    @GET("bloques/{id}")
    Call<ApiResponse<BloqueHorario>> getBloque(@Path("id") int id);

    @POST("bloques")
    Call<ApiResponse<BloqueHorario>> createBloque(@Body BloqueHorario bloque);

    @PUT("bloques/{id}")
    Call<ApiResponse<BloqueHorario>> updateBloque(@Path("id") int id, @Body BloqueHorario bloque);

    @DELETE("bloques/{id}")
    Call<ApiResponse<Void>> deleteBloque(@Path("id") int id);

    // ==================== HORARIOS ====================
    @GET("horarios")
    Call<ApiResponse<List<Horario>>> getHorarios();

    @GET("horarios/{id}")
    Call<ApiResponse<Horario>> getHorario(@Path("id") int id);

    @POST("horarios")
    Call<ApiResponse<Horario>> createHorario(@Body Horario horario);

    @PUT("horarios/{id}")
    Call<ApiResponse<Horario>> updateHorario(@Path("id") int id, @Body Horario horario);

    @DELETE("horarios/{id}")
    Call<ApiResponse<Void>> deleteHorario(@Path("id") int id);

    // ==================== CALENDARIOS ====================
    @GET("calendarios")
    Call<ApiResponse<List<Calendario>>> getCalendarios();

    @GET("calendarios/{id}")
    Call<ApiResponse<Calendario>> getCalendario(@Path("id") int id);

    @POST("calendarios")
    Call<ApiResponse<Calendario>> createCalendario(@Body Calendario calendario);

    @PUT("calendarios/{id}")
    Call<ApiResponse<Calendario>> updateCalendario(@Path("id") int id, @Body Calendario calendario);

    @DELETE("calendarios/{id}")
    Call<ApiResponse<Void>> deleteCalendario(@Path("id") int id);

    // ==================== ASISTENCIAS ====================
    @GET("asistencias")
    Call<ApiResponse<List<Asistencia>>> getAsistencias(
            @Query("usuario_id") Integer usuarioId,
            @Query("fecha_inicio") String fechaInicio,
            @Query("fecha_fin") String fechaFin
    );

    @GET("asistencias/{id}")
    Call<ApiResponse<Asistencia>> getAsistencia(@Path("id") int id);

    @POST("asistencias")
    Call<ApiResponse<Asistencia>> registrarAsistencia(@Body Asistencia asistencia);

    @PUT("asistencias/{id}")
    Call<ApiResponse<Asistencia>> updateAsistencia(@Path("id") int id, @Body Asistencia asistencia);

    @POST("asistencias/entrada")
    Call<ApiResponse<Asistencia>> registrarEntrada(@Body Asistencia asistencia);

    @POST("asistencias/salida")
    Call<ApiResponse<Asistencia>> registrarSalida(@Query("usuario_id") int usuarioId);

    // ==================== BITÁCORAS ====================
    @GET("bitacoras")
    Call<ApiResponse<List<Bitacora>>> getBitacoras(@Query("usuario_id") Integer usuarioId);

    @POST("bitacoras")
    Call<ApiResponse<Bitacora>> createBitacora(@Body Bitacora bitacora);

    // ==================== EVALUACIONES ====================
    @GET("evaluaciones")
    Call<ApiResponse<List<EvaluacionPersonal>>> getEvaluaciones(@Query("usuario_id") Integer usuarioId);

    @GET("evaluaciones/{id}")
    Call<ApiResponse<EvaluacionPersonal>> getEvaluacion(@Path("id") int id);

    @POST("evaluaciones")
    Call<ApiResponse<EvaluacionPersonal>> createEvaluacion(@Body EvaluacionPersonal evaluacion);

    // ==================== ESTADÍSTICAS ====================
    @GET("estadisticas/resumen")
    Call<ApiResponse<Map<String, Object>>> getResumenEstadisticas();

    @GET("estadisticas/usuario/{id}")
    Call<ApiResponse<Map<String, Object>>> getEstadisticasUsuario(@Path("id") int id);
}
