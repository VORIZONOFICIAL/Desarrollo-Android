package com.example.horza_one.ui.ui_Admin.Ges_Per;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.ApiService;
import com.example.horza_one.R;
import com.example.horza_one.adapters.CalendarioAdapter;
import com.example.horza_one.adapters.RolAdapter;
import com.example.horza_one.models.Calendario;
import com.example.horza_one.models.Rol;
import com.example.horza_one.models.Usuario;
import com.example.horza_one.models.UsuarioRequest;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class alta extends Fragment {

    // Campos del formulario
    private TextInputEditText editMatricula, editNombre, editApellidoPaterno, editApellidoMaterno;
    private TextInputEditText editFechaAlta, editRFC, editCURP;
    private TextInputEditText editTelefono, editCorreo;
    private TextInputEditText editCalle, editCodigoPostal;
    private TextInputEditText editTipoContrato, editContrasena;
    private SwitchCompat switchActivo;
    
    // Botones y texto de selección
    private AppCompatButton btnAsignarRol, btnAsignarCalendario;
    private AppCompatButton btnRegistrarAlta, btnCancelarAlta;
    private TextView textRolSeleccionado, textCalendarioSeleccionado;
    
    // Estado
    private ApiService apiService;
    private Rol rolSeleccionado;
    private Calendario calendarioSeleccionado;
    private String fechaAltaSeleccionada = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar campos del formulario
        editMatricula = view.findViewById(R.id.editMatricula);
        editNombre = view.findViewById(R.id.editNombre);
        editApellidoPaterno = view.findViewById(R.id.editApellidoPaterno);
        editApellidoMaterno = view.findViewById(R.id.editApellidoMaterno);
        editFechaAlta = view.findViewById(R.id.editFechaAlta);
        editRFC = view.findViewById(R.id.editRFC);
        editCURP = view.findViewById(R.id.editCURP);
        editTelefono = view.findViewById(R.id.editTelefono);
        editCorreo = view.findViewById(R.id.editCorreo);
        editCalle = view.findViewById(R.id.editCalle);
        editCodigoPostal = view.findViewById(R.id.editCodigoPostal);
        editTipoContrato = view.findViewById(R.id.editTipoContrato);
        editContrasena = view.findViewById(R.id.editContrasena);
        switchActivo = view.findViewById(R.id.switchActivo);
        
        // Inicializar botones y texto de selección
        btnAsignarRol = view.findViewById(R.id.btnAsignarRol);
        btnAsignarCalendario = view.findViewById(R.id.btnAsignarCalendario);
        btnRegistrarAlta = view.findViewById(R.id.btnRegistrarAlta);
        btnCancelarAlta = view.findViewById(R.id.btnCancelarAlta);
        textRolSeleccionado = view.findViewById(R.id.textRolSeleccionado);
        textCalendarioSeleccionado = view.findViewById(R.id.textCalendarioSeleccionado);

        // Inicializar Retrofit con Gson configurado para ser más leniente
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder()
                .setLenient()
                .create();
        
        // Agregar interceptor para logging
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                .addInterceptor(new com.example.horza_one.utils.LoggingInterceptor())
                .build();
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(ApiService.class);

        // Configurar DatePicker para fecha de alta
        editFechaAlta.setOnClickListener(v -> mostrarDatePicker());
        
        // Configurar botones para abrir diálogos
        btnAsignarRol.setOnClickListener(v -> mostrarDialogoRoles());
        btnAsignarCalendario.setOnClickListener(v -> mostrarDialogoCalendarios());
        
        // Configurar botón de registro
        btnRegistrarAlta.setOnClickListener(v -> registrarUsuario());
        
        // Configurar botón de cancelar
        btnCancelarAlta.setOnClickListener(v -> limpiarFormulario());
    }

    private void mostrarDialogoRoles() {
        // Crear vista personalizada del diálogo
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_seleccionar_rol, null);
        
        RecyclerView recyclerRoles = dialogView.findViewById(R.id.recyclerRoles);
        TextView textTitulo = dialogView.findViewById(R.id.textTituloDialog);
        
        recyclerRoles.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Crear y mostrar diálogo
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setCancelable(true)
                .create();

        // Cargar roles desde la API
        Call<List<Rol>> call = apiService.obtenerRoles();
        call.enqueue(new Callback<List<Rol>>() {
            @Override
            public void onResponse(Call<List<Rol>> call, Response<List<Rol>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Rol> roles = response.body();
                    
                    // Configurar adaptador
                    RolAdapter adapter = new RolAdapter(roles, rol -> {
                        // Guardar rol seleccionado
                        rolSeleccionado = rol;
                        
                        // Actualizar texto
                        textRolSeleccionado.setText("Rol: " + rol.getNombreRol() + 
                                " (" + rol.getTipoPermiso().toUpperCase() + ")");
                        
                        // Cerrar diálogo
                        dialog.dismiss();
                        
                        Toast.makeText(getContext(), "Rol asignado: " + rol.getNombreRol(), 
                                Toast.LENGTH_SHORT).show();
                    });
                    
                    recyclerRoles.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error al cargar roles", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Rol>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), 
                        Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void mostrarDialogoCalendarios() {
        // Crear vista personalizada del diálogo
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_seleccionar_calendario, null);
        
        RecyclerView recyclerCalendarios = dialogView.findViewById(R.id.recyclerCalendarios);
        TextView textTitulo = dialogView.findViewById(R.id.textTituloDialog);
        
        recyclerCalendarios.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Crear y mostrar diálogo
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setCancelable(true)
                .create();

        // Cargar calendarios desde la API
        Call<List<Calendario>> call = apiService.obtenerCalendarios();
        call.enqueue(new Callback<List<Calendario>>() {
            @Override
            public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Calendario> calendarios = response.body();
                    
                    // Log para debug
                    android.util.Log.d("CalendarioDialog", "Calendarios recibidos: " + calendarios.size());
                    
                    // Configurar adaptador
                    CalendarioAdapter adapter = new CalendarioAdapter(calendarios, calendario -> {
                        // Guardar calendario seleccionado
                        calendarioSeleccionado = calendario;
                        
                        // Formatear fechas (ya vienen como String del backend)
                        String fechaInicio = calendario.getFechaInicio() != null ? 
                                calendario.getFechaInicio() : "N/A";
                        String fechaFin = calendario.getFechaFin() != null ? 
                                calendario.getFechaFin() : "N/A";
                        
                        // Actualizar texto
                        textCalendarioSeleccionado.setText("Calendario: " + calendario.getNombreCalendario() + 
                                "\n(" + fechaInicio + " - " + fechaFin + ")");
                        
                        // Cerrar diálogo
                        dialog.dismiss();
                        
                        Toast.makeText(getContext(), "Calendario asignado: " + calendario.getNombreCalendario(), 
                                Toast.LENGTH_SHORT).show();
                    });
                    
                    recyclerCalendarios.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error al cargar calendarios", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Calendario>> call, Throwable t) {
                // Log detallado del error
                android.util.Log.e("CalendarioDialog", "Error: " + t.getClass().getName());
                android.util.Log.e("CalendarioDialog", "Mensaje: " + t.getMessage());
                if (t.getCause() != null) {
                    android.util.Log.e("CalendarioDialog", "Causa: " + t.getCause().getMessage());
                }
                t.printStackTrace();
                
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), 
                        Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public Calendario getCalendarioSeleccionado() {
        return calendarioSeleccionado;
    }
    
    private void mostrarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Formato yyyy-MM-dd para el backend
                    fechaAltaSeleccionada = String.format(Locale.getDefault(), "%04d-%02d-%02d", 
                            selectedYear, selectedMonth + 1, selectedDay);
                    
                    // Formato dd/MM/yyyy para mostrar al usuario
                    String fechaMostrar = String.format(Locale.getDefault(), "%02d/%02d/%04d", 
                            selectedDay, selectedMonth + 1, selectedYear);
                    editFechaAlta.setText(fechaMostrar);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    
    private boolean validarCampos() {
        // Validar matrícula
        String matricula = editMatricula.getText().toString().trim();
        if (matricula.isEmpty()) {
            Toast.makeText(getContext(), "La matrícula es obligatoria", Toast.LENGTH_SHORT).show();
            editMatricula.requestFocus();
            return false;
        }
        
        // Validar nombre
        String nombre = editNombre.getText().toString().trim();
        if (nombre.isEmpty()) {
            Toast.makeText(getContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            editNombre.requestFocus();
            return false;
        }
        
        // Validar apellido paterno
        String apellidoPaterno = editApellidoPaterno.getText().toString().trim();
        if (apellidoPaterno.isEmpty()) {
            Toast.makeText(getContext(), "El apellido paterno es obligatorio", Toast.LENGTH_SHORT).show();
            editApellidoPaterno.requestFocus();
            return false;
        }
        
        // Validar RFC (13 caracteres)
        String rfc = editRFC.getText().toString().trim();
        if (rfc.isEmpty() || rfc.length() != 13) {
            Toast.makeText(getContext(), "El RFC debe tener 13 caracteres", Toast.LENGTH_SHORT).show();
            editRFC.requestFocus();
            return false;
        }
        
        // Validar CURP (18 caracteres)
        String curp = editCURP.getText().toString().trim();
        if (curp.isEmpty() || curp.length() != 18) {
            Toast.makeText(getContext(), "La CURP debe tener 18 caracteres", Toast.LENGTH_SHORT).show();
            editCURP.requestFocus();
            return false;
        }
        
        // Validar fecha de alta
        if (fechaAltaSeleccionada.isEmpty()) {
            Toast.makeText(getContext(), "La fecha de alta es obligatoria", Toast.LENGTH_SHORT).show();
            editFechaAlta.requestFocus();
            return false;
        }
        
        // Validar teléfono (10 dígitos)
        String telefono = editTelefono.getText().toString().trim();
        if (telefono.isEmpty() || telefono.length() != 10) {
            Toast.makeText(getContext(), "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show();
            editTelefono.requestFocus();
            return false;
        }
        
        // Validar correo
        String correo = editCorreo.getText().toString().trim();
        if (correo.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(getContext(), "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            editCorreo.requestFocus();
            return false;
        }
        
        // Validar código postal (5 dígitos)
        String cp = editCodigoPostal.getText().toString().trim();
        if (cp.isEmpty() || cp.length() != 5) {
            Toast.makeText(getContext(), "El código postal debe tener 5 dígitos", Toast.LENGTH_SHORT).show();
            editCodigoPostal.requestFocus();
            return false;
        }
        
        // Validar contraseña
        String contrasena = editContrasena.getText().toString().trim();
        if (contrasena.isEmpty() || contrasena.length() < 6) {
            Toast.makeText(getContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            editContrasena.requestFocus();
            return false;
        }
        
        // Validar rol seleccionado
        if (rolSeleccionado == null) {
            Toast.makeText(getContext(), "Debe asignar un rol al usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        // Validar calendario seleccionado
        if (calendarioSeleccionado == null) {
            Toast.makeText(getContext(), "Debe asignar un calendario al usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private void registrarUsuario() {
        // Validar todos los campos
        if (!validarCampos()) {
            return;
        }
        
        // Construir objeto UsuarioRequest
        UsuarioRequest request = new UsuarioRequest();
        request.setMatricula(Integer.parseInt(editMatricula.getText().toString().trim()));
        request.setIdRol(rolSeleccionado.getIdRol());
        request.setIdCalendario(calendarioSeleccionado.getIdCalendario());
        request.setRfc(editRFC.getText().toString().trim().toUpperCase());
        request.setCurp(editCURP.getText().toString().trim().toUpperCase());
        request.setFechaAlta(fechaAltaSeleccionada); // formato yyyy-MM-dd
        request.setNombreUsuario(editNombre.getText().toString().trim());
        request.setApellidoPaternoUsuario(editApellidoPaterno.getText().toString().trim());
        request.setApellidoMaternoUsuario(editApellidoMaterno.getText().toString().trim());
        request.setTelefono(editTelefono.getText().toString().trim());
        request.setTipoContrato(editTipoContrato.getText().toString().trim());
        request.setCorreo(editCorreo.getText().toString().trim());
        request.setActivo(switchActivo.isChecked() ? "Activo" : "Inactivo");
        request.setCpUsuario(editCodigoPostal.getText().toString().trim());
        request.setCalleUsuario(editCalle.getText().toString().trim());
        request.setContrasenia(editContrasena.getText().toString().trim());
        
        // Mostrar progreso
        btnRegistrarAlta.setEnabled(false);
        btnRegistrarAlta.setText("Registrando...");
        
        // Llamar al endpoint POST
        Call<Usuario> call = apiService.crearUsuario(request);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                btnRegistrarAlta.setEnabled(true);
                btnRegistrarAlta.setText("Registrar Personal");
                
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    Toast.makeText(getContext(), 
                            "Usuario registrado exitosamente: " + usuario.getNombreUsuario(), 
                            Toast.LENGTH_LONG).show();
                    
                    // Limpiar formulario
                    limpiarFormulario();
                } else {
                    String errorMsg = "Error al registrar usuario";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            }
            
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                btnRegistrarAlta.setEnabled(true);
                btnRegistrarAlta.setText("Registrar Personal");
                
                android.util.Log.e("RegistroUsuario", "Error: " + t.getMessage(), t);
                Toast.makeText(getContext(), 
                        "Error de conexión: " + t.getMessage(), 
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void limpiarFormulario() {
        editMatricula.setText("");
        editNombre.setText("");
        editApellidoPaterno.setText("");
        editApellidoMaterno.setText("");
        editFechaAlta.setText("");
        editRFC.setText("");
        editCURP.setText("");
        editTelefono.setText("");
        editCorreo.setText("");
        editCalle.setText("");
        editCodigoPostal.setText("");
        editTipoContrato.setText("");
        editContrasena.setText("");
        switchActivo.setChecked(true);
        
        textRolSeleccionado.setText("Ningún rol asignado");
        textCalendarioSeleccionado.setText("Ningún calendario asignado");
        
        rolSeleccionado = null;
        calendarioSeleccionado = null;
        fechaAltaSeleccionada = "";
        
        Toast.makeText(getContext(), "Formulario limpiado", Toast.LENGTH_SHORT).show();
    }
}