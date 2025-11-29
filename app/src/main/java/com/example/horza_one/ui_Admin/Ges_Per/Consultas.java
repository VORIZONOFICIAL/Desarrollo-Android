package com.example.horza_one.ui_Admin.Ges_Per;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.adapters.UsuarioAdapter;
import com.example.horza_one.models.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Consultas extends Fragment {

    private RecyclerView recyclerView;
    private UsuarioAdapter adapter;
    private EditText editBusqueda;
    private TextView textTotalUsuarios;
    private ApiService apiService;
    private List<Usuario> listaUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consultas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        recyclerView = view.findViewById(R.id.recyclerResultados);
        editBusqueda = view.findViewById(R.id.editBusqueda);
        textTotalUsuarios = view.findViewById(R.id.textTotalRegistros);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listaUsuarios = new ArrayList<>();
        adapter = new UsuarioAdapter(listaUsuarios, usuario -> {
            // Click en un usuario - puedes abrir detalles
            Toast.makeText(getContext(), "Usuario: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        // Inicializar Retrofit con soporte para LocalDate
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(ApiService.class);

        // Configurar búsqueda en tiempo real
        editBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filtrar(s.toString());
                    actualizarContadorResultados(adapter.getItemCount());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Cargar usuarios
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        Call<List<Usuario>> call = apiService.obtenerUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaUsuarios = response.body();
                    adapter.actualizarLista(listaUsuarios);
                    
                    // Actualizar contador de resultados
                    actualizarContadorResultados(listaUsuarios.size());
                } else {
                    Toast.makeText(getContext(), "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void actualizarContadorResultados(int cantidad) {
        textTotalUsuarios.setText(cantidad + " usuarios");
    }
}