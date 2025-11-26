package com.example.horza_one;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
<<<<<<< HEAD
import android.widget.Spinner;
=======
>>>>>>> prueba
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horza_one.data.api.HorzaRepository;
import com.example.horza_one.data.api.RetrofitClient;
import com.example.horza_one.data.models.LoginResponse;

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
<<<<<<< HEAD
    EditText nombre, matricula, contra;
    Spinner rol;
    private HorzaRepository repository;
=======
    EditText edtCorreo, edtContrasena;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;
>>>>>>> prueba

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

<<<<<<< HEAD
        String cad[] = {"Seleccione Rol", "Administrador","Personal"};
        ArrayAdapter adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, cad);

        FIS = findViewById(R.id.frameis);

        nombre = findViewById(R.id.nombre);
        matricula = findViewById(R.id.matricula);

        rol = findViewById(R.id.spinrol);
        rol.setAdapter(adapter);

        contra = findViewById(R.id.contrasenia);

        FIS.setOnClickListener(this);

        repository = new HorzaRepository();
=======
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
>>>>>>> prueba
    }

    @Override
    public void onClick(View v) {
<<<<<<< HEAD
        if (v.getId() == R.id.frameis) {
            iniciarSesion();
        }
    }

    private void iniciarSesion() {
        String matricula = nombre.getText().toString().trim();
        String contrasena = contra.getText().toString().trim();

        // Validar campos
        if (matricula.isEmpty()) {
            nombre.setError("Ingrese su matrícula");
=======
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
>>>>>>> prueba
            return;
        }

        if (contrasena.isEmpty()) {
<<<<<<< HEAD
            contra.setError("Ingrese su contraseña");
            return;
        }

        // Deshabilitar botón mientras se procesa
        FIS.setEnabled(false);
        Toast.makeText(this, "Iniciando sesión...", Toast.LENGTH_SHORT).show();

        // Llamar al API
        repository.login(matricula, contrasena, new HorzaRepository.OnLoginCallback() {
            @Override
            public void onSuccess(LoginResponse response) {
                // Guardar token
                RetrofitClient.saveToken(response.getToken());

                // Guardar datos del usuario en SharedPreferences
                SharedPreferences prefs = getSharedPreferences("HorzaPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("usuario_id", response.getUsuario().getId() != null ?
                        response.getUsuario().getId() : 0);
                editor.putString("usuario_nombre", response.getUsuario().getNombre());
                editor.putString("usuario_rol", response.getUsuario().getRol());
                editor.putString("usuario_matricula", response.getUsuario().getMatricula());
                editor.apply();

                // Mostrar mensaje de bienvenida
                Toast.makeText(Login.this,
                        "Bienvenido " + response.getUsuario().getNombre(),
                        Toast.LENGTH_SHORT).show();

                if (rol.equals("Administrador")){
                    Intent intent = new Intent(Login.this, PanelAdmin.class);
                    startActivity(intent);
                } else if (rol.equals("Personal")) {
                     Intent intent = new Intent(Login.this, PanelPersonal.class);
                    startActivity(intent);

                }
                // Navegar a la siguiente pantalla

                finish();
            }

            @Override
            public void onError(String message) {
                // Mostrar error
                Toast.makeText(Login.this,
                        "Error: " + message,
                        Toast.LENGTH_LONG).show();

                // Rehabilitar botón
                FIS.setEnabled(true);
=======
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
>>>>>>> prueba
            }
        });
    }
}