package com.example.horza_one;

import android.content.Intent;
import android.graphics.Color;
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

import com.example.horza_one.databinding.ActivityPanelAdminBinding;

public class PanelAdmin extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPanelAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String nombreActual = getIntent().getStringExtra("UsuarioActual");
        String correoActual = getIntent().getStringExtra("CorreoActual");
        String rolActual = getIntent().getStringExtra("RolActual");




        binding = ActivityPanelAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPanelAdmin.toolbar);
        binding.appBarPanelAdmin.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.toolbar).show();
            }
        });





        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View header =navigationView.getHeaderView(0);
        TextView txtNombreUsuario = header.findViewById(R.id.txtNombreUsuario);
        TextView txtCorreo = header.findViewById(R.id.txtCorreo);
        TextView txtRol = header.findViewById(R.id.txtRol);

        txtRol.setText(rolActual);
        txtNombreUsuario.setText(nombreActual);
        txtCorreo.setText(correoActual);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Home_Fragment, R.id.ges_per, R.id.asis_acc, R.id.Rep_Est_Fragment, R.id.ctrl_emer,R.id.ajustes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_panel_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.salir) {
                Intent intent = new Intent(PanelAdmin.this, Login.class);
                startActivity(intent);
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
        getMenuInflater().inflate(R.menu.panel_admin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_panel_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}