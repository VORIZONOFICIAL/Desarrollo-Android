package com.example.horza_one.ui_Admin.Ajustes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.models.Area;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragment del Tab de Áreas - CRUD de áreas
 */
public class AreasTabFragment extends Fragment {

    // Vistas del formulario
    private EditText editNombreArea, editDescripcionArea, editUbicacionArea;
    private SwitchCompat switchActivoArea;
    private AppCompatButton btnRegistrarArea, btnCancelarEdicion;
    
    // Búsqueda
    private EditText editBuscarArea;
    private AppCompatButton btnBuscar;
    
    // RecyclerView
    private RecyclerView recyclerViewAreas;
    private AreaAdapter areaAdapter;
    private List<Area> listaAreas;
    private List<Area> listaAreasFiltrada;
    
    // API
    private ApiService apiService;
    
    // Modo edición
    private boolean modoEdicion = false;
    private Area areaEnEdicion = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_tab_areas, container, false);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        apiService = retrofit.create(ApiService.class);

        // Inicializar vistas
        inicializarVistas(vista);
        
        // Configurar RecyclerView
        listaAreas = new ArrayList<>();
        listaAreasFiltrada = new ArrayList<>();
        recyclerViewAreas.setLayoutManager(new LinearLayoutManager(getContext()));
        areaAdapter = new AreaAdapter(listaAreasFiltrada);
        recyclerViewAreas.setAdapter(areaAdapter);
        
        // Cargar áreas desde el backend
        cargarAreas();
        
        // Configurar listener del botón
        btnRegistrarArea.setOnClickListener(v -> registrarArea());

        return vista;
    }
    
    private void inicializarVistas(View vista) {
        editNombreArea = vista.findViewById(R.id.editNombreArea);
        editDescripcionArea = vista.findViewById(R.id.editDescripcionArea);
        editUbicacionArea = vista.findViewById(R.id.editUbicacionArea);
        switchActivoArea = vista.findViewById(R.id.switchActivoArea);
        btnRegistrarArea = vista.findViewById(R.id.btnRegistrarArea);
        btnCancelarEdicion = vista.findViewById(R.id.btnCancelarEdicion);
        recyclerViewAreas = vista.findViewById(R.id.recyclerViewAreas);
        editBuscarArea = vista.findViewById(R.id.editBuscarArea);
        btnBuscar = vista.findViewById(R.id.btnBuscar);
        
        // Configurar listener del botón cancelar
        btnCancelarEdicion.setOnClickListener(v -> cancelarEdicion());
        
        // Configurar listener del botón buscar
        btnBuscar.setOnClickListener(v -> buscarAreas());
    }
    
    private void cargarAreas() {
        Call<List<Area>> call = apiService.obtenerAreas();
        call.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaAreas.clear();
                    listaAreas.addAll(response.body());
                    listaAreasFiltrada.clear();
                    listaAreasFiltrada.addAll(listaAreas);
                    areaAdapter.notifyDataSetChanged();
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
    
    private void registrarArea() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Obtener valores
        String nombre = editNombreArea.getText().toString().trim();
        String descripcion = editDescripcionArea.getText().toString().trim();
        String ubicacion = editUbicacionArea.getText().toString().trim();
        String estado = switchActivoArea.isChecked() ? "Activo" : "Inactivo";
        
        if (modoEdicion && areaEnEdicion != null) {
            // MODO EDICIÓN - Actualizar área existente
            Area areaActualizada = new Area();
            areaActualizada.setNombreArea(nombre);
            areaActualizada.setDescripcionArea(descripcion);
            areaActualizada.setUbicacion(ubicacion);
            areaActualizada.setActivoArea(estado);
            
            Call<Area> call = apiService.actualizarArea(areaEnEdicion.getIdArea(), areaActualizada);
            call.enqueue(new Callback<Area>() {
                @Override
                public void onResponse(Call<Area> call, Response<Area> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(requireContext(), "Área actualizada exitosamente", Toast.LENGTH_SHORT).show();
                        cancelarEdicion();
                        cargarAreas();
                    } else {
                        Toast.makeText(requireContext(), "Error al actualizar área", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Area> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            // MODO CREACIÓN - Crear nueva área
            Area nuevaArea = new Area();
            nuevaArea.setNombreArea(nombre);
            nuevaArea.setDescripcionArea(descripcion);
            nuevaArea.setUbicacion(ubicacion);
            nuevaArea.setActivoArea(estado);
            
            Call<Area> call = apiService.crearArea(nuevaArea);
            call.enqueue(new Callback<Area>() {
                @Override
                public void onResponse(Call<Area> call, Response<Area> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(requireContext(), "Área registrada exitosamente", Toast.LENGTH_SHORT).show();
                        limpiarFormulario();
                        cargarAreas();
                    } else {
                        Toast.makeText(requireContext(), "Error al registrar área", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Area> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    
    private boolean validarCampos() {
        if (editNombreArea.getText().toString().trim().isEmpty()) {
            editNombreArea.setError("El nombre es obligatorio");
            editNombreArea.requestFocus();
            return false;
        }
        
        if (editDescripcionArea.getText().toString().trim().isEmpty()) {
            editDescripcionArea.setError("La descripción es obligatoria");
            editDescripcionArea.requestFocus();
            return false;
        }
        
        if (editUbicacionArea.getText().toString().trim().isEmpty()) {
            editUbicacionArea.setError("La ubicación es obligatoria");
            editUbicacionArea.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        editNombreArea.setText("");
        editDescripcionArea.setText("");
        editUbicacionArea.setText("");
        switchActivoArea.setChecked(true);
        editNombreArea.requestFocus();
        
        // Resetear modo edición
        modoEdicion = false;
        areaEnEdicion = null;
        btnRegistrarArea.setText("Registrar Área");
        btnCancelarEdicion.setVisibility(View.GONE);
    }
    
    private void editarArea(Area area) {
        // Cargar datos en el formulario
        editNombreArea.setText(area.getNombreArea());
        editDescripcionArea.setText(area.getDescripcionArea());
        editUbicacionArea.setText(area.getUbicacion());
        switchActivoArea.setChecked("Activo".equals(area.getActivoArea()));
        
        // Activar modo edición
        modoEdicion = true;
        areaEnEdicion = area;
        btnRegistrarArea.setText("Actualizar Área");
        btnCancelarEdicion.setVisibility(View.VISIBLE);
        
        // Hacer scroll al formulario
        if (getView() != null) {
            getView().findViewById(R.id.cardDatosArea).requestFocus();
        }
        
        Toast.makeText(requireContext(), "Editando: " + area.getNombreArea(), Toast.LENGTH_SHORT).show();
    }
    
    private void cancelarEdicion() {
        limpiarFormulario();
        Toast.makeText(requireContext(), "Edición cancelada", Toast.LENGTH_SHORT).show();
    }
    
    private void buscarAreas() {
        String busqueda = editBuscarArea.getText().toString().trim().toLowerCase();
        
        if (busqueda.isEmpty()) {
            // Si está vacío, mostrar todas las áreas
            listaAreasFiltrada.clear();
            listaAreasFiltrada.addAll(listaAreas);
        } else {
            // Filtrar áreas por nombre
            listaAreasFiltrada.clear();
            for (Area area : listaAreas) {
                if (area.getNombreArea().toLowerCase().contains(busqueda)) {
                    listaAreasFiltrada.add(area);
                }
            }
        }
        
        // Actualizar RecyclerView
        areaAdapter.notifyDataSetChanged();
    }
    
    private void confirmarEliminarArea(Area area) {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Área")
            .setMessage("¿Estás seguro de eliminar el área '" + area.getNombreArea() + "'?\n\nEsta acción no se puede deshacer.")
            .setPositiveButton("Eliminar", (dialog, which) -> eliminarArea(area))
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
    
    private void eliminarArea(Area area) {
        Call<Void> call = apiService.eliminarArea(area.getIdArea());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Área '" + area.getNombreArea() + "' eliminada", Toast.LENGTH_SHORT).show();
                    
                    // Si estábamos editando esta área, cancelar edición
                    if (modoEdicion && areaEnEdicion != null && 
                        areaEnEdicion.getIdArea().equals(area.getIdArea())) {
                        cancelarEdicion();
                    }
                    
                    cargarAreas();
                } else {
                    Toast.makeText(requireContext(), "Error al eliminar área", Toast.LENGTH_SHORT).show();
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
    private class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

        private final List<Area> listaAreas;

        public AreaAdapter(List<Area> listaAreas) {
            this.listaAreas = listaAreas;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_area, parent, false);
            return new ViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Area area = listaAreas.get(position);
            holder.textNombreArea.setText(area.getNombreArea());
            holder.textUbicacionArea.setText(area.getUbicacion());
            holder.textDescripcionArea.setText(area.getDescripcionArea());
            holder.badgeEstado.setText(area.getActivoArea());
            
            // Cambiar color del badge según el estado
            if ("Activo".equals(area.getActivoArea())) {
                holder.badgeEstado.setBackgroundResource(R.drawable.item_registro_verde);
            } else {
                holder.badgeEstado.setBackgroundResource(R.drawable.item_registro_rojo);
            }
            
            // Click en Editar
            holder.btnEditar.setOnClickListener(v -> editarArea(area));
            
            // Click en Eliminar
            holder.btnEliminar.setOnClickListener(v -> confirmarEliminarArea(area));
        }

        @Override
        public int getItemCount() {
            return listaAreas.size();
        }

        // ViewHolder interno
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textNombreArea, textUbicacionArea, textDescripcionArea, badgeEstado;
            AppCompatButton btnEditar, btnEliminar;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textNombreArea = itemView.findViewById(R.id.textNombreArea);
                textUbicacionArea = itemView.findViewById(R.id.textUbicacionArea);
                textDescripcionArea = itemView.findViewById(R.id.textDescripcionArea);
                badgeEstado = itemView.findViewById(R.id.badgeEstado);
                btnEditar = itemView.findViewById(R.id.btnEditarArea);
                btnEliminar = itemView.findViewById(R.id.btnEliminarArea);
            }
        }
    }
}
