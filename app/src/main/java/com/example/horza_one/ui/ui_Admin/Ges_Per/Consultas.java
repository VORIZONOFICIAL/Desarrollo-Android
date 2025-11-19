package com.example.horza_one.ui.ui_Admin.Ges_Per;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;
import com.example.horza_one.data.api.HorzaRepository;
import com.example.horza_one.data.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Consultas extends Fragment {

    private RecyclerView recyclerView;
    private UsuariosAdapter adapter;
    private HorzaRepository repository;
    private List<Usuario> listaUsuarios;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultas, container, false);

        // Inicializar repository
        repository = new HorzaRepository();

        // Inicializar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaUsuarios = new ArrayList<>();
        adapter = new UsuariosAdapter(listaUsuarios);
        recyclerView.setAdapter(adapter);

        // Cargar usuarios
        cargarUsuarios();

        return view;
    }

    private void cargarUsuarios() {
        // Mostrar loading (puedes agregar un ProgressBar en tu layout)
        Toast.makeText(getContext(), "Cargando usuarios...", Toast.LENGTH_SHORT).show();

        repository.getUsuarios(new HorzaRepository.OnUsuariosCallback() {
            @Override
            public void onSuccess(List<Usuario> usuarios) {
                listaUsuarios.clear();
                listaUsuarios.addAll(usuarios);
                adapter.notifyDataSetChanged();

                Toast.makeText(getContext(),
                        "Cargados " + usuarios.size() + " usuarios",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(),
                        "Error: " + message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    // Adapter básico para el RecyclerView
    private static class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

        private List<Usuario> usuarios;

        public UsuariosAdapter(List<Usuario> usuarios) {
            this.usuarios = usuarios;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Usuario usuario = usuarios.get(position);
            holder.bind(usuario);
        }

        @Override
        public int getItemCount() {
            return usuarios.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(Usuario usuario) {
                // Aquí puedes personalizar cómo se muestra cada usuario
                // Por ahora usamos un layout simple
                android.widget.TextView text1 = itemView.findViewById(android.R.id.text1);
                android.widget.TextView text2 = itemView.findViewById(android.R.id.text2);

                text1.setText(usuario.getNombre());
                text2.setText(usuario.getMatricula() + " - " + usuario.getRol());
            }
        }
    }
}
