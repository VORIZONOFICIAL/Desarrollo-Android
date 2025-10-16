package com.example.horza_one.ui_Personal.Horario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.databinding.FragmentHorarioPersonalBinding;
import com.example.horza_one.ui_Personal.Horario.Horario_ViewModel;

public class Horario_Fragment extends Fragment {
    private FragmentHorarioPersonalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Horario_ViewModel Ajustes_ViewModel =
                new ViewModelProvider(this).get(Horario_ViewModel.class);

        binding = FragmentHorarioPersonalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        Ajustes_ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
