package com.example.horza_one;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horza_one.databinding.ActivityPanelPersonalBinding;

public class PanelPersonal extends AppCompatActivity {




    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPanelPersonalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    String nombreActual = getIntent().getStringExtra("UsuarioActual");
    String correoActual = getIntent().getStringExtra("CorreoActual");
    String rolActual = getIntent().getStringExtra("RolActual");

        binding = ActivityPanelPersonalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPanelPersonal.toolbar);
        binding.appBarPanelPersonal.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.toolbar).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        View header =navigationView.getHeaderView(0);
        TextView txtNombreUsuario = header.findViewById(R.id.txtNombreUsuario);
        TextView txtCorreo = header.findViewById(R.id.txtCorreo);
        TextView txtRol = header.findViewById(R.id.txtRol);

        txtRol.setText(rolActual);
        txtNombreUsuario.setText(nombreActual);
        txtCorreo.setText(correoActual);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_bitacora, R.id.nav_horario, R.id.nav_ev_per, R.id.nav_con_per)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_panel_personal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.salir) {
<<<<<<< HEAD
//                Intent intent = new Intent(PanelPersonal.this, Selec_rol.class);
//                startActivity(intent);
=======
                Intent intent = new Intent(PanelPersonal.this, Login.class);
                startActivity(intent);
>>>>>>> prueba
                finish();
                return true;
            } else {
                // Para los demás items, usa la navegación normal
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panel_personal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_panel_personal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}