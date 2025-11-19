package com.example.horza_one.ui.ui_Personal.C_Per;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.databinding.FragmentConfigPerPersonalBinding;

public class C_Per_Fragment extends Fragment {
    private FragmentConfigPerPersonalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        C_Per_ViewModel Ajustes_ViewModel =
                new ViewModelProvider(this).get(C_Per_ViewModel.class);

        binding = FragmentConfigPerPersonalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
