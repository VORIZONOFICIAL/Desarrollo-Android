package com.example.horza_one.ui.ui_Personal.Horario;

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
                {"7:00 - 8:00", "Matemáticas I (1°A)", "Matemáticas II (2°B)", "Matemáticas I (1°A)", "Matemáticas II (2°B)", "Matemáticas I (1°A)"},
                {"8:00 - 9:00", "Matemáticas I (1°B)", "Matemáticas II (2°A)", "Matemáticas I (1°B)", "Matemáticas II (2°A)", "Matemáticas I (1°B)"},
                {"9:00 - 10:00", "Libre", "Libre", "Matemáticas II (2°A)", "Libre", "Libre"},
                {"10:00 - 10:30", "Receso", "Receso", "Receso", "Receso", "Receso"},
                {"10:30 - 11:30", "Matemáticas III (3°A)", "Matemáticas III (3°B)", "Libre", "Matemáticas III (3°A)", "Matemáticas III (3°B)"},
                {"11:30 - 12:30", "Tutoría 1°B", "Planeación", "Tutoría 2°A", "Reunión académica", "Planeación"},
                {"12:30 - 13:30", "Libre", "Matemáticas II (2°B)", "Libre", "Matemáticas I (1°B)", "Evaluaciones"}
        };

        for (String[] fila : horario) {
            TableRow tr = new TableRow(getContext());
            for (String celda : fila) {
                TextView tv = new TextView(getContext());
                tv.setText(celda);
                tv.setPadding(6, 16, 6, 16);
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
