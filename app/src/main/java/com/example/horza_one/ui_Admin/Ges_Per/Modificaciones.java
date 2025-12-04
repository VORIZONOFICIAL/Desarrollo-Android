package com.example.horza_one.ui_Admin.Ges_Per;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.models.Usuario;
import com.example.horza_one.models.UsuarioRequest;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Modificaciones extends Fragment {

    // Vistas de búsqueda
    private TextInputEditText etBuscarMatricula;
    private AppCompatButton btnBuscar;
    
    // Contenedor del formulario
    private CardView itemModificacion;
    
    // Vistas del formulario de modificación
    private TextView textAvatar, textMatricula, badgeEstado, textNombreCompletoModificacion;
    private TextInputEditText editNombre, editApellidoPaterno, editApellidoMaterno;
    private TextInputEditText editRFC, editCURP;
    private TextInputEditText editTelefono, editCorreo;
    private TextInputEditText editCalle, editCodigoPostal;
    private TextInputEditText editTipoContrato;
    private SwitchCompat switchActivo;
    private AppCompatButton btnGuardarCambios, btnCancelarModificacion;
    
    // API y datos
    private ApiService apiService;
    private Usuario usuarioOriginal;

    public Modificaciones() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificaciones, container, false);
        
        // Inicializar Retrofit AQUÍ (después de que el Fragment esté adjunto)
        // Configurar Gson para NO enviar campos null (importante para preservar contraseñas)
        Gson gson = new GsonBuilder()
                // NO usar .serializeNulls() - esto hace que Gson omita campos null del JSON
                .create();
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        
        apiService = retrofit.create(ApiService.class);


        inicializarVistas(view);
        configurarListeners();
        
        return view;
    }
    
    private void inicializarVistas(View view) {
        // Vistas de búsqueda
        etBuscarMatricula = view.findViewById(R.id.editMatriculaBusqueda);
        btnBuscar = view.findViewById(R.id.btnBuscarEmpleado);
        
        // Contenedor del formulario
        itemModificacion = view.findViewById(R.id.itemModificacion);
        
        // Vistas del encabezado

        textAvatar = view.findViewById(R.id.textAvatarModificacion);
        textMatricula = view.findViewById(R.id.textMatriculaModificacion);
        badgeEstado = view.findViewById(R.id.badgeEstadoModificacion);
        textNombreCompletoModificacion = view.findViewById(R.id.textNombreCompletoModificacion);
        
        // Campos de información personal
        editNombre = view.findViewById(R.id.editNombreModificacion);
        editApellidoPaterno = view.findViewById(R.id.editApellidoPaternoModificacion);
        editApellidoMaterno = view.findViewById(R.id.editApellidoMaternoModificacion);
        
        // Campos de identificación
        editRFC = view.findViewById(R.id.editRFCModificacion);
        editCURP = view.findViewById(R.id.editCURPModificacion);
        
        // Campos de contacto
        editTelefono = view.findViewById(R.id.editTelefonoModificacion);
        editCorreo = view.findViewById(R.id.editCorreoModificacion);
        
        // Campos de dirección
        editCalle = view.findViewById(R.id.editCalleModificacion);
        editCodigoPostal = view.findViewById(R.id.editCodigoPostalModificacion);
        
        // Tipo de contrato
        editTipoContrato = view.findViewById(R.id.editTipoContrato);
        
        // Switch de estado
        switchActivo = view.findViewById(R.id.switchActivoModificacion);
        
        // Botones de acción
        btnGuardarCambios = view.findViewById(R.id.btnGuardarModificacion);
        btnCancelarModificacion = view.findViewById(R.id.btnCancelarModificacion);
        
        // Ocultar formulario inicialmente
        itemModificacion.setVisibility(View.GONE);
    }
    
    private void configurarListeners() {
        btnBuscar.setOnClickListener(v -> buscarUsuario());
        
        btnGuardarCambios.setOnClickListener(v -> {
            if (validarCampos()) {
                actualizarUsuario();
            }
        });
        
        btnCancelarModificacion.setOnClickListener(v -> {
            limpiarFormulario();
            itemModificacion.setVisibility(View.GONE);
        });
    }
    
    private void buscarUsuario() {
        String matriculaStr = etBuscarMatricula.getText().toString().trim();
        
        if (matriculaStr.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese una matrícula", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            final int matriculaBuscada = Integer.parseInt(matriculaStr);
            
            apiService.obtenerUsuarios().enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Usuario usuarioEncontrado = null;
                        for (Usuario u : response.body()) {
                            if (u.getMatricula().equals(matriculaBuscada)) {
                                usuarioEncontrado = u;
                                break;
                            }
                        }
                        
                        if (usuarioEncontrado != null) {
                            usuarioOriginal = usuarioEncontrado;
                            cargarDatosUsuario(usuarioEncontrado);
                            itemModificacion.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error al buscar usuario", Toast.LENGTH_SHORT).show();
                    }
                }
                
                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "La matrícula debe ser un número", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void cargarDatosUsuario(Usuario usuario) {
        // Encabezado
        String inicial = usuario.getNombreUsuario().substring(0, 1).toUpperCase();
        textAvatar.setText(inicial);
        textMatricula.setText("MAT-" + usuario.getMatricula());
        badgeEstado.setText(usuario.getActivo());
        textNombreCompletoModificacion.setText(usuario.getNombreUsuario() + " " + usuario.getApellidoPaternoUsuario() + " " + usuario.getApellidoMaternoUsuario());

        
        // Información personal
        editNombre.setText(usuario.getNombreUsuario());
        editApellidoPaterno.setText(usuario.getApellidoPaternoUsuario());
        editApellidoMaterno.setText(usuario.getApellidoMaternoUsuario());
        
        // Identificación
        editRFC.setText(usuario.getRfc());
        editCURP.setText(usuario.getCurp());
        
        // Contacto
        editTelefono.setText(usuario.getTelefono());
        editCorreo.setText(usuario.getCorreo());
        
        // Dirección
        editCalle.setText(usuario.getCalleUsuario());
        editCodigoPostal.setText(usuario.getCpUsuario());
        
        // Tipo de contrato
        editTipoContrato.setText(usuario.getTipoContrato());
        
        // Estado
        switchActivo.setChecked("Activo".equals(usuario.getActivo()));
    }
    
    private boolean validarCampos() {
        if (editNombre.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (editApellidoPaterno.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "El apellido paterno es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (editApellidoMaterno.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "El apellido materno es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (editRFC.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "El RFC es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (editCURP.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "El CURP es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (editCorreo.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "El correo es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private void actualizarUsuario() {
        // Crear request con datos actualizados
        UsuarioRequest request = new UsuarioRequest();
        request.setMatricula(usuarioOriginal.getMatricula());
        request.setIdRol(usuarioOriginal.getIdRol());
        request.setIdCalendario(usuarioOriginal.getIdCalendario());

        // Datos modificables
        request.setNombreUsuario(editNombre.getText().toString().trim());
        request.setApellidoPaternoUsuario(editApellidoPaterno.getText().toString().trim());
        request.setApellidoMaternoUsuario(editApellidoMaterno.getText().toString().trim());
        request.setRfc(editRFC.getText().toString().trim());
        request.setCurp(editCURP.getText().toString().trim());
        request.setTelefono(editTelefono.getText().toString().trim());
        request.setCorreo(editCorreo.getText().toString().trim());
        request.setCalleUsuario(editCalle.getText().toString().trim());
        request.setCpUsuario(editCodigoPostal.getText().toString().trim());
        request.setTipoContrato(editTipoContrato.getText().toString().trim());
        request.setActivo(switchActivo.isChecked() ? "Activo" : "Inactivo");
        
        // Mantener datos originales que no se modifican
        request.setFechaAlta(usuarioOriginal.getFechaAlta());
        // NO enviar contraseña - el backend la preservará automáticamente cuando recibe null
        // request.setContrasenia(usuarioOriginal.getContrasena());
        
        apiService.actualizarUsuario(usuarioOriginal.getMatricula(), request).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Usuario actualizado correctamente", Toast.LENGTH_LONG).show();
                    limpiarFormulario();
                    itemModificacion.setVisibility(View.GONE);
                } else {
                    Toast.makeText(requireContext(), "Error al actualizar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void limpiarFormulario() {
        etBuscarMatricula.setText("");
        editNombre.setText("");
        editApellidoPaterno.setText("");
        editApellidoMaterno.setText("");
        editRFC.setText("");
        editCURP.setText("");
        editTelefono.setText("");
        editCorreo.setText("");
        editCalle.setText("");
        editCodigoPostal.setText("");
        editTipoContrato.setText("");
        switchActivo.setChecked(true);
        usuarioOriginal = null;
    }
}