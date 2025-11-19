package com.example.horza_one.ui.ui_Admin.Ajustes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.horza_one.R;

import java.util.ArrayList;
import java.util.List;


public class Ajustes_Administracion_Areas extends Fragment {

    private RecyclerView recyclerViewAreas;
    private AreaAdapter areaAdapter;
    private List<Area> listaAreas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ajustes__administracion__areas, container, false);

        recyclerViewAreas = vista.findViewById(R.id.recyclerViewAreas);
        recyclerViewAreas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Datos de ejemplo
        listaAreas = new ArrayList<>();
        listaAreas.add(new Area("Recursos Humanos", "Edificio A, Piso 2",
                "Encargado de la gestión del personal y desarrollo del talento humano.", "Activo"));
        listaAreas.add(new Area("Finanzas", "Edificio B, Piso 1",
                "Maneja presupuestos, pagos y control financiero.", "Activo"));
        listaAreas.add(new Area("Sistemas", "Edificio C, Piso 3",
                "Área encargada del soporte técnico y sistemas internos.", "Inactivo"));

        areaAdapter = new AreaAdapter(listaAreas);
        recyclerViewAreas.setAdapter(areaAdapter);

        return vista;
    }

    // -------------------------------------------------------------------------
    // MODELO (Clase interna)
    // -------------------------------------------------------------------------
    private static class Area {
        private final String nombre;
        private final String ubicacion;
        private final String descripcion;
        private final String estado;

        public Area(String nombre, String ubicacion, String descripcion, String estado) {
            this.nombre = nombre;
            this.ubicacion = ubicacion;
            this.descripcion = descripcion;
            this.estado = estado;
        }

        public String getNombre() { return nombre; }
        public String getUbicacion() { return ubicacion; }
        public String getDescripcion() { return descripcion; }
        public String getEstado() { return estado; }
    }

    // -------------------------------------------------------------------------
    // ADAPTER (Clase interna)
    // -------------------------------------------------------------------------
    private static class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

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
            holder.textNombreArea.setText(area.getNombre());
            holder.textUbicacionArea.setText(area.getUbicacion());
            holder.textDescripcionArea.setText(area.getDescripcion());
            holder.badgeEstado.setText(area.getEstado());
        }

        @Override
        public int getItemCount() {
            return listaAreas.size();
        }

        // ViewHolder interno
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textNombreArea, textUbicacionArea, textDescripcionArea, badgeEstado;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textNombreArea = itemView.findViewById(R.id.textNombreArea);
                textUbicacionArea = itemView.findViewById(R.id.textUbicacionArea);
                textDescripcionArea = itemView.findViewById(R.id.textDescripcionArea);
                badgeEstado = itemView.findViewById(R.id.badgeEstado);
            }
        }
    }
}
