package com.example.horza_one.ui_Admin.Ajustes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.horza_one.R;


public class Ajustes_Roles_Permisos extends Fragment implements View.OnClickListener {

    ImageView regresar;
    LinearLayout BotonInicar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BotonInicar = view.findViewById(R.id.btnIniciar);

        regresar = view.findViewById(R.id.Regresa);
        regresar.setOnClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ctrl_emer_admin, container, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Regresa) {
            Navigation.findNavController(view).navigate(R.id.Home_Fragment);
        }
    }
}