package com.example.horza_one.ui_Admin.Ajustes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Adapter para ViewPager2 que maneja los tabs de Áreas y Dispositivos
 */
public class AreasDispositivosAdapter extends FragmentStateAdapter {

    public AreasDispositivosAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AreasTabFragment();
            case 1:
                return new DispositivosTabFragment();
            default:
                return new AreasTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // 2 tabs: Áreas y Dispositivos
    }
}
