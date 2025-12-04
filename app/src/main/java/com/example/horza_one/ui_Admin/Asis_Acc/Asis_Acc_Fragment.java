package com.example.horza_one.ui_Admin.Asis_Acc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.databinding.FragmentAsisAccAdminBinding;
import com.example.horza_one.adapters.RegistroAdapter;
import com.example.horza_one.models.Dispositivo;
import com.example.horza_one.models.EstadoDispositivoRequest;
import com.example.horza_one.models.Registro;
import com.example.horza_one.models.RegistroAccesoRequest;
import com.example.horza_one.models.RegistroAccesoResponse;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.cardview.widget.CardView;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Asis_Acc_Fragment extends Fragment implements View.OnClickListener {
    private FragmentAsisAccAdminBinding binding;
    private LinearLayout consultarHistorialCard, cambiarArea;
    private TextView textTime, textDate;
    private EditText editTextMatricula;
    private Button buttonRegistrar;
    private TextView textDispositivoActual, textAreaActual;
    private RecyclerView recyclerViewRegistros;
    private RegistroAdapter registroAdapter;
    private LinearLayout layoutNoRegistros;
    
    private int dispositivoId;
    private String dispositivoNombre;
    private Dispositivo dispositivoActual;
    private ApiService apiService;

    private Handler handler = new Handler();
    private SimpleDateFormat timeFormat;
    private SimpleDateFormat dateFormat;

    private Runnable timeUpdater = new Runnable() {
        @Override
        public void run() {
            updateDateTime();
            handler.postDelayed(this, 1000); // Actualizar cada segundo
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAsisAccAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        
        // Obtener información del dispositivo seleccionado
        if (getArguments() != null) {
            dispositivoId = getArguments().getInt("dispositivoId", -1);
            dispositivoNombre = getArguments().getString("dispositivoNombre", "");
        }

        Locale spanishLocale = new Locale("es", "ES");
        timeFormat = new SimpleDateFormat("HH:mm:ss", spanishLocale);
        dateFormat = new SimpleDateFormat("EEEE, dd 'de' MMMM yyyy", spanishLocale);

        textTime = binding.textTime;
        textDate = binding.textDate;
        consultarHistorialCard = binding.buttonConsultarHistorial;
        cambiarArea = binding.buttonCambiarArea;
        editTextMatricula = root.findViewById(R.id.editTextMatricula);
        buttonRegistrar = root.findViewById(R.id.buttonRegistrar);
        textDispositivoActual = root.findViewById(R.id.textDispositivoActual);
        textAreaActual = root.findViewById(R.id.textAreaActual);
        recyclerViewRegistros = root.findViewById(R.id.recyclerViewRegistros);
        layoutNoRegistros = root.findViewById(R.id.layoutNoRegistros);
        
        // Configurar RecyclerView
        recyclerViewRegistros.setLayoutManager(new LinearLayoutManager(getContext()));
        registroAdapter = new RegistroAdapter(new java.util.ArrayList<>());
        recyclerViewRegistros.setAdapter(registroAdapter);
        
        // Cargar información del dispositivo
        if (dispositivoId != -1) {
            cargarInformacionDispositivo();
            cargarUltimos3Registros();
        }

        consultarHistorialCard.setOnClickListener(this);
        cambiarArea.setOnClickListener(this);
        
        // Configurar listener del botón registrar
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarAcceso();
            }
        });

        updateDateTime();

        return root;
    }

    private void updateDateTime() {
        Date now = Calendar.getInstance().getTime();
        textTime.setText(timeFormat.format(now));
        textDate.setText(dateFormat.format(now));
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(timeUpdater);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(timeUpdater);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonConsultarHistorial.getId()) {
            // Pasar el dispositivoId al fragmento de historial
            Bundle bundle = new Bundle();
            bundle.putInt("dispositivoId", dispositivoId);
            bundle.putString("dispositivoNombre", dispositivoNombre);
            Navigation.findNavController(v).navigate(R.id.asis_Acc_Hist_Fragment, bundle);
        } else if (v.getId() == binding.buttonCambiarArea.getId()) {
            // Pasar el dispositivoId al fragmento de cambio de área
            Bundle bundle = new Bundle();
            bundle.putInt("dispositivoId", dispositivoId);
            bundle.putString("dispositivoNombre", dispositivoNombre);
            Navigation.findNavController(v).navigate(R.id.asis_Acc_CA_Fragment, bundle);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        
        // Liberar el dispositivo al salir
        if (dispositivoId != -1) {
            liberarDispositivo();
        }
        
        binding = null;
    }
    
    private void cargarInformacionDispositivo() {
        apiService.obtenerDispositivoPorId(dispositivoId).enqueue(new Callback<Dispositivo>() {
            @Override
            public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dispositivoActual = response.body();
                    actualizarInfoDispositivo();
                    // Activar el dispositivo automáticamente al entrar a la pestaña
                    activarDispositivo();
                }
            }

            @Override
            public void onFailure(Call<Dispositivo> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar información del dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void activarDispositivo() {
        EstadoDispositivoRequest estadoRequest = new EstadoDispositivoRequest("Ocupado");
        apiService.cambiarEstadoDispositivo(dispositivoId, estadoRequest).enqueue(new Callback<Dispositivo>() {
            @Override
            public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                if (response.isSuccessful()) {
                    // Dispositivo activado correctamente
                }
            }

            @Override
            public void onFailure(Call<Dispositivo> call, Throwable t) {
                // Silencioso - no es crítico mostrar el error
            }
        });
    }
    
    private void actualizarInfoDispositivo() {
        if (dispositivoActual != null) {
            textDispositivoActual.setText(dispositivoActual.getNombreDispositivo());
            if (dispositivoActual.getNombreArea() != null) {
                textAreaActual.setText(dispositivoActual.getNombreArea());
            } else {
                textAreaActual.setText("Área no asignada");
            }
        }
    }
    
    private void liberarDispositivo() {
        EstadoDispositivoRequest estadoRequest = new EstadoDispositivoRequest("Inactivo");
        
        apiService.cambiarEstadoDispositivo(dispositivoId, estadoRequest)
                .enqueue(new Callback<Dispositivo>() {
            @Override
            public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                // Dispositivo liberado exitosamente
            }

            @Override
            public void onFailure(Call<Dispositivo> call, Throwable t) {
                // Error al liberar, pero no interrumpir el flujo
            }
        });
    }
    
    private void registrarAcceso() {
        String matriculaStr = editTextMatricula.getText().toString().trim();
        
        // Validar que la matrícula no esté vacía
        if (matriculaStr.isEmpty()) {
            editTextMatricula.setError("La matrícula es obligatoria");
            editTextMatricula.requestFocus();
            Toast.makeText(getContext(), "Por favor ingrese la matrícula", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validar que solo contenga números
        if (!matriculaStr.matches("\\d+")) {
            editTextMatricula.setError("Solo se permiten números");
            editTextMatricula.requestFocus();
            Toast.makeText(getContext(), "La matrícula debe contener solo números", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validar longitud mínima
        if (matriculaStr.length() < 1) {
            editTextMatricula.setError("Matrícula demasiado corta");
            editTextMatricula.requestFocus();
            Toast.makeText(getContext(), "La matrícula debe tener al menos 1 dígito", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validar que sea un número válido
        Integer matricula;
        try {
            matricula = Integer.parseInt(matriculaStr);
            
            // Validar que sea positivo
            if (matricula <= 0) {
                editTextMatricula.setError("Matrícula inválida");
                editTextMatricula.requestFocus();
                Toast.makeText(getContext(), "La matrícula debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            editTextMatricula.setError("Número inválido");
            editTextMatricula.requestFocus();
            Toast.makeText(getContext(), "La matrícula debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validar que el dispositivo esté seleccionado
        if (dispositivoId == -1) {
            Toast.makeText(getContext(), "Error: Dispositivo no seleccionado", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Deshabilitar botón mientras se procesa
        buttonRegistrar.setEnabled(false);
        buttonRegistrar.setText("Registrando...");
        
        // Crear request de registro (tipo "Entrada" por defecto)
        RegistroAccesoRequest request = new RegistroAccesoRequest(matricula, dispositivoId, "Entrada");
        
        // Llamar al API
        apiService.registrarAcceso(request).enqueue(new Callback<RegistroAccesoResponse>() {
            @Override
            public void onResponse(Call<RegistroAccesoResponse> call, Response<RegistroAccesoResponse> response) {
                // Habilitar botón nuevamente
                buttonRegistrar.setEnabled(true);
                buttonRegistrar.setText("Registrar");
                
                if (response.isSuccessful() && response.body() != null) {
                    RegistroAccesoResponse resultado = response.body();
                    
                    if (resultado.isExito()) {
                        // Registro exitoso - Mostrar diálogo personalizado
                        mostrarDialogoRegistroExitoso(resultado);
                        
                        // Limpiar campo de matrícula y errores
                        editTextMatricula.setText("");
                        editTextMatricula.setError(null);
                        
                    } else {
                        // Error en el registro
                        Toast.makeText(getContext(), resultado.getMensaje(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al procesar el registro", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<RegistroAccesoResponse> call, Throwable t) {
                // Habilitar botón nuevamente
                buttonRegistrar.setEnabled(true);
                buttonRegistrar.setText("Registrar");
                
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void cargarUltimos3Registros() {
        Call<java.util.List<Registro>> call = apiService.obtenerUltimos3Registros(dispositivoId);
        call.enqueue(new Callback<java.util.List<Registro>>() {
            @Override
            public void onResponse(Call<java.util.List<Registro>> call, Response<java.util.List<Registro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    java.util.List<Registro> registros = response.body();
                    registroAdapter.actualizarLista(registros);
                    
                    if (registros.isEmpty()) {
                        recyclerViewRegistros.setVisibility(View.GONE);
                        layoutNoRegistros.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewRegistros.setVisibility(View.VISIBLE);
                        layoutNoRegistros.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<java.util.List<Registro>> call, Throwable t) {
                recyclerViewRegistros.setVisibility(View.GONE);
                layoutNoRegistros.setVisibility(View.VISIBLE);
            }
        });
    }
    
    private void mostrarDialogoRegistroExitoso(RegistroAccesoResponse resultado) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_registro_resultado, null);
        
        // Referencias
        LinearLayout headerEstado = dialogView.findViewById(R.id.headerEstado);
        LinearLayout badgeTipoRegistro = dialogView.findViewById(R.id.badgeTipoRegistro);
        android.widget.ImageView iconEstado = dialogView.findViewById(R.id.iconEstado);
        android.widget.ImageView iconTipoRegistro = dialogView.findViewById(R.id.iconTipoRegistro);
        TextView textEstadoTitulo = dialogView.findViewById(R.id.textEstadoTitulo);
        TextView textSubtituloEstado = dialogView.findViewById(R.id.textSubtituloEstado);
        TextView textTipoRegistro = dialogView.findViewById(R.id.textTipoRegistro);
        TextView textMatricula = dialogView.findViewById(R.id.textMatricula);
        TextView textHoraRegistro = dialogView.findViewById(R.id.textHoraRegistro);
        TextView textFechaRegistro = dialogView.findViewById(R.id.textFechaRegistro);
        CardView cardDiferenciaTiempo = dialogView.findViewById(R.id.cardDiferenciaTiempo);
        TextView textDiferenciaTiempo = dialogView.findViewById(R.id.textDiferenciaTiempo);
        TextView textTipoDiferencia = dialogView.findViewById(R.id.textTipoDiferencia);
        TextView textMensaje = dialogView.findViewById(R.id.textMensaje);
        Button buttonAceptar = dialogView.findViewById(R.id.buttonAceptar);
        
        String estado = resultado.getEstadoRegistro();
        String tipoRegistro = resultado.getRegistro() != null ? resultado.getRegistro().getTipoRegistro() : "Entrada";
        Integer minutosDiferencia = resultado.getMinutosDiferencia();
        
        // Configurar ícono y texto de tipo de registro (Entrada/Salida)
        if ("Entrada".equals(tipoRegistro)) {
            badgeTipoRegistro.setBackgroundResource(R.drawable.pill_green);
            iconTipoRegistro.setImageResource(R.drawable.ic_entrada);
            iconTipoRegistro.setColorFilter(getResources().getColor(R.color.white));
            textTipoRegistro.setText("ENTRADA");
            textTipoRegistro.setTextColor(getResources().getColor(R.color.white));
        } else {
            badgeTipoRegistro.setBackgroundResource(R.drawable.pill_orange);
            iconTipoRegistro.setImageResource(R.drawable.ic_salida);
            iconTipoRegistro.setColorFilter(getResources().getColor(R.color.white));
            textTipoRegistro.setText("SALIDA");
            textTipoRegistro.setTextColor(getResources().getColor(R.color.white));
        }
        
        // Configurar según el estado (Puntual/Retardo/Anticipado)
        if ("Puntual".equals(estado)) {
            headerEstado.setBackgroundResource(R.drawable.fondo_encabezado_azul);
            iconEstado.setImageResource(R.drawable.ic_check_circle);
            textEstadoTitulo.setText("¡Puntual!");
            
            // Subtítulo según tipo de registro
            if ("Entrada".equals(tipoRegistro)) {
                textSubtituloEstado.setText("Entrada registrada a tiempo");
            } else {
                textSubtituloEstado.setText("Salida registrada");
            }
            cardDiferenciaTiempo.setVisibility(View.GONE);
            
        } else if ("Retardo".equals(estado)) {
            headerEstado.setBackgroundColor(getResources().getColor(R.color.error_red));
            iconEstado.setImageResource(R.drawable.ic_error_circle);
            textEstadoTitulo.setText("Retardo");
            
            // Subtítulo según tipo de registro (solo Entrada puede tener retardo)
            if ("Entrada".equals(tipoRegistro)) {
                textSubtituloEstado.setText("Entrada tardía registrada");
            } else {
                textSubtituloEstado.setText("Salida registrada");
            }
            
            if (minutosDiferencia != null && minutosDiferencia > 0) {
                cardDiferenciaTiempo.setVisibility(View.VISIBLE);
                textDiferenciaTiempo.setText(minutosDiferencia + " min");
                textDiferenciaTiempo.setTextColor(getResources().getColor(R.color.error_red));
                textTipoDiferencia.setText("de retardo");
                textTipoDiferencia.setTextColor(getResources().getColor(R.color.error_red));
            }
            
        } else if ("Anticipado".equals(estado)) {
            headerEstado.setBackgroundColor(getResources().getColor(R.color.success_green));
            iconEstado.setImageResource(R.drawable.ic_check_circle);
            textEstadoTitulo.setText("Anticipado");
            
            // Subtítulo según tipo de registro (solo Entrada puede ser anticipada)
            if ("Entrada".equals(tipoRegistro)) {
                textSubtituloEstado.setText("Entrada anticipada registrada");
            } else {
                textSubtituloEstado.setText("Salida registrada");
            }
            
            if (minutosDiferencia != null) {
                cardDiferenciaTiempo.setVisibility(View.VISIBLE);
                textDiferenciaTiempo.setText(Math.abs(minutosDiferencia) + " min");
                textDiferenciaTiempo.setTextColor(getResources().getColor(R.color.success_green));
                textTipoDiferencia.setText("de anticipación");
                textTipoDiferencia.setTextColor(getResources().getColor(R.color.success_green));
            }
        }
        
        // Datos del registro
        if (resultado.getRegistro() != null) {
            textMatricula.setText("Matrícula: " + resultado.getRegistro().getMatricula());
            textHoraRegistro.setText("Hora: " + resultado.getRegistro().getHora());
            textFechaRegistro.setText("Fecha: " + resultado.getRegistro().getFecha());
        }
        
        textMensaje.setText(resultado.getMensaje());
        
        // Crear diálogo
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setCancelable(true)
                .create();
        
        buttonAceptar.setOnClickListener(v -> {
            dialog.dismiss();
            editTextMatricula.setText("");
            // Recargar los registros recientes
            cargarUltimos3Registros();
        });
        
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        
        dialog.show();
    }
}