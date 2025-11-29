package com.example.horza_one.ui_Admin.Ajustes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Tab_Calendarios extends Fragment {

    // Views
    private EditText editNombreCalendario, editDescripcionCalendario;
    private TextView textFechaInicio, textFechaFin;
    private Spinner spinnerHorarios;
    private SwitchCompat switchActivoCalendario;
    private AppCompatButton btnCrearCalendario;
    private RecyclerView recyclerViewCalendarios;

    // TODO: Adapter
    // private CalendariosAdapter calendariosAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_calendarios, container, false);
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
        editNombreCalendario = view.findViewById(R.id.editNombreCalendario);
        editDescripcionCalendario = view.findViewById(R.id.editDescripcionCalendario);
        textFechaInicio = view.findViewById(R.id.textFechaInicio);
        textFechaFin = view.findViewById(R.id.textFechaFin);
        spinnerHorarios = view.findViewById(R.id.spinnerHorarios);
        switchActivoCalendario = view.findViewById(R.id.switchActivoCalendario);
        btnCrearCalendario = view.findViewById(R.id.btnCrearCalendario);
        recyclerViewCalendarios = view.findViewById(R.id.recyclerViewCalendarios);
    }

    private void setupRecyclerView() {
        recyclerViewCalendarios.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: Configurar adapter
        // calendariosAdapter = new CalendariosAdapter();
        // recyclerViewCalendarios.setAdapter(calendariosAdapter);
    }

    private void setupListeners() {
        // Click en fecha inicio - abrir DatePicker
        textFechaInicio.setOnClickListener(v -> showDatePickerInicio());
        
        // Click en fecha fin - abrir DatePicker
        textFechaFin.setOnClickListener(v -> showDatePickerFin());
        
        // Click en botón crear calendario
        btnCrearCalendario.setOnClickListener(v -> crearCalendario());
    }

    private void showDatePickerInicio() {
        // TODO: Implementar DatePickerDialog
        Toast.makeText(getContext(), "Seleccionar fecha de inicio", Toast.LENGTH_SHORT).show();
    }

    private void showDatePickerFin() {
        // TODO: Implementar DatePickerDialog
        Toast.makeText(getContext(), "Seleccionar fecha de fin", Toast.LENGTH_SHORT).show();
    }

    private void crearCalendario() {
        String nombre = editNombreCalendario.getText().toString().trim();
        String descripcion = editDescripcionCalendario.getText().toString().trim();
        boolean activo = switchActivoCalendario.isChecked();
        
        if (nombre.isEmpty()) {
            editNombreCalendario.setError("Ingrese un nombre");
            return;
        }
        
        // TODO: Validar fechas y horario seleccionado
        // TODO: Guardar en base de datos
        
        String estado = activo ? "Activo" : "Inactivo";
        Toast.makeText(getContext(), "Calendario creado: " + nombre + " (" + estado + ")", 
                      Toast.LENGTH_SHORT).show();
        
        // Limpiar formulario
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        editNombreCalendario.setText("");
        editDescripcionCalendario.setText("");
        textFechaInicio.setText("");
        textFechaFin.setText("");
        spinnerHorarios.setSelection(0);
        switchActivoCalendario.setChecked(true);
    }
}
