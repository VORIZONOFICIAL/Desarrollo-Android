package com.example.horza_one.ui_Admin.Ajustes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Tab_Horarios extends Fragment {

    // Views
    private EditText editNombreHorario, editDescripcionHorario;
    private AppCompatButton btnGuardarHorario;
    
    // Celdas de días de la semana
    private LinearLayout cellLunes, cellMartes, cellMiercoles, cellJueves, 
                         cellViernes, cellSabado, cellDomingo;
    
    // RecyclerViews para cada día
    private RecyclerView recyclerLunes, recyclerMartes, recyclerMiercoles, 
                         recyclerJueves, recyclerViernes, recyclerSabado, recyclerDomingo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_horarios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Inicializar vistas
        initViews(view);
        
        // Configurar RecyclerViews de cada día
        setupRecyclerViews();
        
        // Configurar listeners de celdas
        setupCellListeners();
        
        setupSaveButton();
    }

    private void initViews(View view) {
        // Formulario
        editNombreHorario = view.findViewById(R.id.editNombreHorario);
        editDescripcionHorario = view.findViewById(R.id.editDescripcionHorario);
        btnGuardarHorario = view.findViewById(R.id.btnGuardarHorario);
        
        // Celdas
        cellLunes = view.findViewById(R.id.cellLunes);
        cellMartes = view.findViewById(R.id.cellMartes);
        cellMiercoles = view.findViewById(R.id.cellMiercoles);
        cellJueves = view.findViewById(R.id.cellJueves);
        cellViernes = view.findViewById(R.id.cellViernes);
        cellSabado = view.findViewById(R.id.cellSabado);
        cellDomingo = view.findViewById(R.id.cellDomingo);
        
        // RecyclerViews
        recyclerLunes = view.findViewById(R.id.recyclerLunes);
        recyclerMartes = view.findViewById(R.id.recyclerMartes);
        recyclerMiercoles = view.findViewById(R.id.recyclerMiercoles);
        recyclerJueves = view.findViewById(R.id.recyclerJueves);
        recyclerViernes = view.findViewById(R.id.recyclerViernes);
        recyclerSabado = view.findViewById(R.id.recyclerSabado);
        recyclerDomingo = view.findViewById(R.id.recyclerDomingo);
    }

    private void setupRecyclerViews() {
        // Configurar cada RecyclerView con su adapter
        setupRecyclerForDay(recyclerLunes);
        setupRecyclerForDay(recyclerMartes);
        setupRecyclerForDay(recyclerMiercoles);
        setupRecyclerForDay(recyclerJueves);
        setupRecyclerForDay(recyclerViernes);
        setupRecyclerForDay(recyclerSabado);
        setupRecyclerForDay(recyclerDomingo);
    }

    private void setupRecyclerForDay(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: Configurar adapter para bloques en celda
        // BloqueCeldaAdapter adapter = new BloqueCeldaAdapter();
        // recyclerView.setAdapter(adapter);
    }

    private void setupCellListeners() {
        // Click en cada celda abre el dialog de selección de bloques
        cellLunes.setOnClickListener(v -> showSeleccionarBloqueDialog("Lunes"));
        cellMartes.setOnClickListener(v -> showSeleccionarBloqueDialog("Martes"));
        cellMiercoles.setOnClickListener(v -> showSeleccionarBloqueDialog("Miércoles"));
        cellJueves.setOnClickListener(v -> showSeleccionarBloqueDialog("Jueves"));
        cellViernes.setOnClickListener(v -> showSeleccionarBloqueDialog("Viernes"));
        cellSabado.setOnClickListener(v -> showSeleccionarBloqueDialog("Sábado"));
        cellDomingo.setOnClickListener(v -> showSeleccionarBloqueDialog("Domingo"));
    }

    private void setupSaveButton() {
        btnGuardarHorario.setOnClickListener(v -> guardarHorario());
    }

    private void showSeleccionarBloqueDialog(String dia) {
        // TODO: Mostrar dialog para seleccionar bloque
        Toast.makeText(getContext(), "Seleccionar bloque para " + dia, Toast.LENGTH_SHORT).show();
        
        // Aquí deberías mostrar un DialogFragment con la lista de bloques disponibles
        // SeleccionarBloqueDialog dialog = SeleccionarBloqueDialog.newInstance(dia);
        // dialog.show(getChildFragmentManager(), "SeleccionarBloqueDialog");
    }

    private void guardarHorario() {
        String nombre = editNombreHorario.getText().toString().trim();
        String descripcion = editDescripcionHorario.getText().toString().trim();
        
        if (nombre.isEmpty()) {
            editNombreHorario.setError("Ingrese un nombre");
            return;
        }
        
        // TODO: Guardar horario en base de datos con sus bloques asignados
        Toast.makeText(getContext(), "Horario guardado: " + nombre, Toast.LENGTH_SHORT).show();
        
        // Limpiar formulario
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        editNombreHorario.setText("");
        editDescripcionHorario.setText("");
        // TODO: Limpiar bloques asignados en cada celda
    }
}
