package com.example.horza_one.ui_Admin.Asis_Acc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.models.Area;
import com.example.horza_one.models.CambioContrasenaRequest;
import com.example.horza_one.models.CambioContrasenaResponse;
import com.example.horza_one.models.Dispositivo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fragmento para cambio de área de dispositivos.
 * Requiere validación de contraseña del usuario actual antes de permitir cambios.
 */
public class Asis_Acc_CA_Fragment extends Fragment {

    // Vistas
    private TextView textDispositivoActual, textAreaActual;
    private Spinner spinnerAreas;
    private AppCompatButton buttonGuardar, buttonCancelar;
    private ProgressBar progressBar;
    
    // Servicios y datos
    private ApiService apiService;
    private int dispositivoId;
    private Integer matriculaUsuario;
    private Dispositivo dispositivoActual;
    private List<Area> listaAreas;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_asis__acc__c_a_, container, false);
        
        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
        
        // Obtener matrícula del usuario logueado desde SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("HorzaPrefs", Context.MODE_PRIVATE);
        matriculaUsuario = prefs.getInt("matricula", -1);
        
        if (matriculaUsuario == -1) {
            Toast.makeText(getContext(), "Error: No se encontró la sesión del usuario", Toast.LENGTH_LONG).show();
            Navigation.findNavController(root).navigateUp();
            return root;
        }
        
        // Obtener información del dispositivo seleccionado
        if (getArguments() != null) {
            dispositivoId = getArguments().getInt("dispositivoId", -1);
        }
        
        // Inicializar vistas
        textDispositivoActual = root.findViewById(R.id.textDispositivoActual);
        textAreaActual = root.findViewById(R.id.textAreaActual);
        spinnerAreas = root.findViewById(R.id.spinnerAreas);
        buttonGuardar = root.findViewById(R.id.buttonGuardar);
        buttonCancelar = root.findViewById(R.id.buttonCancelar);
        progressBar = root.findViewById(R.id.progressBar);
        
        // Configurar botones
        buttonGuardar.setOnClickListener(v -> guardarCambioArea());
        buttonCancelar.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Cambios cancelados", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(root).navigateUp();
        });
        
        // Cargar datos
        if (dispositivoId != -1) {
            cargarInformacionDispositivo();
            cargarAreas();
        } else {
            Toast.makeText(getContext(), "Error: No se recibió el ID del dispositivo", Toast.LENGTH_LONG).show();
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
                } else {
                    Toast.makeText(getContext(), "Error al cargar dispositivo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dispositivo> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void actualizarInfoDispositivo() {
        if (dispositivoActual != null) {
            if (textDispositivoActual != null) {
                textDispositivoActual.setText(dispositivoActual.getNombreDispositivo() != null ? 
                    dispositivoActual.getNombreDispositivo() : "Dispositivo");
            }
            if (textAreaActual != null) {
                textAreaActual.setText("Área actual: " + 
                    (dispositivoActual.getNombreArea() != null ? dispositivoActual.getNombreArea() : "Sin asignar"));
            }
        }
    }
    
    private void cargarAreas() {
        Call<List<Area>> call = apiService.obtenerAreas();
        call.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaAreas = response.body();
                    configurarSpinnerAreas();
                } else {
                    Toast.makeText(getContext(), "Error al cargar áreas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void configurarSpinnerAreas() {
        if (listaAreas == null || listaAreas.isEmpty()) {
            Toast.makeText(getContext(), "No hay áreas disponibles", Toast.LENGTH_SHORT).show();
            return;
        }
        
        List<String> nombresAreas = new ArrayList<>();
        for (Area area : listaAreas) {
            nombresAreas.add(area.getNombreArea());
        }
        
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombresAreas);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAreas.setAdapter(spinnerAdapter);
        
        // Seleccionar el área actual del dispositivo
        if (dispositivoActual != null && dispositivoActual.getIdArea() != null) {
            for (int i = 0; i < listaAreas.size(); i++) {
                if (listaAreas.get(i).getIdArea().equals(dispositivoActual.getIdArea())) {
                    spinnerAreas.setSelection(i);
                    break;
                }
            }
        }
    }
    
    /**
     * Inicia el proceso de guardado solicitando primero la contraseña del usuario.
     */
    private void guardarCambioArea() {
        if (dispositivoActual == null) {
            Toast.makeText(getContext(), "Error: Dispositivo no cargado", Toast.LENGTH_SHORT).show();
            return;
        }
        
        int posicionSeleccionada = spinnerAreas.getSelectedItemPosition();
        if (posicionSeleccionada < 0 || posicionSeleccionada >= listaAreas.size()) {
            Toast.makeText(getContext(), "Seleccione un área válida", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Area areaNueva = listaAreas.get(posicionSeleccionada);
        
        // Verificar si realmente cambió el área
        if (areaNueva.getIdArea().equals(dispositivoActual.getIdArea())) {
            Toast.makeText(getContext(), "El área seleccionada es la misma que la actual", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Solicitar contraseña antes de continuar
        mostrarDialogoContrasena(areaNueva);
    }
    
    /**
     * Muestra un diálogo para ingresar la contraseña del usuario actual.
     */
    private void mostrarDialogoContrasena(final Area areaNueva) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmar cambio de área");
        builder.setMessage("Por seguridad, ingrese su contraseña para continuar:");
        
        // Crear campo de texto para la contraseña
        final EditText inputContrasena = new EditText(requireContext());
        inputContrasena.setInputType(android.text.InputType.TYPE_CLASS_TEXT | 
                                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputContrasena.setHint("Contraseña");
        builder.setView(inputContrasena);
        
        builder.setPositiveButton("Confirmar", (dialog, which) -> {
            String contrasena = inputContrasena.getText().toString().trim();
            if (contrasena.isEmpty()) {
                Toast.makeText(getContext(), "Debe ingresar su contraseña", Toast.LENGTH_SHORT).show();
            } else {
                validarContrasenaYGuardar(contrasena, areaNueva);
            }
        });
        
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
    
    /**
     * Valida la contraseña del usuario y procede con el cambio de área si es correcta.
     */
    private void validarContrasenaYGuardar(String contrasena, final Area areaNueva) {
        // Crear request de validación (reutilizamos el endpoint de cambio de contraseña solo para validar)
        CambioContrasenaRequest request = new CambioContrasenaRequest(
                matriculaUsuario,
                contrasena,
                contrasena // Enviamos la misma contraseña para validar
        );
        
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        buttonGuardar.setEnabled(false);
        
        // Validar contraseña
        Call<CambioContrasenaResponse> call = apiService.cambiarContrasena(request);
        call.enqueue(new Callback<CambioContrasenaResponse>() {
            @Override
            public void onResponse(Call<CambioContrasenaResponse> call, Response<CambioContrasenaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CambioContrasenaResponse resultado = response.body();
                    
                    if (resultado.isSuccess() || response.body().getMensaje().contains("misma")) {
                        // Contraseña correcta, proceder con el cambio de área
                        ejecutarCambioArea(areaNueva);
                    } else {
                        // Contraseña incorrecta
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        buttonGuardar.setEnabled(true);
                        Toast.makeText(getContext(), "✗ Contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                    buttonGuardar.setEnabled(true);
                    Toast.makeText(getContext(), "Error al validar contraseña", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CambioContrasenaResponse> call, Throwable t) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                buttonGuardar.setEnabled(true);
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
    /**
     * Ejecuta el cambio de área del dispositivo después de validar la contraseña.
     */
    private void ejecutarCambioArea(Area areaNueva) {
        // Actualizar el dispositivo con la nueva área
        dispositivoActual.setIdArea(areaNueva.getIdArea());
        
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        buttonGuardar.setEnabled(false);
        
        Call<Dispositivo> call = apiService.actualizarDispositivo(dispositivoId, dispositivoActual);
        call.enqueue(new Callback<Dispositivo>() {
            @Override
            public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                buttonGuardar.setEnabled(true);
                
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), 
                        "✓ Área actualizada correctamente a: " + areaNueva.getNombreArea(), 
                        Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireView()).navigateUp();
                } else {
                    Toast.makeText(getContext(), 
                        "Error al actualizar área: " + response.code(), 
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Dispositivo> call, Throwable t) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                buttonGuardar.setEnabled(true);
                Toast.makeText(getContext(), 
                    "Error de conexión: " + t.getMessage(), 
                    Toast.LENGTH_LONG).show();
            }
        });
    }
}