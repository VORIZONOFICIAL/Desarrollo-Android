package com.example.horza_one;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.horza_one.models.LoginRequest;
import com.example.horza_one.models.LoginResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements View.OnClickListener {
    FrameLayout FIS;
    EditText edtCorreo, edtContrasena;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        FIS = findViewById(R.id.frameis);
        edtCorreo = findViewById(R.id.correo);
        edtContrasena = findViewById(R.id.contrasena);

        FIS.setOnClickListener(this);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("HorzaPrefs", MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frameis){
            realizarLogin();
        }
    }

    private void realizarLogin() {
        String correo = edtCorreo.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();

        // Validar campos
        if (correo.isEmpty()) {
            edtCorreo.setError("Ingrese su correo");
            edtCorreo.requestFocus();
            return;
        }

        if (contrasena.isEmpty()) {
            edtContrasena.setError("Ingrese su contraseña");
            edtContrasena.requestFocus();
            return;
        }

        // Crear objeto de petición
        LoginRequest loginRequest = new LoginRequest(correo, contrasena);

        // Realizar llamada a la API
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = null;
                
                // Intentar obtener el LoginResponse del body o errorBody
                if (response.body() != null) {
                    loginResponse = response.body();
                } else if (response.errorBody() != null) {
                    // Si viene en errorBody (código 401), parsearlo
                    try {
                        String errorJson = response.errorBody().string();
                        Gson gson = new Gson();
                        loginResponse = gson.fromJson(errorJson, LoginResponse.class);
                    } catch (Exception e) {
                        Toast.makeText(Login.this, "Error al procesar respuesta del servidor", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                
                // Si tenemos un LoginResponse válido
                if (loginResponse != null) {
                    if (loginResponse.getSuccess()) {
                        // LOGIN EXITOSO
                        // Guardar datos en SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("matricula", loginResponse.getMatricula());
                        editor.putString("nombreCompleto", loginResponse.getNombreCompleto());
                        editor.putString("nombreRol", loginResponse.getNombreRol());
                        editor.putInt("idRol", loginResponse.getIdRol());
                        editor.putString("tipoPermiso", loginResponse.getTipoPermiso());
                        editor.apply();

                        Toast.makeText(Login.this, "Bienvenido " + loginResponse.getNombreCompleto(), Toast.LENGTH_SHORT).show();

                        // Redirigir según el tipo de permiso
                        if ("ADMIN".equals(loginResponse.getTipoPermiso())) {
                            // Usuario Administrador
                            Intent admin = new Intent(Login.this, PanelAdmin.class);
                            admin.putExtra("UsuarioActual",loginResponse.getNombreCompleto());
                            admin.putExtra("CorreoActual",correo);
                            admin.putExtra("RolActual",loginResponse.getNombreRol());
                            startActivity(admin);
                            finish();
                        } else {
                            // Usuario Personal
                            Intent personal = new Intent(Login.this, PanelPersonal.class);
                            personal.putExtra("UsuarioActual",loginResponse.getNombreCompleto());
                            personal.putExtra("CorreoActual",correo);
                            personal.putExtra("RolActual",loginResponse.getNombreRol());
                            startActivity(personal);
                            finish();
                        }
                    } else {
                        // ERROR DE LOGIN: Mostrar el mensaje específico del servidor
                        Toast.makeText(Login.this, loginResponse.getMensaje(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Error en el servidor. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}