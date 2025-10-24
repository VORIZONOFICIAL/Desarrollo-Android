package com.example.horza_one_god;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class Panel_Seleccion_Rol extends AppCompatActivity implements View.OnClickListener {

    FrameLayout btnadmin, btnpersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panel_seleccion_rol);

        btnadmin = findViewById(R.id.Admin);
        btnpersonal = findViewById(R.id.personal);

        btnadmin.setOnClickListener(this);
        btnadmin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intento;
        if (v.getId() == R.id.Admin){
            intento = new Intent(this, Admin.class);
            startActivity(intento);
        }
    }
}