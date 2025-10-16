package com.example.horza_one.ui_Personal.Ev_Personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.databinding.FragmentEvPerPersonalBinding;
import com.example.horza_one.ui_Personal.Ev_Personal.Ev_Personal_ViewModel;

public class Ev_Personal_Fragment extends Fragment {
    private FragmentEvPerPersonalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Ev_Personal_ViewModel Ajustes_ViewModel =
                new ViewModelProvider(this).get(Ev_Personal_ViewModel.class);

        binding = FragmentEvPerPersonalBinding.inflate(inflater, container, false);
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
