package com.example.horza_one.ui.ui_Admin.Asis_Acc;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.horza_one.R;
import com.example.horza_one.data.api.HorzaRepository;
import com.example.horza_one.data.models.Asistencia;
import com.example.horza_one.databinding.FragmentAsisAccAdminBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Asis_Acc_Fragment extends Fragment implements View.OnClickListener {

    private FragmentAsisAccAdminBinding binding;
    private LinearLayout consultarHistorialCard, cambiarArea;
    private Button btnRegistrarEntrada, btnRegistrarSalida;
    private TextView textTime, textDate;
    private HorzaRepository repository;

    private Handler handler = new Handler();
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateFormat;

    private Runnable timeUpdater = new Runnable() {
        @Override
        public void run() {
            updateDateTime();
            handler.postDelayed(this, 1000);
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

        // Puedes agregar botones de registrar entrada/salida en tu layout
        // btnRegistrarEntrada = binding.btnRegistrarEntrada;
        // btnRegistrarSalida = binding.btnRegistrarSalida;

        consultarHistorialCard.setOnClickListener(this);
        cambiarArea.setOnClickListener(this);

        // Inicializar repository
        repository = new HorzaRepository();

        updateDateTime();

        return root;
    }

    private void registrarEntrada() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("HorzaPrefs", MODE_PRIVATE);
        int usuarioId = prefs.getInt("usuario_id", 0);

        if (usuarioId == 0) {
            Toast.makeText(getContext(), "Error: Usuario no identificado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto Asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setUsuarioId(usuarioId);
        asistencia.setFecha(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        asistencia.setTipo("Asistencia");

        repository.registrarEntrada(asistencia, new HorzaRepository.OnAsistenciaCallback() {
            @Override
            public void onSuccess(Asistencia asistenciaRegistrada) {
                Toast.makeText(getContext(),
                        "Entrada registrada: " + asistenciaRegistrada.getHoraEntrada(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(),
                        "Error: " + message,
                        Toast.LENGTH_LONG).show();
            }
        });
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
        // Tu código de navegación existente
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}