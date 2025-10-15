package com.example.horza_one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Selec_rol extends AppCompatActivity implements View.OnClickListener {

    Button botonAdmin, botonPer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selec_rol);

        botonAdmin = findViewById(R.id.btnA);
        botonPer = findViewById(R.id.btnP);

        botonAdmin.setOnClickListener(this);
        botonPer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intento;
        if (v.getId() == R.id.btnA){
            intento = new Intent(Selec_rol.this, PanelAdmin.class);
            startActivity(intento);
        } else
            if (v.getId() == R.id.btnP){
                intento = new Intent(Selec_rol.this, PanelPersonal.class);
                startActivity(intento);
            }
    }
}