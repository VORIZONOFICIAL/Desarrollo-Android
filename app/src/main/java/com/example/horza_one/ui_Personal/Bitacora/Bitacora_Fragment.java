package com.example.horza_one.ui_Personal.Bitacora;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentBitacoraPersonalBinding;
import com.example.horza_one.ui_Personal.Bitacora.Bitacora_ViewModel;

public class Bitacora_Fragment extends Fragment implements View.OnClickListener {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bitacora_personal, container, false);
    }

    @Override
    public void onClick(View view) {
    }
}
