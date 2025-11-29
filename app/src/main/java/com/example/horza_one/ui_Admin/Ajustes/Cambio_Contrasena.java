package com.example.horza_one.ui_Admin.Ajustes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.models.CambioContrasenaRequest;
import com.example.horza_one.models.CambioContrasenaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cambio_Contrasena extends Fragment {

    private EditText editContrasenaActual, editNuevaContrasena, editConfirmarContrasena;
    private AppCompatButton btnGuardarContrasena, btnCancelar;
    private ApiService apiService;
    private Integer matriculaUsuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_cambio__contrasena, container, false);

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
            Toast.makeText(requireContext(), "Error: No se encontró la sesión del usuario", Toast.LENGTH_LONG).show();
            return vista;
        }

        // Inicializar vistas
        inicializarVistas(vista);

        // Configurar listeners
        btnGuardarContrasena.setOnClickListener(v -> cambiarContrasena());
        btnCancelar.setOnClickListener(v -> limpiarFormulario());

        return vista;
    }

    private void inicializarVistas(View vista) {
        editContrasenaActual = vista.findViewById(R.id.editContrasenaActual);
        editNuevaContrasena = vista.findViewById(R.id.editNuevaContrasena);
        editConfirmarContrasena = vista.findViewById(R.id.editConfirmarContrasena);
        btnGuardarContrasena = vista.findViewById(R.id.btnGuardarContrasena);
        btnCancelar = vista.findViewById(R.id.btnCancelar);
    }

    private void cambiarContrasena() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        String contrasenaActual = editContrasenaActual.getText().toString().trim();
        String contrasenaNueva = editNuevaContrasena.getText().toString().trim();

        // Crear request
        CambioContrasenaRequest request = new CambioContrasenaRequest(
                matriculaUsuario,
                contrasenaActual,
                contrasenaNueva
        );

        // Llamar al API
        Call<CambioContrasenaResponse> call = apiService.cambiarContrasena(request);
        call.enqueue(new Callback<CambioContrasenaResponse>() {
            @Override
            public void onResponse(Call<CambioContrasenaResponse> call, Response<CambioContrasenaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CambioContrasenaResponse resultado = response.body();
                    
                    if (resultado.isSuccess()) {
                        Toast.makeText(requireContext(), 
                            "✓ " + resultado.getMensaje(), 
                            Toast.LENGTH_LONG).show();
                        limpiarFormulario();
                    } else {
                        Toast.makeText(requireContext(), 
                            "✗ " + resultado.getMensaje(), 
                            Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(requireContext(), 
                        "Error al cambiar contraseña", 
                        Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CambioContrasenaResponse> call, Throwable t) {
                Toast.makeText(requireContext(), 
                    "Error de conexión: " + t.getMessage(), 
                    Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validarCampos() {
        String contrasenaActual = editContrasenaActual.getText().toString().trim();
        String contrasenaNueva = editNuevaContrasena.getText().toString().trim();
        String confirmarContrasena = editConfirmarContrasena.getText().toString().trim();

        // Validar contraseña actual
        if (contrasenaActual.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese su contraseña actual", Toast.LENGTH_SHORT).show();
            editContrasenaActual.requestFocus();
            return false;
        }

        // Validar nueva contraseña
        if (contrasenaNueva.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese la nueva contraseña", Toast.LENGTH_SHORT).show();
            editNuevaContrasena.requestFocus();
            return false;
        }

        // Validar longitud mínima
        if (contrasenaNueva.length() < 6) {
            Toast.makeText(requireContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            editNuevaContrasena.requestFocus();
            return false;
        }

        // Validar confirmación
        if (confirmarContrasena.isEmpty()) {
            Toast.makeText(requireContext(), "Confirme la nueva contraseña", Toast.LENGTH_SHORT).show();
            editConfirmarContrasena.requestFocus();
            return false;
        }

        // Validar que las contraseñas coincidan
        if (!contrasenaNueva.equals(confirmarContrasena)) {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            editConfirmarContrasena.requestFocus();
            return false;
        }

        // Validar que la nueva contraseña sea diferente a la actual
        if (contrasenaActual.equals(contrasenaNueva)) {
            Toast.makeText(requireContext(), "La nueva contraseña debe ser diferente a la actual", Toast.LENGTH_SHORT).show();
            editNuevaContrasena.requestFocus();
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        editContrasenaActual.setText("");
        editNuevaContrasena.setText("");
        editConfirmarContrasena.setText("");
        editContrasenaActual.requestFocus();
    }
}