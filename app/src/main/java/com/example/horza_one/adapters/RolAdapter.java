package com.example.horza_one.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;
import com.example.horza_one.models.Rol;

import java.util.List;

public class RolAdapter extends RecyclerView.Adapter<RolAdapter.RolViewHolder> {

    private List<Rol> listaRoles;
    private OnRolClickListener listener;
    private int selectedPosition = -1;

    public interface OnRolClickListener {
        void onRolClick(Rol rol);
    }

    public RolAdapter(List<Rol> listaRoles, OnRolClickListener listener) {
        this.listaRoles = listaRoles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rol_dialog, parent, false);
        return new RolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RolViewHolder holder, int position) {
        Rol rol = listaRoles.get(position);
        holder.bind(rol, listener, position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return listaRoles.size();
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        
        if (previousPosition != -1) {
            notifyItemChanged(previousPosition);
        }
        if (selectedPosition != -1) {
            notifyItemChanged(selectedPosition);
        }
    }

    static class RolViewHolder extends RecyclerView.ViewHolder {
        TextView textNombreRol;
        TextView textTipoPermiso;
        ImageView iconCheck;
        ImageView iconRol;

        public RolViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombreRol = itemView.findViewById(R.id.textNombreRol);
            textTipoPermiso = itemView.findViewById(R.id.textTipoPermiso);
            iconCheck = itemView.findViewById(R.id.iconCheck);
            iconRol = itemView.findViewById(R.id.iconRol);
        }

        public void bind(Rol rol, OnRolClickListener listener, boolean isSelected) {
            // Nombre del rol
            textNombreRol.setText(rol.getNombreRol());
            
            // Tipo de permiso
            if (rol.getTipoPermiso() != null) {
                textTipoPermiso.setText(rol.getTipoPermiso().toUpperCase());
                
                // Cambiar color según el tipo de permiso
                if ("ADMIN".equalsIgnoreCase(rol.getTipoPermiso())) {
                    iconRol.setBackgroundResource(R.drawable.boton_principal_v2);
                } else {
                    iconRol.setBackgroundResource(R.drawable.boton_principal);
                }
            }
            
            // Mostrar/ocultar check según selección
            iconCheck.setVisibility(isSelected ? View.VISIBLE : View.GONE);
            
            // Click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRolClick(rol);
                }
            });
        }
    }
}
