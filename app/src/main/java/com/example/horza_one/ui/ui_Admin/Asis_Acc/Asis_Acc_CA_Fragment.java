package com.example.horza_one.ui_Admin.Asis_Acc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentAsisAccAdminBinding;
import com.example.horza_one.databinding.FragmentAsisAccCABinding;


public class Asis_Acc_CA_Fragment extends Fragment implements View.OnClickListener {

    private FragmentAsisAccCABinding binding;
    private LinearLayout cambiarArea;
    private Spinner spinner;
    private Handler handler = new Handler();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAsisAccCABinding.inflate(inflater, container, false);


        return inflater.inflate(R.layout.fragment_asis__acc__c_a_, container, false);

    }

    @Override
    public void onClick(View view) {
    }
}