package com.example.horza_one.ui.ui_Admin.Ajustes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.horza_one.R;

public class Ajustes_Fragment  extends Fragment implements View.OnClickListener {

    CardView cardRyP , cardAreas, cardPassword,cardHorario;
    ImageView btnRegresar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardRyP = view.findViewById(R.id.cardRoles);
        cardAreas = view.findViewById(R.id.cardAreas);
        cardPassword = view.findViewById(R.id.cardPassword);
        cardHorario = view.findViewById(R.id.cardHorario);

        cardRyP.setOnClickListener(this);
        cardAreas.setOnClickListener(this);
        cardPassword.setOnClickListener(this);
        cardHorario.setOnClickListener(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ajustes_admin, container, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cardRoles) {
            Navigation.findNavController(view).navigate(R.id.ajustes_Roles_Permisos2);
        }else if (view.getId() == R.id.cardAreas) {
            Navigation.findNavController(view).navigate(R.id.ajustes_Administracion_Areas);
        }else if (view.getId() == R.id.cardHorario){
            Navigation.findNavController(view).navigate(R.id.ajustes_G_H_C);
        } else if (view.getId() == R.id.cardPassword) {
            Navigation.findNavController(view).navigate(R.id.cambio_Contrasena);
        }
    }
}
