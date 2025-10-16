package com.example.horza_one.ui_Personal.C_Per;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.databinding.FragmentConfigPerPersonalBinding;
import com.example.horza_one.ui_Personal.C_Per.C_Per_ViewModel;

public class C_Per_Fragment extends Fragment {
    private FragmentConfigPerPersonalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        C_Per_ViewModel Ajustes_ViewModel =
                new ViewModelProvider(this).get(C_Per_ViewModel.class);

        binding = FragmentConfigPerPersonalBinding.inflate(inflater, container, false);
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
