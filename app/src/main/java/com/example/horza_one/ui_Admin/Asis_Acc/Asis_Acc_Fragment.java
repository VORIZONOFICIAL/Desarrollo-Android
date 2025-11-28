package com.example.horza_one.ui_Admin.Asis_Acc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentAsisAccAdminBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Asis_Acc_Fragment extends Fragment implements View.OnClickListener {
    private FragmentAsisAccAdminBinding binding;
    private LinearLayout consultarHistorialCard, cambiarArea;
    private TextView textTime, textDate;

    private Handler handler = new Handler();
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateFormat;

    private Runnable timeUpdater = new Runnable() {
        @Override
        public void run() {
            updateDateTime();
            handler.postDelayed(this, 1000); // Actualizar cada segundo
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAsisAccAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Locale spanishLocale = new Locale("es", "ES");
        timeFormat = new SimpleDateFormat("HH:mm:ss", spanishLocale);
        dateFormat = new SimpleDateFormat("EEEE, dd 'de' MMMM yyyy", spanishLocale);

        textTime = binding.textTime;
        textDate = binding.textDate;
        consultarHistorialCard = binding.buttonConsultarHistorial;
        cambiarArea = binding.buttonCambiarArea;

        consultarHistorialCard.setOnClickListener(this);
        cambiarArea.setOnClickListener(this);

        updateDateTime();

        return root;
    }

    private void updateDateTime() {
        Date now = Calendar.getInstance().getTime();
        textTime.setText(timeFormat.format(now));
        textDate.setText(dateFormat.format(now));
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(timeUpdater);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(timeUpdater);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonConsultarHistorial.getId()) {
            Navigation.findNavController(v).navigate(R.id.asis_Acc_Hist_Fragment);
        } else if (v.getId() == binding.buttonCambiarArea.getId()) {
            Navigation.findNavController(v).navigate(R.id.asis_Acc_CA_Fragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}