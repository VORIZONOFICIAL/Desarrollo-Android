package com.example.horza_one.ui.ui_Admin.Ajustes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GestionHorarioCalendarioAdapter extends FragmentStateAdapter {

    public GestionHorarioCalendarioAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public GestionHorarioCalendarioAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Crear el fragment correspondiente según la posición del tab
        switch (position) {
            case 0:
                return new Tab_Bloques_Horario();
            case 1:
                return new Tab_Horarios();
            case 2:
                return new Tab_Calendarios();
            default:
                return new Tab_Bloques_Horario();
        }
    }

    @Override
    public int getItemCount() {
        // Número total de tabs
        return 3;
    }
}
