package com.example.horza_one.ui_Admin.Ajustes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.models.Area;
import com.example.horza_one.models.Dispositivo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment del Tab de Dispositivos - CRUD de dispositivos
 */
public class DispositivosTabFragment extends Fragment {

    // Vistas del formulario
    private EditText editIdDispositivo, editNombreDispositivo, editDescripcionDispositivo;
    private Spinner spinnerArea;
    private androidx.appcompat.widget.SwitchCompat switchActivoDispositivo;
    private AppCompatButton btnRegistrarDispositivo, btnCancelarEdicion;
    
    // Búsqueda
    private EditText editBuscarDispositivo;
    private AppCompatButton btnBuscar;
    
    // RecyclerView
    private RecyclerView recyclerViewDispositivos;
    private DispositivoAdapter dispositivoAdapter;
    private List<Dispositivo> listaDispositivos;
    private List<Dispositivo> listaDispositivosFiltrada;
    
    // Áreas para el spinner
    private List<Area> listaAreas;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> nombresAreas;
    
    // API
    private ApiService apiService;
    
    // Modo edición
    private boolean modoEdicion = false;
    private Dispositivo dispositivoEnEdicion = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_tab_dispositivos, container, false);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        apiService = retrofit.create(ApiService.class);

        // Inicializar vistas
        inicializarVistas(vista);
        
        // Configurar RecyclerView
        listaDispositivos = new ArrayList<>();
        listaDispositivosFiltrada = new ArrayList<>();
        recyclerViewDispositivos.setLayoutManager(new LinearLayoutManager(getContext()));
        dispositivoAdapter = new DispositivoAdapter(listaDispositivosFiltrada);
        recyclerViewDispositivos.setAdapter(dispositivoAdapter);
        
        // Cargar áreas para el spinner
        cargarAreas();
        
        // Cargar dispositivos desde el backend
        cargarDispositivos();
        
        // Configurar listener del botón
        btnRegistrarDispositivo.setOnClickListener(v -> registrarDispositivo());

        return vista;
    }
    
    private void inicializarVistas(View vista) {
        editIdDispositivo = vista.findViewById(R.id.editIdDispositivo);
        editNombreDispositivo = vista.findViewById(R.id.editNombreDispositivo);
        editDescripcionDispositivo = vista.findViewById(R.id.editDescripcionDispositivo);
        spinnerArea = vista.findViewById(R.id.spinnerArea);
        switchActivoDispositivo = vista.findViewById(R.id.switchActivoDispositivo);
        btnRegistrarDispositivo = vista.findViewById(R.id.btnRegistrarDispositivo);
        btnCancelarEdicion = vista.findViewById(R.id.btnCancelarEdicion);
        recyclerViewDispositivos = vista.findViewById(R.id.recyclerViewDispositivos);
        editBuscarDispositivo = vista.findViewById(R.id.editBuscarDispositivo);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        
        // Configurar listener del botón cancelar
        btnCancelarEdicion.setOnClickListener(v -> cancelarEdicion());
        
        // Configurar listener del botón buscar
        btnBuscar.setOnClickListener(v -> buscarDispositivos());
        
        // Inicializar listas para spinner
        listaAreas = new ArrayList<>();
        nombresAreas = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<>(requireContext(), 
                android.R.layout.simple_spinner_item, nombresAreas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(spinnerAdapter);
    }
    
    private void cargarAreas() {
        Call<List<Area>> call = apiService.obtenerAreas();
        call.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaAreas.clear();
                    listaAreas.addAll(response.body());
                    
                    // Actualizar spinner
                    nombresAreas.clear();
                    for (Area area : listaAreas) {
                        nombresAreas.add(area.getNombreArea());
                    }
                    spinnerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireContext(), "Error al cargar áreas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void cargarDispositivos() {
        Call<List<Dispositivo>> call = apiService.obtenerDispositivos();
        call.enqueue(new Callback<List<Dispositivo>>() {
            @Override
            public void onResponse(Call<List<Dispositivo>> call, Response<List<Dispositivo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaDispositivos.clear();
                    listaDispositivos.addAll(response.body());
                    listaDispositivosFiltrada.clear();
                    listaDispositivosFiltrada.addAll(listaDispositivos);
                    dispositivoAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireContext(), "Error al cargar dispositivos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dispositivo>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void registrarDispositivo() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Obtener ID (solo se usa en modo creación)
        String idStr = editIdDispositivo.getText().toString().trim();
        Integer id = null;
        if (!modoEdicion) {
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "ID inválido", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        
        // Obtener valores
        String nombre = editNombreDispositivo.getText().toString().trim();
        String descripcion = editDescripcionDispositivo.getText().toString().trim();
        String estado = switchActivoDispositivo.isChecked() ? "Activo" : "Inactivo";
        
        // Obtener el área seleccionada
        int posicionArea = spinnerArea.getSelectedItemPosition();
        if (posicionArea < 0 || posicionArea >= listaAreas.size()) {
            Toast.makeText(requireContext(), "Selecciona un área válida", Toast.LENGTH_SHORT).show();
            return;
        }
        Area areaSeleccionada = listaAreas.get(posicionArea);
        
        if (modoEdicion && dispositivoEnEdicion != null) {
            // MODO EDICIÓN - Actualizar dispositivo existente (ID no cambia)
            Dispositivo dispositivoActualizado = new Dispositivo();
            dispositivoActualizado.setIdDispositivo(dispositivoEnEdicion.getIdDispositivo());
            dispositivoActualizado.setNombreDispositivo(nombre);
            dispositivoActualizado.setDescripcionDispositivo(descripcion);
            dispositivoActualizado.setActivoDispositivo(estado);
            dispositivoActualizado.setIdArea(areaSeleccionada.getIdArea());
            
            Call<Dispositivo> call = apiService.actualizarDispositivo(
                    dispositivoEnEdicion.getIdDispositivo(), dispositivoActualizado);
            call.enqueue(new Callback<Dispositivo>() {
                @Override
                public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(requireContext(), "Dispositivo actualizado exitosamente", Toast.LENGTH_SHORT).show();
                        cancelarEdicion();
                        cargarDispositivos();
                    } else {
                        Toast.makeText(requireContext(), "Error al actualizar dispositivo", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Dispositivo> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            // MODO CREACIÓN - Crear nuevo dispositivo con ID proporcionado
            final Integer idFinal = id; // Variable final para usar en callback
            
            Dispositivo nuevoDispositivo = new Dispositivo();
            nuevoDispositivo.setIdDispositivo(idFinal);
            nuevoDispositivo.setNombreDispositivo(nombre);
            nuevoDispositivo.setDescripcionDispositivo(descripcion);
            nuevoDispositivo.setActivoDispositivo(estado);
            nuevoDispositivo.setIdArea(areaSeleccionada.getIdArea());
            
            Call<Dispositivo> call = apiService.crearDispositivo(nuevoDispositivo);
            call.enqueue(new Callback<Dispositivo>() {
                @Override
                public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(requireContext(), "Dispositivo registrado exitosamente", Toast.LENGTH_SHORT).show();
                        limpiarFormulario();
                        cargarDispositivos();
                    } else if (response.code() == 409) {
                        // ID duplicado - Conflict
                        Toast.makeText(requireContext(), "Error: El ID " + idFinal + " ya está en uso. Elige otro ID.", Toast.LENGTH_LONG).show();
                        editIdDispositivo.setError("ID duplicado");
                        editIdDispositivo.requestFocus();
                    } else if (response.code() == 400) {
                        // Bad Request
                        Toast.makeText(requireContext(), "Error: Datos inválidos. Verifica la información.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(requireContext(), "Error al registrar dispositivo (código: " + response.code() + ")", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Dispositivo> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    
    private boolean validarCampos() {
        // Validar ID (solo en modo creación, en edición está deshabilitado)
        if (!modoEdicion) {
            if (editIdDispositivo.getText().toString().trim().isEmpty()) {
                editIdDispositivo.setError("El ID es obligatorio");
                editIdDispositivo.requestFocus();
                return false;
            }
            
            try {
                Integer.parseInt(editIdDispositivo.getText().toString().trim());
            } catch (NumberFormatException e) {
                editIdDispositivo.setError("El ID debe ser un número válido");
                editIdDispositivo.requestFocus();
                return false;
            }
        }
        
        if (editNombreDispositivo.getText().toString().trim().isEmpty()) {
            editNombreDispositivo.setError("El nombre es obligatorio");
            editNombreDispositivo.requestFocus();
            return false;
        }
        
        if (editDescripcionDispositivo.getText().toString().trim().isEmpty()) {
            editDescripcionDispositivo.setError("La descripción es obligatoria");
            editDescripcionDispositivo.requestFocus();
            return false;
        }
        
        if (spinnerArea.getSelectedItemPosition() < 0) {
            Toast.makeText(requireContext(), "Selecciona un área", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        editIdDispositivo.setText("");
        editIdDispositivo.setEnabled(true);
        editIdDispositivo.setTextColor(requireContext().getColor(R.color.text_primary));
        editNombreDispositivo.setText("");
        editDescripcionDispositivo.setText("");
        switchActivoDispositivo.setChecked(true); // Default Activo
        spinnerArea.setSelection(0);
        editIdDispositivo.requestFocus();
        
        // Resetear modo edición
        modoEdicion = false;
        dispositivoEnEdicion = null;
        btnRegistrarDispositivo.setText("Registrar Dispositivo");
        btnCancelarEdicion.setVisibility(View.GONE);
    }
    
    private void editarDispositivo(Dispositivo dispositivo) {
        // Cargar ID y deshabilitar (no se puede cambiar la clave primaria)
        editIdDispositivo.setText(String.valueOf(dispositivo.getIdDispositivo()));
        editIdDispositivo.setEnabled(false);
        editIdDispositivo.setTextColor(requireContext().getColor(R.color.text_hint));
        
        // Cargar datos en el formulario
        editNombreDispositivo.setText(dispositivo.getNombreDispositivo());
        editDescripcionDispositivo.setText(dispositivo.getDescripcionDispositivo());
        switchActivoDispositivo.setChecked("Activo".equals(dispositivo.getActivoDispositivo()));
        
        // Seleccionar el área en el spinner
        for (int i = 0; i < listaAreas.size(); i++) {
            if (listaAreas.get(i).getIdArea().equals(dispositivo.getIdArea())) {
                spinnerArea.setSelection(i);
                break;
            }
        }
        
        // Activar modo edición
        modoEdicion = true;
        dispositivoEnEdicion = dispositivo;
        btnRegistrarDispositivo.setText("Actualizar Dispositivo");
        btnCancelarEdicion.setVisibility(View.VISIBLE);
        
        // Hacer scroll al formulario
        if (getView() != null) {
            getView().findViewById(R.id.cardDatosDispositivo).requestFocus();
        }
        
        Toast.makeText(requireContext(), "Editando: " + dispositivo.getNombreDispositivo(), Toast.LENGTH_SHORT).show();
    }
    
    private void cancelarEdicion() {
        limpiarFormulario();
        Toast.makeText(requireContext(), "Edición cancelada", Toast.LENGTH_SHORT).show();
    }
    
    private void buscarDispositivos() {
        String busqueda = editBuscarDispositivo.getText().toString().trim().toLowerCase();
        
        if (busqueda.isEmpty()) {
            // Si está vacío, mostrar todos los dispositivos
            listaDispositivosFiltrada.clear();
            listaDispositivosFiltrada.addAll(listaDispositivos);
        } else {
            // Filtrar dispositivos por nombre
            listaDispositivosFiltrada.clear();
            for (Dispositivo dispositivo : listaDispositivos) {
                if (dispositivo.getNombreDispositivo().toLowerCase().contains(busqueda)) {
                    listaDispositivosFiltrada.add(dispositivo);
                }
            }
        }
        
        // Actualizar RecyclerView
        dispositivoAdapter.notifyDataSetChanged();
    }
    
    private void confirmarEliminarDispositivo(Dispositivo dispositivo) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Dispositivo")
            .setMessage("¿Estás seguro de eliminar el dispositivo '" + dispositivo.getNombreDispositivo() + "'?\n\nEsta acción no se puede deshacer.")
            .setPositiveButton("Eliminar", (dialog, which) -> eliminarDispositivo(dispositivo))
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
    
    private void eliminarDispositivo(Dispositivo dispositivo) {
        Call<Void> call = apiService.eliminarDispositivo(dispositivo.getIdDispositivo());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Dispositivo '" + dispositivo.getNombreDispositivo() + "' eliminado", Toast.LENGTH_SHORT).show();
                    
                    // Si estábamos editando este dispositivo, cancelar edición
                    if (modoEdicion && dispositivoEnEdicion != null && 
                        dispositivoEnEdicion.getIdDispositivo().equals(dispositivo.getIdDispositivo())) {
                        cancelarEdicion();
                    }
                    
                    cargarDispositivos();
                } else {
                    Toast.makeText(requireContext(), "Error al eliminar dispositivo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // -------------------------------------------------------------------------
    // ADAPTER (Clase interna)
    // -------------------------------------------------------------------------
    private class DispositivoAdapter extends RecyclerView.Adapter<DispositivoAdapter.ViewHolder> {

        private final List<Dispositivo> listaDispositivos;

        public DispositivoAdapter(List<Dispositivo> listaDispositivos) {
            this.listaDispositivos = listaDispositivos;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dispositivo, parent, false);
            return new ViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Dispositivo dispositivo = listaDispositivos.get(position);
            holder.textNombreDispositivo.setText(dispositivo.getNombreDispositivo());
            holder.textDescripcionDispositivo.setText(dispositivo.getDescripcionDispositivo());
            holder.textNombreArea.setText(dispositivo.getNombreArea() != null ? 
                    dispositivo.getNombreArea() : "Sin área");
            holder.textUbicacionArea.setText(dispositivo.getUbicacionArea() != null ? 
                    dispositivo.getUbicacionArea() : "Sin ubicación");
            
            // Configurar badge de estado
            holder.badgeEstado.setText(dispositivo.getActivoDispositivo());
            if ("Activo".equals(dispositivo.getActivoDispositivo())) {
                holder.badgeEstado.setBackgroundResource(R.drawable.item_registro_verde);
            } else {
                holder.badgeEstado.setBackgroundResource(R.drawable.item_registro_rojo);
            }
            
            // Click en Editar
            holder.btnEditar.setOnClickListener(v -> editarDispositivo(dispositivo));
            
            // Click en Eliminar
            holder.btnEliminar.setOnClickListener(v -> confirmarEliminarDispositivo(dispositivo));
        }

        @Override
        public int getItemCount() {
            return listaDispositivos.size();
        }

        // ViewHolder interno
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textNombreDispositivo, textDescripcionDispositivo, textNombreArea, textUbicacionArea, badgeEstado;
            AppCompatButton btnEditar, btnEliminar;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textNombreDispositivo = itemView.findViewById(R.id.textNombreDispositivo);
                textDescripcionDispositivo = itemView.findViewById(R.id.textDescripcionDispositivo);
                textNombreArea = itemView.findViewById(R.id.textNombreArea);
                textUbicacionArea = itemView.findViewById(R.id.textUbicacionArea);
                badgeEstado = itemView.findViewById(R.id.badgeEstado);
                btnEditar = itemView.findViewById(R.id.btnEditarDispositivo);
                btnEliminar = itemView.findViewById(R.id.btnEliminarDispositivo);
            }
        }
    }
}
