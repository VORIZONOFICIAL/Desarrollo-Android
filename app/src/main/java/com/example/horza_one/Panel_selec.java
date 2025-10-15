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

public class Panel_selec extends AppCompatActivity implements View.OnClickListener {

    Button botonr, botonis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panel_selec);

        botonis = findViewById(R.id.btnis);
        botonr = findViewById(R.id.btnregistro);

        botonis.setOnClickListener(this);
        botonr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intento;
        if (v.getId() == R.id.btnregistro){
            intento = new Intent(Panel_selec.this, Login.class);
            startActivity(intento);
        } else
            if (v.getId() == R.id.btnis){
                intento = new Intent(Panel_selec.this, Login.class);
                startActivity(intento);
            }
    }
}