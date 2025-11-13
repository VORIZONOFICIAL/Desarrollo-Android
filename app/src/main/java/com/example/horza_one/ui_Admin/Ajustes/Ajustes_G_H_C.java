package com.example.horza_one.ui_Admin.Ajustes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.horza_one.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Fragment principal para la Gestión de Horario y Calendario
 * Contiene 3 tabs: Bloques, Horarios y Calendarios
 */
public class Ajustes_G_H_C extends Fragment {



    private TabLayout tabLayoutGestion;
    private ViewPager2 viewPagerGestion;
    private GestionHorarioCalendarioAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ajustes__g__h__c, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        initViews(view);

        // Configurar ViewPager2 con el adapter
        setupViewPager();

        // Conectar TabLayout con ViewPager2
        setupTabLayout();
    }

    private void initViews(View view) {
        tabLayoutGestion = view.findViewById(R.id.tabLayoutGestion);
        viewPagerGestion = view.findViewById(R.id.viewPagerGestion);
    }

    private void setupViewPager() {
        // Crear adapter y asignarlo al ViewPager2
        adapter = new GestionHorarioCalendarioAdapter(this);
        viewPagerGestion.setAdapter(adapter);
    }

    private void setupTabLayout() {
        // Conectar TabLayout con ViewPager2
        new TabLayoutMediator(tabLayoutGestion, viewPagerGestion,
                (tab, position) -> {
                    // Configurar el texto de cada tab
                    switch (position) {
                        case 0:
                            tab.setText("Bloques");
                            break;
                        case 1:
                            tab.setText("Horarios");
                            break;
                        case 2:
                            tab.setText("Calendarios");
                            break;
                    }
                }
        ).attach();
    }
}