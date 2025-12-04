package com.example.horza_one.ui_Admin.Ges_Per;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.models.BajaResponse;
import com.example.horza_one.models.BajaUsuarioRequest;
import com.example.horza_one.models.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Baja extends Fragment {

    private TextInputEditText etMatricula, etMotivo, etContrasena;
    private TextView tvNombre, tvRol;
    private LinearLayout layoutInfoEmpleado;
    private AppCompatButton btnEliminar, btnCancelar;
    
    private ApiService apiService;
    private Usuario usuarioEncontrado;
    private Integer matriculaAdmin;

    public Baja() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        apiService = retrofit.create(ApiService.class);
        
        // Obtener la matrícula del administrador actual desde SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("HorzaPrefs", Context.MODE_PRIVATE);
        matriculaAdmin = prefs.getInt("matricula", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baja, container, false);
        
        inicializarVistas(view);
        configurarListeners();
        
        return view;
    }

    private void inicializarVistas(View view) {
        etMatricula = view.findViewById(R.id.editMatricula);
        etMotivo = view.findViewById(R.id.editMotivo);
        etContrasena = view.findViewById(R.id.editPassword);
        
        tvNombre = view.findViewById(R.id.textNombreEmpleado);
        tvRol = view.findViewById(R.id.textRolEmpleado);
        TextView tvEstado = view.findViewById(R.id.textEstadoEmpleado);
        
        layoutInfoEmpleado = view.findViewById(R.id.layoutInfoEmpleado);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        
        // Ocultar información del empleado al inicio
        layoutInfoEmpleado.setVisibility(View.GONE);
    }

    private void configurarListeners() {
        // Buscar usuario cuando cambia la matrícula
        etMatricula.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && etMatricula.getText() != null && !etMatricula.getText().toString().isEmpty()) {
                buscarUsuario();
            }
        });
        
        btnEliminar.setOnClickListener(v -> {
            if (validarCampos()) {
                eliminarUsuario();
            }
        });
        
        btnCancelar.setOnClickListener(v -> {
            limpiarFormulario();
        });
    }

    private void buscarUsuario() {
        String matriculaStr = etMatricula.getText().toString().trim();
        
        if (matriculaStr.isEmpty()) {
            return;
        }
        
        try {
            Integer matricula = Integer.parseInt(matriculaStr);
            
            // Buscar el usuario en la lista de usuarios
            apiService.obtenerUsuarios().enqueue(new Callback<java.util.List<Usuario>>() {
                @Override
                public void onResponse(Call<java.util.List<Usuario>> call, Response<java.util.List<Usuario>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Buscar el usuario con esa matrícula
                        for (Usuario u : response.body()) {
                            if (u.getMatricula().equals(matricula)) {
                                usuarioEncontrado = u;
                                mostrarInformacionUsuario(u);
                                return;
                            }
                        }
                        // No se encontró
                        Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        layoutInfoEmpleado.setVisibility(View.GONE);
                        usuarioEncontrado = null;
                    }
                }

                @Override
                public void onFailure(Call<java.util.List<Usuario>> call, Throwable t) {
                    Toast.makeText(getContext(), "Error al buscar usuario: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Matrícula inválida", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        layoutInfoEmpleado.setVisibility(View.VISIBLE);
        
        // Mostrar nombre completo
        String nombreCompleto = usuario.getNombreUsuario() + " " + 
                                usuario.getApellidoPaternoUsuario() + " " + 
                                usuario.getApellidoMaternoUsuario();
        tvNombre.setText(nombreCompleto);
        
        // Mostrar rol
        String rolText = "Rol: " + (usuario.getNombreRol() != null ? usuario.getNombreRol() : "ID: " + usuario.getIdRol());
        tvRol.setText(rolText);
    }

    private boolean validarCampos() {
        if (usuarioEncontrado == null) {
            Toast.makeText(getContext(), "Debe buscar un usuario válido primero", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (etMotivo.getText() == null || etMotivo.getText().toString().trim().isEmpty()) {
            etMotivo.setError("El motivo es obligatorio");
            etMotivo.requestFocus();
            return false;
        }
        
        if (etContrasena.getText() == null || etContrasena.getText().toString().trim().isEmpty()) {
            etContrasena.setError("Debe ingresar su contraseña");
            etContrasena.requestFocus();
            return false;
        }
        
        if (matriculaAdmin == -1) {
            Toast.makeText(getContext(), "Error: No se pudo obtener la sesión del administrador", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }

    private void eliminarUsuario() {
        String contrasenaAdmin = etContrasena.getText().toString().trim();
        String motivo = etMotivo.getText().toString().trim();
        
        BajaUsuarioRequest request = new BajaUsuarioRequest(
                usuarioEncontrado.getMatricula(),
                matriculaAdmin,
                contrasenaAdmin,
                motivo
        );
        
        btnEliminar.setEnabled(false);
        
        Call<BajaResponse> call = apiService.eliminarUsuarioConValidacion(request);
        call.enqueue(new Callback<BajaResponse>() {
            @Override
            public void onResponse(Call<BajaResponse> call, Response<BajaResponse> response) {
                btnEliminar.setEnabled(true);
                
                if (response.isSuccessful() && response.body() != null) {
                    BajaResponse bajaResponse = response.body();
                    
                    if (bajaResponse.isSuccess()) {
                        Toast.makeText(getContext(), "Usuario eliminado correctamente", Toast.LENGTH_LONG).show();
                        limpiarFormulario();
                    } else {
                        Toast.makeText(getContext(), bajaResponse.getMensaje(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al eliminar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BajaResponse> call, Throwable t) {
                btnEliminar.setEnabled(true);
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarFormulario() {
        etMatricula.setText("");
        etMotivo.setText("");
        etContrasena.setText("");
        layoutInfoEmpleado.setVisibility(View.GONE);
        usuarioEncontrado = null;
    }
}