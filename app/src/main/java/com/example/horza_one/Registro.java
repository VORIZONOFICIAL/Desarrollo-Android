package com.example.horza_one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    FrameLayout frameInSe;
    EditText nombre, matricula, contraseña, rfc, curp, area,
            telefono, correo, direccion, t_cont, alta, est_lab ;

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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frameis){
            Intent intento = new Intent(Registro.this, Selec_rol.class);
            startActivity(intento);
        }
    }
}