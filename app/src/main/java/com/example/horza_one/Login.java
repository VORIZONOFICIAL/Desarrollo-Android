package com.example.horza_one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity implements View.OnClickListener {
    FrameLayout FIS;
    EditText nombre, curp, rfc, correo;

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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frameis){
            Intent intento = new Intent(Login.this, Selec_rol.class);
            startActivity(intento);
            finish();
        }
    }
}