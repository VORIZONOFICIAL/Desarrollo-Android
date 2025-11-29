package com.example.horza_one.ui_Admin.Ajustes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.horza_one.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Fragment principal para Administración de Áreas y Dispositivos
 * Contiene 2 tabs: Áreas y Dispositivos
 */
public class Ajustes_Administracion_Areas extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private AreasDispositivosAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ajustes__administracion__areas_new, container, false);

        // Inicializar vistas
        inicializarVistas(vista);
        
        // Configurar ViewPager2 con el adapter
        setupViewPager();
        
        // Conectar TabLayout con ViewPager2
        setupTabLayout();

        return vista;
    }
    
    private void inicializarVistas(View vista) {
        tabLayout = vista.findViewById(R.id.tabLayout);
        viewPager = vista.findViewById(R.id.viewPager);
    }
    
    private void setupViewPager() {
        // Crear adapter y asignarlo al ViewPager2
        adapter = new AreasDispositivosAdapter(this);
        viewPager.setAdapter(adapter);
    }
    
    private void setupTabLayout() {
        // Conectar TabLayout con ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Configurar el texto de cada tab
                    switch (position) {
                        case 0:
                            tab.setText("Áreas");
                            break;
                        case 1:
                            tab.setText("Dispositivos");
                            break;
                    }
                }
        ).attach();
    }
}
