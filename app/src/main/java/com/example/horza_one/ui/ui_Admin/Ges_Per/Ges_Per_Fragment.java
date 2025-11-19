package com.example.horza_one.ui.ui_Admin.Ges_Per;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.horza_one.R;

public class Ges_Per_Fragment extends Fragment implements View.OnClickListener {
CardView cardAltas , cardBajas ,cardModificaciones ,cardConsultas;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardAltas = view.findViewById(R.id.btnAgregarPersonal);
        cardBajas = view.findViewById(R.id.btnEliminarPersonal);
        cardModificaciones = view.findViewById(R.id.btnEditarPersonal);
        cardConsultas = view.findViewById(R.id.btnConsultasPersonal);

        cardAltas.setOnClickListener(this);
        cardBajas.setOnClickListener(this);
        cardModificaciones.setOnClickListener(this);
        cardConsultas.setOnClickListener(this);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ges_per_admin, container, false);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnConsultasPersonal){
            Navigation.findNavController(view).navigate(R.id.consultas);
        }else if (view.getId() == R.id.btnEliminarPersonal){
            Navigation.findNavController(view).navigate(R.id.baja);
        } else if (view.getId() == R.id.btnEditarPersonal) {
            Navigation.findNavController(view).navigate(R.id.modificaciones);
        } else if (view.getId() == R.id.btnAgregarPersonal) {
            Navigation.findNavController(view).navigate(R.id.alta);
        }

    }
}