package com.example.horza_one.ui.Ctrl_Emer;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.databinding.FragmentGalleryBinding;
import com.example.horza_one.ui.Ajustes.Ajustes_ViewModel;

public class Crtl_Emer_Fragment extends Fragment {
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Crtl_Emer_ViewModel Crtl_Emer_ViewModel =
                new ViewModelProvider(this).get(Crtl_Emer_ViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        Crtl_Emer_ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
