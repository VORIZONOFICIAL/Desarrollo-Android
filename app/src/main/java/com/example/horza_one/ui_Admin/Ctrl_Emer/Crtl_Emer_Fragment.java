package com.example.horza_one.ui_Admin.Ctrl_Emer;

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
import com.example.horza_one.databinding.FragmentCtrlEmerAdminBinding;

public class Crtl_Emer_Fragment extends Fragment implements View.OnClickListener {
    LinearLayout BotonInicar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BotonInicar = view.findViewById(R.id.btnIniciar);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ctrl_emer_admin, container, false);
    }

    @Override
    public void onClick(View view) {

    }
    }
