package com.example.horza_one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Panel_selec extends AppCompatActivity implements View.OnClickListener {

    FrameLayout frameregistro, frameinses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panel_selec);

        frameinses = findViewById(R.id.frameinses);
        frameregistro = findViewById(R.id.framer);

        frameinses.setOnClickListener(this);
        frameregistro.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intento;
        if (v.getId() == R.id.frameinses){
            intento = new Intent(Panel_selec.this, Login.class);
            startActivity(intento);
        } else
            if (v.getId() == R.id.framer){
                intento = new Intent(Panel_selec.this, Registro.class);
                startActivity(intento);
            }
    }
}