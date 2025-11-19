package com.example.horza_one.data.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.horza_one.data.models.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorzaRepository {

    private static final String TAG = "HorzaRepository";
    private final HorzaApiService apiService;

    public HorzaRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    public HorzaRepository(HorzaApiService apiService) {
        this.apiService = apiService;
    }

    // ==================== INTERFACES PARA CALLBACKS ====================

    public interface OnLoginCallback {
        void onSuccess(LoginResponse response);
        void onError(String message);
    }

    public interface OnUsuarioCallback {
        void onSuccess(Usuario usuario);
        void onError(String message);
    }

    public interface OnUsuariosCallback {
        void onSuccess(List<Usuario> usuarios);
        void onError(String message);
    }

    public interface OnAreasCallback {
        void onSuccess(List<Area> areas);
        void onError(String message);
    }

    public interface OnAsistenciasCallback {
        void onSuccess(List<Asistencia> asistencias);
        void onError(String message);
    }

    public interface OnAsistenciaCallback {
        void onSuccess(Asistencia asistencia);
        void onError(String message);
    }

    public interface OnEvaluacionesCallback {
        void onSuccess(List<EvaluacionPersonal> evaluaciones);
        void onError(String message);
    }

    public interface OnSuccessCallback {
        void onSuccess();
        void onError(String message);
    }

    // ==================== MÉTODOS DE AUTENTICACIÓN ====================

    public void login(String matricula, String contrasena, OnLoginCallback callback) {
        LoginRequest request = new LoginRequest(matricula, contrasena);

        apiService.login(request).enqueue(new Callback<ApiResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<LoginResponse>> call,
                                   @NonNull Response<ApiResponse<LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<LoginResponse> apiResponse = response.body();
                    if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error en login");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<LoginResponse>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error en login", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void register(Usuario usuario, OnUsuarioCallback callback) {
        apiService.register(usuario).enqueue(new Callback<ApiResponse<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Usuario>> call,
                                   @NonNull Response<ApiResponse<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Usuario> apiResponse = response.body();
                    if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error en registro");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Usuario>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error en registro", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== MÉTODOS DE USUARIOS ====================

    public void getUsuarios(OnUsuariosCallback callback) {
        apiService.getUsuarios().enqueue(new Callback<ApiResponse<List<Usuario>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Usuario>>> call,
                                   @NonNull Response<ApiResponse<List<Usuario>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Usuario>> apiResponse = response.body();
                    if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error al obtener usuarios");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Usuario>>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error al obtener usuarios", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void createUsuario(Usuario usuario, OnUsuarioCallback callback) {
        apiService.createUsuario(usuario).enqueue(new Callback<ApiResponse<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Usuario>> call,
                                   @NonNull Response<ApiResponse<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Usuario> apiResponse = response.body();
                    if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error al crear usuario");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Usuario>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error al crear usuario", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void updateUsuario(int id, Usuario usuario, OnUsuarioCallback callback) {
        apiService.updateUsuario(id, usuario).enqueue(new Callback<ApiResponse<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Usuario>> call,
                                   @NonNull Response<ApiResponse<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Usuario> apiResponse = response.body();
                    if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error al actualizar usuario");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Usuario>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error al actualizar usuario", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    public void deleteUsuario(int id, OnSuccessCallback callback) {
        apiService.deleteUsuario(id).enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Void>> call,
                                   @NonNull Response<ApiResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Void> apiResponse = response.body();
                    if (apiResponse.getSuccess()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error al eliminar usuario");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Void>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error al eliminar usuario", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== MÉTODOS DE ÁREAS ====================

    public void getAreas(OnAreasCallback callback) {
        apiService.getAreas().enqueue(new Callback<ApiResponse<List<Area>>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<List<Area>>> call,
                                   @NonNull Response<ApiResponse<List<Area>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<Area>> apiResponse = response.body();
                    if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error al obtener áreas");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<List<Area>>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error al obtener áreas", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== MÉTODOS DE ASISTENCIAS ====================

    public void getAsistencias(Integer usuarioId, String fechaInicio, String fechaFin,
                               OnAsistenciasCallback callback) {
        apiService.getAsistencias(usuarioId, fechaInicio, fechaFin)
                .enqueue(new Callback<ApiResponse<List<Asistencia>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<Asistencia>>> call,
                                           @NonNull Response<ApiResponse<List<Asistencia>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<List<Asistencia>> apiResponse = response.body();
                            if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                                callback.onSuccess(apiResponse.getData());
                            } else {
                                callback.onError(apiResponse.getMessage() != null ?
                                        apiResponse.getMessage() : "Error al obtener asistencias");
                            }
                        } else {
                            callback.onError("Error en la respuesta del servidor");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<Asistencia>>> call,
                                          @NonNull Throwable t) {
                        Log.e(TAG, "Error al obtener asistencias", t);
                        callback.onError("Error de conexión: " + t.getMessage());
                    }
                });
    }

    public void registrarEntrada(Asistencia asistencia, OnAsistenciaCallback callback) {
        apiService.registrarEntrada(asistencia).enqueue(new Callback<ApiResponse<Asistencia>>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse<Asistencia>> call,
                                   @NonNull Response<ApiResponse<Asistencia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<Asistencia> apiResponse = response.body();
                    if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                        callback.onSuccess(apiResponse.getData());
                    } else {
                        callback.onError(apiResponse.getMessage() != null ?
                                apiResponse.getMessage() : "Error al registrar entrada");
                    }
                } else {
                    callback.onError("Error en la respuesta del servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse<Asistencia>> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "Error al registrar entrada", t);
                callback.onError("Error de conexión: " + t.getMessage());
            }
        });
    }

    // ==================== MÉTODOS DE EVALUACIONES ====================

    public void getEvaluacionesUsuario(int usuarioId, OnEvaluacionesCallback callback) {
        apiService.getEvaluaciones(usuarioId)
                .enqueue(new Callback<ApiResponse<List<EvaluacionPersonal>>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<List<EvaluacionPersonal>>> call,
                                           @NonNull Response<ApiResponse<List<EvaluacionPersonal>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse<List<EvaluacionPersonal>> apiResponse = response.body();
                            if (apiResponse.getSuccess() && apiResponse.getData() != null) {
                                callback.onSuccess(apiResponse.getData());
                            } else {
                                callback.onError(apiResponse.getMessage() != null ?
                                        apiResponse.getMessage() : "Error al obtener evaluaciones");
                            }
                        } else {
                            callback.onError("Error en la respuesta del servidor");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<List<EvaluacionPersonal>>> call,
                                          @NonNull Throwable t) {
                        Log.e(TAG, "Error al obtener evaluaciones", t);
                        callback.onError("Error de conexión: " + t.getMessage());
                    }
                });
    }

    // Agrega más métodos según necesites...
}