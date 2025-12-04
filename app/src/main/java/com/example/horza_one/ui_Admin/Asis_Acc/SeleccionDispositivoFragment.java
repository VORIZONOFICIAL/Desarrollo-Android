package com.example.horza_one.ui_Admin.Asis_Acc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.models.Dispositivo;
import com.example.horza_one.models.EstadoDispositivoRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeleccionDispositivoFragment extends Fragment {

    private RecyclerView recyclerViewDispositivos;
    private ProgressBar progressBar;
    private TextView textNoDispositivos;
    
    private DispositivoAdapter dispositivoAdapter;
    private List<Dispositivo> listaDispositivos;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_seleccion_dispositivo, container, false);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Inicializar vistas
        recyclerViewDispositivos = vista.findViewById(R.id.recyclerViewDispositivos);
        progressBar = vista.findViewById(R.id.progressBar);
        textNoDispositivos = vista.findViewById(R.id.textNoDispositivos);

        // Configurar RecyclerView
        listaDispositivos = new ArrayList<>();
        dispositivoAdapter = new DispositivoAdapter(listaDispositivos);
        recyclerViewDispositivos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDispositivos.setAdapter(dispositivoAdapter);

        // Cargar dispositivos inactivos
        cargarDispositivosInactivos();

        return vista;
    }

    private void cargarDispositivosInactivos() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewDispositivos.setVisibility(View.GONE);
        textNoDispositivos.setVisibility(View.GONE);

        apiService.obtenerDispositivosInactivos().enqueue(new Callback<List<Dispositivo>>() {
            @Override
            public void onResponse(Call<List<Dispositivo>> call, Response<List<Dispositivo>> response) {
                progressBar.setVisibility(View.GONE);
                
                if (response.isSuccessful() && response.body() != null) {
                    listaDispositivos.clear();
                    listaDispositivos.addAll(response.body());
                    
                    if (listaDispositivos.isEmpty()) {
                        textNoDispositivos.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewDispositivos.setVisibility(View.VISIBLE);
                        dispositivoAdapter.notifyDataSetChanged();
                    }
                } else {
                    textNoDispositivos.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Error al cargar dispositivos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dispositivo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                textNoDispositivos.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void seleccionarDispositivo(Dispositivo dispositivo) {
        // Verificar que el dispositivo esté inactivo
        if (!"Inactivo".equals(dispositivo.getActivoDispositivo())) {
            Toast.makeText(getContext(), "Este dispositivo está siendo ocupado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cambiar estado del dispositivo a Activo
        progressBar.setVisibility(View.VISIBLE);
        EstadoDispositivoRequest estadoRequest = new EstadoDispositivoRequest("Activo");
        
        apiService.cambiarEstadoDispositivo(dispositivo.getIdDispositivo(), estadoRequest)
                .enqueue(new Callback<Dispositivo>() {
            @Override
            public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                progressBar.setVisibility(View.GONE);
                
                if (response.isSuccessful()) {
                    // Guardar el ID del dispositivo seleccionado
                    Bundle bundle = new Bundle();
                    bundle.putInt("dispositivoId", dispositivo.getIdDispositivo());
                    bundle.putString("dispositivoNombre", dispositivo.getNombreDispositivo());
                    
                    // Navegar al módulo de accesos
                    Navigation.findNavController(getView())
                            .navigate(R.id.asis_Acc_Fragment, bundle);
                } else {
                    Toast.makeText(getContext(), "Error al activar el dispositivo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dispositivo> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // -------------------------------------------------------------------------
    // ADAPTER (Clase interna)
    // -------------------------------------------------------------------------
    private class DispositivoAdapter extends RecyclerView.Adapter<DispositivoAdapter.ViewHolder> {
        private List<Dispositivo> dispositivos;

        public DispositivoAdapter(List<Dispositivo> dispositivos) {
            this.dispositivos = dispositivos;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dispositivo_seleccion, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Dispositivo dispositivo = dispositivos.get(position);
            holder.bind(dispositivo);
        }

        @Override
        public int getItemCount() {
            return dispositivos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textNombreDispositivo;
            TextView textAreaDispositivo;
            TextView textUbicacionDispositivo;
            TextView textEstado;
            CardView cardDispositivo;

            ViewHolder(View itemView) {
                super(itemView);
                textNombreDispositivo = itemView.findViewById(R.id.textNombreDispositivo);
                textAreaDispositivo = itemView.findViewById(R.id.textAreaDispositivo);
                textUbicacionDispositivo = itemView.findViewById(R.id.textUbicacionDispositivo);
                textEstado = itemView.findViewById(R.id.textEstado);
                cardDispositivo = itemView.findViewById(R.id.cardDispositivo);
            }

            void bind(Dispositivo dispositivo) {
                textNombreDispositivo.setText(dispositivo.getNombreDispositivo());
                textAreaDispositivo.setText("Área: " + dispositivo.getNombreArea());
                textUbicacionDispositivo.setText("Ubicación: " + dispositivo.getUbicacionArea());
                
                // Mostrar estado (aunque debería ser Inactivo)
                if ("Inactivo".equals(dispositivo.getActivoDispositivo())) {
                    textEstado.setText("Disponible");
                    textEstado.setTextColor(getResources().getColor(R.color.success));
                } else {
                    textEstado.setText("Ocupado");
                    textEstado.setTextColor(getResources().getColor(R.color.error));
                }

                // Click para seleccionar
                cardDispositivo.setOnClickListener(v -> seleccionarDispositivo(dispositivo));
            }
        }
    }
}
