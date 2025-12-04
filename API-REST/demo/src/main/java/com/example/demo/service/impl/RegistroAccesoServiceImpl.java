package com.example.demo.service.impl;

import com.example.demo.dto.RegistroAccesoRequest;
import com.example.demo.dto.RegistroAccesoResponse;
import com.example.demo.dto.RegistroDTO;
import com.example.demo.model.*;
import com.example.demo.respository.*;
import com.example.demo.service.RegistroAccesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroAccesoServiceImpl implements RegistroAccesoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private UsuarioCalendarioRepository usuarioCalendarioRepository;

    @Autowired
    private BloqueHorarioRepository bloqueHorarioRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    @Transactional
    public RegistroAccesoResponse registrarAcceso(RegistroAccesoRequest request) {
        try {
            // 1. Validar que el usuario exista
            Usuario usuario = usuarioRepository.findById(request.getMatricula())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con matrícula: " + request.getMatricula()));

            // 2. Validar que el dispositivo exista
            Dispositivo dispositivo = dispositivoRepository.findById(request.getIdDispositivo())
                    .orElseThrow(() -> new RuntimeException("Dispositivo no encontrado"));

            // 3. Verificar que el usuario esté activo
            if (!"Activo".equals(usuario.getActivo())) {
                return new RegistroAccesoResponse(false, "Usuario inactivo. No puede registrar acceso.", "", 0, null);
            }

            // 4. Obtener o crear bitácora del usuario
            Bitacora bitacora = bitacoraRepository.findByUsuario(usuario)
                    .orElseGet(() -> {
                        Bitacora nuevaBitacora = new Bitacora();
                        nuevaBitacora.setUsuario(usuario);
                        nuevaBitacora.setNumEntradas(0);
                        nuevaBitacora.setNumInasistencias(0);
                        nuevaBitacora.setNumRetardos(0);
                        nuevaBitacora.setNumEntradasAnticipadas(0);
                        return bitacoraRepository.save(nuevaBitacora);
                    });

            // 5. Determinar automáticamente el tipo de registro (Entrada o Salida)
            // según el último registro del usuario
            String tipoRegistroAutomatico = determinarTipoRegistro(usuario);
            
            // Si el request tiene tipo diferente, actualizar
            if (!request.getTipoRegistro().equals(tipoRegistroAutomatico)) {
                request.setTipoRegistro(tipoRegistroAutomatico);
            }

            // 6. Obtener la hora y fecha actual
            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();

            // 7. Obtener el calendario asignado al usuario
            List<UsuarioCalendario> usuarioCalendarios = usuarioCalendarioRepository.findById_Matricula(request.getMatricula());
            
            if (usuarioCalendarios.isEmpty()) {
                return new RegistroAccesoResponse(false, "Usuario sin calendario asignado. Contacte al administrador.", "", 0, null);
            }

            // 9. Buscar el horario activo del calendario
            UsuarioCalendario usuarioCalendario = usuarioCalendarios.get(0);
            Calendario calendario = usuarioCalendario.getCalendario();
            
            if (!"Activo".equals(calendario.getActivoCalendario())) {
                return new RegistroAccesoResponse(false, "El calendario asignado no está activo.", "", 0, null);
            }

            // 8. Obtener horarios del calendario
            List<Horario> horarios = horarioRepository.findByCalendario_IdCalendario(calendario.getIdCalendario());
            
            // Buscar el primer horario activo
            Optional<Horario> horarioActivo = horarios.stream()
                    .filter(h -> "Activo".equals(h.getActivoHorario()))
                    .findFirst();
            
            if (!horarioActivo.isPresent()) {
                return new RegistroAccesoResponse(false, "No hay horarios activos asignados al calendario.", "", 0, null);
            }
            
            // 9. Buscar bloques de horario para el área del dispositivo
            List<BloqueHorario> bloques = bloqueHorarioRepository.findByHorario_IdHorario(horarioActivo.get().getIdHorario());

            // Filtrar bloques por el área del dispositivo
            Optional<BloqueHorario> bloqueEncontrado = bloques.stream()
                    .filter(b -> b.getArea().getIdArea().equals(dispositivo.getArea().getIdArea()))
                    .findFirst();

            // 10. Determinar el estado del registro (Puntual, Retardo, Anticipado)
            String estadoRegistro = "Puntual";
            String observacion = "Registro exitoso";
            Integer minutosDiferencia = 0; // Positivo = retardo, Negativo = anticipado

            if (bloqueEncontrado.isPresent()) {
                BloqueHorario bloque = bloqueEncontrado.get();
                LocalTime horaInicio = bloque.getHoraInicio();
                LocalTime horaFin = bloque.getHoraFin();

                // Tolerancia de 10 minutos
                LocalTime inicioConTolerancia = horaInicio.minusMinutes(10);
                LocalTime finConTolerancia = horaInicio.plusMinutes(10);

                if (request.getTipoRegistro().equals("Entrada")) {
                    if (horaActual.isBefore(inicioConTolerancia)) {
                        estadoRegistro = "Anticipado";
                        observacion = "Entrada anticipada";
                        // Calcular minutos de anticipación (negativo)
                        minutosDiferencia = (int) java.time.Duration.between(horaActual, horaInicio).toMinutes();
                        minutosDiferencia = -minutosDiferencia; // Negativo para anticipado
                        bitacora.setNumEntradasAnticipadas(bitacora.getNumEntradasAnticipadas() + 1);
                    } else if (horaActual.isAfter(finConTolerancia)) {
                        estadoRegistro = "Retardo";
                        observacion = "Entrada con retardo";
                        // Calcular minutos de retardo (positivo)
                        minutosDiferencia = (int) java.time.Duration.between(horaInicio, horaActual).toMinutes();
                        bitacora.setNumRetardos(bitacora.getNumRetardos() + 1);
                    } else {
                        estadoRegistro = "Puntual";
                        observacion = "Entrada puntual";
                        minutosDiferencia = 0;
                    }
                    bitacora.setNumEntradas(bitacora.getNumEntradas() + 1);
                } else {
                    // Para salida, se registra como puntual
                    estadoRegistro = "Puntual";
                    observacion = "Salida registrada";
                    minutosDiferencia = 0;
                }
            } else {
                // Si no hay bloque horario, se registra pero como observación
                observacion = "Sin bloque horario asignado para esta área";
                minutosDiferencia = 0;
            }

            // 11. Actualizar estado_presencia del usuario según el tipo de registro
            String estadoPresenciaAnterior = usuario.getEstadoPresencia();
            if ("Entrada".equals(request.getTipoRegistro())) {
                usuario.setEstadoPresencia("Dentro");
                System.out.println("✅ Usuario " + usuario.getMatricula() + " cambiado de '" + estadoPresenciaAnterior + "' a 'Dentro'");
            } else if ("Salida".equals(request.getTipoRegistro())) {
                usuario.setEstadoPresencia("Fuera");
                System.out.println("✅ Usuario " + usuario.getMatricula() + " cambiado de '" + estadoPresenciaAnterior + "' a 'Fuera'");
            }
            usuarioRepository.save(usuario);
            System.out.println("💾 Estado de presencia guardado en BD para usuario " + usuario.getMatricula() + ": " + usuario.getEstadoPresencia());

            // 12. Actualizar bitácora
            bitacoraRepository.save(bitacora);

            // 13. Generar ID único para el registro
            Integer nuevoIdRegistro = registroRepository.findAll().stream()
                    .mapToInt(Registro::getIdRegistro)
                    .max()
                    .orElse(0) + 1;

            // 14. Crear el registro
            Registro registro = new Registro();
            registro.setIdRegistro(nuevoIdRegistro);
            registro.setUsuario(usuario);
            registro.setBitacora(bitacora);
            registro.setDispositivo(dispositivo);
            registro.setArea(dispositivo.getArea());
            registro.setTipoRegistro(request.getTipoRegistro());
            registro.setFecha(fechaActual);
            registro.setHora(horaActual);
            registro.setObservacion(observacion);
            registro.setEstadoRegistro(estadoRegistro);

            Registro registroGuardado = registroRepository.save(registro);

            // 15. Convertir a DTO y retornar
            RegistroDTO registroDTO = convertirADTO(registroGuardado);

            return new RegistroAccesoResponse(
                    true,
                    "Registro de " + request.getTipoRegistro().toLowerCase() + " exitoso. " + observacion,
                    estadoRegistro,
                    minutosDiferencia,
                    registroDTO
            );

        } catch (RuntimeException e) {
            return new RegistroAccesoResponse(false, "Error: " + e.getMessage(), null, 0, null);
        } catch (Exception e) {
            return new RegistroAccesoResponse(false, "Error inesperado al registrar acceso: " + e.getMessage(), null, 0, null);
        }
    }

    /**
     * Determina automáticamente si el siguiente registro debe ser "Entrada" o "Salida"
     * basándose en el último registro del usuario en el día actual
     */
    private String determinarTipoRegistro(Usuario usuario) {
        LocalDate hoy = LocalDate.now();
        
        // Buscar el último registro del usuario de hoy
        List<Registro> registrosHoy = registroRepository.findByUsuario_MatriculaAndFecha(
                usuario.getMatricula(), hoy);
        
        if (registrosHoy.isEmpty()) {
            // Si no hay registros hoy, debe ser una Entrada
            return "Entrada";
        }
        
        // Ordenar por hora descendente y obtener el último
        registrosHoy.sort((r1, r2) -> r2.getHora().compareTo(r1.getHora()));
        Registro ultimoRegistro = registrosHoy.get(0);
        
        // Si el último fue "Entrada", el siguiente debe ser "Salida" y viceversa
        if ("Entrada".equals(ultimoRegistro.getTipoRegistro())) {
            return "Salida";
        } else {
            return "Entrada";
        }
    }

    private RegistroDTO convertirADTO(Registro registro) {
        return new RegistroDTO(
                registro.getIdRegistro(),
                registro.getUsuario().getMatricula(),
                registro.getBitacora().getIdBitacora(),
                registro.getDispositivo().getIdDispositivo(),
                registro.getArea().getIdArea(),
                registro.getArea().getNombreArea(), // Nombre del área
                registro.getTipoRegistro(),
                registro.getFecha(),
                registro.getHora(),
                registro.getObservacion(),
                registro.getEstadoRegistro()
        );
    }
}
