package com.example.horza_one.data.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Cambia esta URL por la de tu servidor
    private static final String BASE_URL = "http://localhost:8080/api/"; // Para emulador
    // private static final String BASE_URL = "http://tu-servidor.com/api/"; // Para producción

    private static Retrofit retrofit = null;
    private static SharedPreferences sharedPreferences = null;

    /**
     * Inicializar el cliente con el contexto de la aplicación
     */
    public static void initialize(Context context) {
        sharedPreferences = context.getSharedPreferences("HorzaPrefs", Context.MODE_PRIVATE);
    }

    /**
     * Obtener el cliente Retrofit configurado
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Obtener el servicio API
     */
    public static HorzaApiService getApiService() {
        return getClient().create(HorzaApiService.class);
    }

    /**
     * Configurar OkHttpClient con interceptores
     */
    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getAuthInterceptor())
                .addInterceptor(getLoggingInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Interceptor para agregar el token JWT a las peticiones
     */
    private static Interceptor getAuthInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = getToken();
                Request request = chain.request();

                if (token != null && !token.isEmpty()) {
                    request = request.newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    /**
     * Interceptor para logging de peticiones y respuestas
     */
    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    /**
     * Guardar token JWT
     */
    public static void saveToken(String token) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString("auth_token", token).apply();
        }
    }

    /**
     * Obtener token JWT guardado
     */
    public static String getToken() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString("auth_token", null);
        }
        return null;
    }

    /**
     * Limpiar token JWT (para logout)
     */
    public static void clearToken() {
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove("auth_token").apply();
        }
    }

    /**
     * Verificar si hay sesión activa
     */
    public static boolean hasToken() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }

}
