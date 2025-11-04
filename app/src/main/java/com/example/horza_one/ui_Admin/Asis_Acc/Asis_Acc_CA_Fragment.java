package com.example.horza_one.ui_Admin.Asis_Acc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.horza_one.R;


public class Asis_Acc_CA_Fragment extends Fragment implements View.OnClickListener {
    ImageView regresar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        regresar = view.findViewById(R.id.Regresa);
        regresar.setOnClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asis__acc__c_a_, container, false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Regresa) {
            Navigation.findNavController(view).navigate(R.id.asis_Acc_CA_Fragment);
        }
    }
}