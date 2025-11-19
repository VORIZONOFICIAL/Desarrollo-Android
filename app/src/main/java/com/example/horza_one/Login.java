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

import com.example.horza_one.data.api.HorzaRepository;
import com.example.horza_one.data.api.RetrofitClient;
import com.example.horza_one.data.models.LoginResponse;

public class Login extends AppCompatActivity implements View.OnClickListener {
    FrameLayout FIS;
    EditText nombre, curp, rfc, correo;
    private HorzaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        FIS = findViewById(R.id.frameis);

        nombre = findViewById(R.id.nombre);
        curp = findViewById(R.id.curp);
        rfc = findViewById(R.id.rfc);
        correo = findViewById(R.id.correo);

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
        String contrasena = curp.getText().toString().trim();

        // Validar campos
        if (matricula.isEmpty()) {
            nombre.setError("Ingrese su matrícula");
            return;
        }

        if (contrasena.isEmpty()) {
            curp.setError("Ingrese su contraseña");
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

                // Navegar a la siguiente pantalla
                Intent intent = new Intent(Login.this, Selec_rol.class);
                startActivity(intent);
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
        Intent intento = new Intent(this, Selec_rol.class);
        startActivity(intento);
    }
}