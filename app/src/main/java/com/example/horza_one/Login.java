package com.example.horza_one;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horza_one.data.api.HorzaRepository;
import com.example.horza_one.data.api.RetrofitClient;
import com.example.horza_one.data.models.LoginResponse;

public class Login extends AppCompatActivity implements View.OnClickListener {
    FrameLayout FIS;
    EditText nombre, matricula, contra;
    Spinner rol;
    private HorzaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

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
    }

    @Override
    public void onClick(View v) {
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
            return;
        }

        if (contrasena.isEmpty()) {
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
            }
        });
    }
}