package com.example.horza_one_god;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Panel_carga extends AppCompatActivity {

    ProgressBar progressBar;

    private Handler handler = new Handler();

    private int progreso;
    private int dotState;

    private final int durtotal = 300;
    private final int intervalo = 3;
    private final int intervalot = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panel_carga);


        progressBar = findViewById(R.id.barraprogreso);

        animarTexto();
        animarProgreso();
    }

    private void animarTexto() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dotState = (dotState + 1) % 4;
                handler.postDelayed(this, intervalot);
            }
        }, intervalot);
    }

    private void animarProgreso() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progreso++;
                progressBar.setProgress(progreso);

                if (progreso < 100) {
                    handler.postDelayed(this, intervalo);
                } else {
                    handler.postDelayed(() -> {
                        startActivity(new Intent(Panel_carga.this, Panel_Select_IS_R.class));
                        finish();
                    }, 300);
                }
            }
        }, intervalo);
    }
}