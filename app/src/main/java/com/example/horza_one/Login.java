package com.example.horza_one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button botonR;
    EditText nombre, contra, curp, rfc, dir, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        botonR = findViewById(R.id.btnCR);

        nombre = findViewById(R.id.nombre);
        contra = findViewById(R.id.contra);
        curp = findViewById(R.id.curp);
        rfc = findViewById(R.id.rfc);
        dir = findViewById(R.id.direccion);
        correo = findViewById(R.id.correo);

        botonR.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCR){
            Intent intento = new Intent(Login.this, Selec_rol.class);
            startActivity(intento);
        }
    }
}