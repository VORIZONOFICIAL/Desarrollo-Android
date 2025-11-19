package com.example.horza_one.ui.ui_Admin.Ajustes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_Bloques_Horario extends Fragment {

    // Views
    private EditText editNombreBloque;
    private Spinner spinnerArea;
    private TextView textHoraInicio, textHoraFin;
    private AppCompatButton btnCrearBloque;
    private RecyclerView recyclerViewBloques;

    // TODO: Adapter para el RecyclerView
    // private BloquesAdapter bloquesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_bloques_horario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Inicializar vistas
        initViews(view);
        
        // Configurar RecyclerView
        setupRecyclerView();
        
        // Configurar listeners
        setupListeners();
    }

    private void initViews(View view) {
        editNombreBloque = view.findViewById(R.id.editNombreBloque);
        spinnerArea = view.findViewById(R.id.spinnerArea);
        textHoraInicio = view.findViewById(R.id.textHoraInicio);
        textHoraFin = view.findViewById(R.id.textHoraFin);
        btnCrearBloque = view.findViewById(R.id.btnCrearBloque);
        recyclerViewBloques = view.findViewById(R.id.recyclerViewBloques);
    }

    private void setupRecyclerView() {
        recyclerViewBloques.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: Configurar el adapter para el RecyclerView
    }

    private void setupListeners() {
        // Click en hora inicio - abrir TimePicker
        textHoraInicio.setOnClickListener(v -> showTimePickerInicio());
        
        // Click en hora fin - abrir TimePicker
        textHoraFin.setOnClickListener(v -> showTimePickerFin());
        
        // Click en botón crear bloque
        btnCrearBloque.setOnClickListener(v -> crearBloque());
    }

    private void showTimePickerInicio() {
        Toast.makeText(getContext(), "Seleccionar hora de inicio", Toast.LENGTH_SHORT).show();
    }

    private void showTimePickerFin() {
        Toast.makeText(getContext(), "Seleccionar hora de fin", Toast.LENGTH_SHORT).show();
    }

    private void crearBloque() {
        String nombre = editNombreBloque.getText().toString().trim();
        
        if (nombre.isEmpty()) {
            editNombreBloque.setError("Ingrese un nombre");
            return;
        }
        
        Toast.makeText(getContext(), "Bloque creado: " + nombre, Toast.LENGTH_SHORT).show();
        
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        editNombreBloque.setText("");
        textHoraInicio.setText("");
        textHoraFin.setText("");
        spinnerArea.setSelection(0);
    }
}
