package com.example.horza_one.ui_Admin.Asis_Acc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.adapters.RegistroAdapter;
import com.example.horza_one.models.Dispositivo;
import com.example.horza_one.models.Registro;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Asis_Acc_Hist_Fragment extends Fragment implements View.OnClickListener {

    private TextView textDispositivoActual, textAreaActual;
    private RecyclerView recyclerViewRegistros;
    private RegistroAdapter registroAdapter;
    private ApiService apiService;
    
    private int dispositivoId;
    private String dispositivoNombre;
    private Dispositivo dispositivoActual;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_asis__acc__hist_, container, false);
        
        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        
        // Obtener información del dispositivo seleccionado
        if (getArguments() != null) {
            dispositivoId = getArguments().getInt("dispositivoId", -1);
            dispositivoNombre = getArguments().getString("dispositivoNombre", "Desconocido");
        }
        
        // Inicializar vistas
        textDispositivoActual = root.findViewById(R.id.textDispositivoActual);
        textAreaActual = root.findViewById(R.id.textAreaActual);
        recyclerViewRegistros = root.findViewById(R.id.recyclerViewHistorial);
        
        // Configurar RecyclerView
        recyclerViewRegistros.setLayoutManager(new LinearLayoutManager(getContext()));
        registroAdapter = new RegistroAdapter(new ArrayList<>());
        recyclerViewRegistros.setAdapter(registroAdapter);
        
        // Cargar información del dispositivo
        if (dispositivoId != -1) {
            cargarInformacionDispositivo();
            cargarUltimos3Registros();
        }
        
        return root;
    }

    private void cargarInformacionDispositivo() {
        Call<Dispositivo> call = apiService.obtenerDispositivoPorId(dispositivoId);
        call.enqueue(new Callback<Dispositivo>() {
            @Override
            public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dispositivoActual = response.body();
                    actualizarInfoDispositivo();
                }
            }

            @Override
            public void onFailure(Call<Dispositivo> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar información del dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void actualizarInfoDispositivo() {
        if (dispositivoActual != null) {
            textDispositivoActual.setText(dispositivoActual.getNombreDispositivo());
            textAreaActual.setText(dispositivoActual.getNombreArea());
        }
    }
    
    private void cargarUltimos3Registros() {
        Toast.makeText(getContext(), "Cargando registros del dispositivo " + dispositivoId, Toast.LENGTH_SHORT).show();
        
        Call<List<Registro>> call = apiService.obtenerUltimos3Registros(dispositivoId);
        call.enqueue(new Callback<List<Registro>>() {
            @Override
            public void onResponse(Call<List<Registro>> call, Response<List<Registro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Registro> registros = response.body();
                    
                    Toast.makeText(getContext(), "Se encontraron " + registros.size() + " registros", Toast.LENGTH_SHORT).show();
                    
                    registroAdapter.actualizarLista(registros);
                    
                    if (registros.isEmpty()) {
                        Toast.makeText(getContext(), "No hay registros recientes", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al cargar registros. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Registro>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
    }
}