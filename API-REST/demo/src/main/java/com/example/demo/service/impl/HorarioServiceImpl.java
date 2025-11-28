package com.example.demo.service.impl;

import com.example.demo.dto.HorarioDTO;
import com.example.demo.model.Calendario;
import com.example.demo.model.Horario;
import com.example.demo.respository.CalendarioRepository;
import com.example.demo.respository.HorarioRepository;
import com.example.demo.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private CalendarioRepository calendarioRepository;

    @Override
    public List<HorarioDTO> obtenerTodos() {
        return horarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public HorarioDTO obtenerPorId(Integer id) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        return convertirADTO(horario);
    }

    @Override
    public HorarioDTO crear(HorarioDTO horarioDTO) {
        Horario horario = convertirAEntidad(horarioDTO);
        Horario horarioGuardado = horarioRepository.save(horario);
        return convertirADTO(horarioGuardado);
    }

    @Override
    public HorarioDTO actualizar(Integer id, HorarioDTO horarioDTO) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        
        Calendario calendario = calendarioRepository.findById(horarioDTO.getIdCalendario())
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));
        
        horario.setCalendario(calendario);
        horario.setNombreHorario(horarioDTO.getNombreHorario());
        horario.setDescripcion(horarioDTO.getDescripcion());
        horario.setActivoHorario(horarioDTO.getActivoHorario());
        
        Horario horarioActualizado = horarioRepository.save(horario);
        return convertirADTO(horarioActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        horarioRepository.deleteById(id);
    }

    private HorarioDTO convertirADTO(Horario horario) {
        return new HorarioDTO(
                horario.getIdHorario(),
                horario.getCalendario().getIdCalendario(),
                horario.getNombreHorario(),
                horario.getDescripcion(),
                horario.getActivoHorario()
        );
    }

    private Horario convertirAEntidad(HorarioDTO dto) {
        Calendario calendario = calendarioRepository.findById(dto.getIdCalendario())
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));
        
        return new Horario(
                dto.getIdHorario(),
                calendario,
                dto.getNombreHorario(),
                dto.getDescripcion(),
                dto.getActivoHorario()
        );
    }
}
