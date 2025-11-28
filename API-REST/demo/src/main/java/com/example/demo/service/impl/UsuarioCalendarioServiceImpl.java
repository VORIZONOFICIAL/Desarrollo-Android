package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.respository.*;
import com.example.demo.service.UsuarioCalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioCalendarioServiceImpl implements UsuarioCalendarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioCalendarioRepository usuarioCalendarioRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private BloqueHorarioRepository bloqueHorarioRepository;

    @Override
    public UsuarioCalendarioCompleto obtenerCalendariosCompletos(Integer matricula) {
        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(matricula)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        String nombreCompleto = usuario.getNombreUsuario() + " " +
                usuario.getApellidoPaternoUsuario() + " " +
                usuario.getApellidoMaternoUsuario();
        
        // Obtener calendarios asignados al usuario
        List<UsuarioCalendario> usuarioCalendarios = usuarioCalendarioRepository.findById_Matricula(matricula);
        
        List<CalendarioConHorarios> calendarios = new ArrayList<>();
        
        for (UsuarioCalendario uc : usuarioCalendarios) {
            Calendario calendario = uc.getCalendario();
            
            // Obtener horarios del calendario
            List<Horario> horarios = horarioRepository.findByCalendario_IdCalendario(calendario.getIdCalendario());
            
            List<HorarioConBloques> horariosConBloques = new ArrayList<>();
            
            for (Horario horario : horarios) {
                // Obtener bloques del horario
                List<BloqueHorario> bloques = bloqueHorarioRepository.findByHorario_IdHorario(horario.getIdHorario());
                
                List<BloqueHorarioDTO> bloquesDTO = bloques.stream()
                        .map(b -> new BloqueHorarioDTO(
                                b.getIdBloque(),
                                b.getHorario().getIdHorario(),
                                b.getArea().getIdArea(),
                                b.getNombreBloque(),
                                b.getHoraInicio(),
                                b.getHoraFin()
                        ))
                        .collect(Collectors.toList());
                
                horariosConBloques.add(new HorarioConBloques(
                        horario.getIdHorario(),
                        horario.getNombreHorario(),
                        horario.getDescripcion(),
                        bloquesDTO
                ));
            }
            
            calendarios.add(new CalendarioConHorarios(
                    calendario.getIdCalendario(),
                    calendario.getNombreCalendario(),
                    calendario.getFechaInicio(),
                    calendario.getFechaFin(),
                    horariosConBloques
            ));
        }
        
        return new UsuarioCalendarioCompleto(
                matricula,
                nombreCompleto,
                usuario.getCorreo(),
                calendarios
        );
    }
}
