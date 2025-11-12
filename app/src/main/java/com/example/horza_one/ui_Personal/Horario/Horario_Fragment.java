package com.example.horza_one.ui_Personal.Horario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentHorarioPersonalBinding;

public class Horario_Fragment extends Fragment {

    private FragmentHorarioPersonalBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Horario_ViewModel Horario_ViewModel =
                new ViewModelProvider(this).get(Horario_ViewModel.class);

        binding = FragmentHorarioPersonalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 1️⃣ Obtén la referencia del TableLayout
        TableLayout tabla = binding.tablaHorario;

        // 3️⃣ Crea las filas del horario
        String[][] horario = {
                {"8:00", "Matemáticas", "Español", "Historia", "Física", "Inglés"},
                {"9:00", "Arte", "Matemáticas", "Deporte", "Ciencias", "Música"},
                {"10:00", "Química", "Historia", "Matemáticas", "Programación", "Física"}
        };

        for (String[] fila : horario) {
            TableRow tr = new TableRow(getContext());
            for (String celda : fila) {
                TextView tv = new TextView(getContext());
                tv.setText(celda);
                tv.setPadding(6, 16, 6, 16);
                tv.setBackgroundResource(R.drawable.borde_celda); // 🔹 Aquí aplicas el borde
                tr.addView(tv);
            }
            tabla.addView(tr);
        }

        Spinner calendario;
        String cad[] = {"Seleccione área", "Área1","Área2","Área3"};
        ArrayAdapter adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cad);
        calendario = binding.selecCalendario;
        calendario.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
