package com.example.horza_one.ui_Personal.Bitacora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.databinding.FragmentBitacoraPersonalBinding;
import com.example.horza_one.ui_Personal.Bitacora.Bitacora_ViewModel;

public class Bitacora_Fragment extends Fragment {

    private FragmentBitacoraPersonalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Bitacora_ViewModel Ajustes_ViewModel =
                new ViewModelProvider(this).get(Bitacora_ViewModel.class);

        binding = FragmentBitacoraPersonalBinding.inflate(inflater, container, false);
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
