package com.example.horza_one.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Interceptor para loggear las respuestas HTTP en Logcat
 * Útil para debugging de llamadas a la API
 */
public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "API_Response";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        
        // Log de la petición
        Log.d(TAG, "Request URL: " + request.url());
        Log.d(TAG, "Request Method: " + request.method());
        
        // Ejecutar la petición
        Response response = chain.proceed(request);
        
        // Leer el body de la respuesta
        ResponseBody responseBody = response.body();
        String responseBodyString = responseBody.string();
        
        // Log de la respuesta
        Log.d(TAG, "Response Code: " + response.code());
        Log.d(TAG, "Response Body: " + responseBodyString);
        
        // Crear nueva respuesta con el body (ya que lo consumimos al leer)
        return response.newBuilder()
                .body(ResponseBody.create(responseBodyString, responseBody.contentType()))
                .build();
    }
}
