package com.example.horza_one;

import android.content.Intent;
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
import com.example.horza_one.data.models.Usuario;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    FrameLayout frameInSe;
    EditText nombre, matricula, contraseña, rfc, curp, area,
            telefono, correo, direccion, t_cont, alta, est_lab ;
    private HorzaRepository repository;
    Spinner calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        String cad[] = {"Seleccione Calendario", "Calendario1","Calendario2","Calendario3"};
        ArrayAdapter adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, cad);

        frameInSe = findViewById(R.id.frameis);
        nombre = findViewById(R.id.nombre);
        matricula = findViewById(R.id.matricula);
        contraseña = findViewById(R.id.contraseña);
        rfc = findViewById(R.id.rfc);
        curp = findViewById(R.id.curp);
        area = findViewById(R.id.area);
        telefono = findViewById(R.id.tel);
        correo = findViewById(R.id.correo);
        direccion = findViewById(R.id.direccion);
        t_cont = findViewById(R.id.t_contrato);
        alta = findViewById(R.id.f_alta);
        est_lab = findViewById(R.id.est_lab);
        calendario = findViewById(R.id.selec_calendario);

        frameInSe.setOnClickListener(this);
        calendario.setAdapter(adapter);

        repository = new HorzaRepository();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frameis) {
            registrarUsuario();
        }
    }

    private void registrarUsuario() {
        // Obtener valores
        String nombreStr = nombre.getText().toString().trim();
        String matriculaStr = matricula.getText().toString().trim();
        String contraseñaStr = contraseña.getText().toString().trim();
        String correoStr = correo.getText().toString().trim();

        // Validar campos obligatorios
        if (nombreStr.isEmpty()) {
            nombre.setError("Campo obligatorio");
            return;
        }

        if (matriculaStr.isEmpty()) {
            matricula.setError("Campo obligatorio");
            return;
        }

        if (contraseñaStr.isEmpty()) {
            contraseña.setError("Campo obligatorio");
            return;
        }

        if (correoStr.isEmpty()) {
            correo.setError("Campo obligatorio");
            return;
        }

        // Validar contraseña (mínimo 6 caracteres)
        if (contraseñaStr.length() < 6) {
            contraseña.setError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        // Crear objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombreStr);
        usuario.setMatricula(matriculaStr);
        usuario.setContrasena(contraseñaStr);
        usuario.setCurp(curp.getText().toString().trim());
        usuario.setRfc(rfc.getText().toString().trim());
        usuario.setCorreo(correoStr);
        usuario.setTelefono(telefono.getText().toString().trim());
        usuario.setDireccion(direccion.getText().toString().trim());
        usuario.setRol("personal"); // Por defecto es personal
        usuario.setTipoContrato(t_cont.getText().toString().trim());
        usuario.setFechaAlta(alta.getText().toString().trim());
        usuario.setEstadoLaboral(est_lab.getText().toString().trim());
        usuario.setActivo(true);

        // Deshabilitar botón
        frameInSe.setEnabled(false);
        Toast.makeText(this, "Registrando usuario...", Toast.LENGTH_SHORT).show();

        // Llamar al API
        repository.register(usuario, new HorzaRepository.OnUsuarioCallback() {
            @Override
            public void onSuccess(Usuario usuarioCreado) {
                Toast.makeText(Registro.this,
                        "Usuario registrado exitosamente",
                        Toast.LENGTH_SHORT).show();

                // Navegar a login
//                Intent intent = new Intent(Registro.this, Selec_rol.class);
//                startActivity(intent);
//                finish();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(Registro.this,
                        "Error: " + message,
                        Toast.LENGTH_LONG).show();

                // Rehabilitar botón
                frameInSe.setEnabled(true);
            }
        });
//        Intent intento = new Intent(this, Selec_rol.class);
//        startActivity(intento);
    }
}