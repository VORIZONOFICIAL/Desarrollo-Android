package com.example.horza_one.ui_Admin.Ges_Per;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.databinding.FragmentGesPerAdminBinding;

public class Ges_Per_Fragment extends Fragment {

    private FragmentGesPerAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Ges_Per_ViewModel Ges_Per_ViewModel =
                new ViewModelProvider(this).get(Ges_Per_ViewModel.class);

        binding = FragmentGesPerAdminBinding.inflate(inflater,
                container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        Ges_Per_ViewModel.getText().observe(getViewLifecycleOwner(),
                textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}