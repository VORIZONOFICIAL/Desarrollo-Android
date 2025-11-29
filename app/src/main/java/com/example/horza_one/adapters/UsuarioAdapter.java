package com.example.horza_one.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;
import com.example.horza_one.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> listaUsuarios;
    private List<Usuario> listaUsuariosFiltrada;
    private OnUsuarioClickListener listener;

    public interface OnUsuarioClickListener {
        void onUsuarioClick(Usuario usuario);
    }

    public UsuarioAdapter(List<Usuario> listaUsuarios, OnUsuarioClickListener listener) {
        this.listaUsuarios = listaUsuarios;
        this.listaUsuariosFiltrada = new ArrayList<>(listaUsuarios);
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_personal, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = listaUsuariosFiltrada.get(position);
        holder.bind(usuario, listener);
    }

    @Override
    public int getItemCount() {
        return listaUsuariosFiltrada.size();
    }

    // Método para filtrar por nombre o matrícula
    public void filtrar(String texto) {
        listaUsuariosFiltrada.clear();
        
        if (texto.isEmpty()) {
            listaUsuariosFiltrada.addAll(listaUsuarios);
        } else {
            String textoLower = texto.toLowerCase().trim();
            
            for (Usuario usuario : listaUsuarios) {
                // Buscar por matrícula
                if (String.valueOf(usuario.getMatricula()).contains(textoLower)) {
                    listaUsuariosFiltrada.add(usuario);
                    continue;
                }
                
                // Buscar por nombre completo
                String nombreCompleto = (usuario.getNombreUsuario() + " " + 
                                        usuario.getApellidoPaternoUsuario() + " " + 
                                        usuario.getApellidoMaternoUsuario()).toLowerCase();
                
                if (nombreCompleto.contains(textoLower)) {
                    listaUsuariosFiltrada.add(usuario);
                }
            }
        }
        
        notifyDataSetChanged();
    }

    // Actualizar lista completa
    public void actualizarLista(List<Usuario> nuevaLista) {
        this.listaUsuarios = nuevaLista;
        this.listaUsuariosFiltrada = new ArrayList<>(nuevaLista);
        notifyDataSetChanged();
    }

    static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView textAvatar;
        TextView textMatricula;
        TextView textNombreCompleto;
        TextView textRol;
        TextView badgeEstado;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            textAvatar = itemView.findViewById(R.id.textAvatar);
            textMatricula = itemView.findViewById(R.id.textMatricula);
            textNombreCompleto = itemView.findViewById(R.id.textNombreCompleto);
            textRol = itemView.findViewById(R.id.textRol);
            badgeEstado = itemView.findViewById(R.id.badgeEstado);
        }

        public void bind(Usuario usuario, OnUsuarioClickListener listener) {
            // Avatar con inicial del nombre
            String inicial = usuario.getNombreUsuario().substring(0, 1).toUpperCase();
            textAvatar.setText(inicial);
            
            // Matrícula
            textMatricula.setText("MAT-" + usuario.getMatricula());
            
            // Nombre completo
            String nombreCompleto = usuario.getNombreUsuario() + " " + 
                                   usuario.getApellidoPaternoUsuario() + " " + 
                                   usuario.getApellidoMaternoUsuario();
            textNombreCompleto.setText(nombreCompleto);
            
            // Rol
            if (usuario.getNombreRol() != null) {
                textRol.setText(usuario.getNombreRol());
            }
            
            // Estado
            badgeEstado.setText(usuario.getActivo());
            if ("Activo".equals(usuario.getActivo())) {
                badgeEstado.setBackgroundResource(R.drawable.item_registro_verde);
            } else {
                badgeEstado.setBackgroundResource(R.drawable.item_registro_rojo);
            }
            
            // Click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onUsuarioClick(usuario);
                }
            });
        }
    }
}
