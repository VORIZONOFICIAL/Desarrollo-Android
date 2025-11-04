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

public class Selec_rol extends AppCompatActivity implements View.OnClickListener {

    FrameLayout btnadmin, btnpersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selec_rol);

        btnadmin = findViewById(R.id.Admin);
        btnpersonal = findViewById(R.id.personal);

        btnadmin.setOnClickListener(this);
        btnpersonal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intento;
        if (v.getId() == R.id.Admin){
            intento = new Intent(Selec_rol.this, PanelAdmin.class);
            startActivity(intento);
        } else
            if (v.getId() == R.id.personal){
                intento = new Intent(Selec_rol.this, PanelPersonal.class);
                startActivity(intento);
                finish();
            }
    }
}