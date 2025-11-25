package com.example.demo.service.impl;

import com.example.demo.dto.CalendarioDTO;
import com.example.demo.model.Calendario;
import com.example.demo.respository.CalendarioRepository;
import com.example.demo.service.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarioServiceImpl implements CalendarioService {

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Override
    public List<CalendarioDTO> obtenerTodos() {
        return calendarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public CalendarioDTO obtenerPorId(Integer id) {
        Calendario calendario = calendarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));
        return convertirADTO(calendario);
    }

    @Override
    public CalendarioDTO crear(CalendarioDTO calendarioDTO) {
        Calendario calendario = convertirAEntidad(calendarioDTO);
        Calendario calendarioGuardado = calendarioRepository.save(calendario);
        return convertirADTO(calendarioGuardado);
    }

    @Override
    public CalendarioDTO actualizar(Integer id, CalendarioDTO calendarioDTO) {
        Calendario calendario = calendarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));
        
        calendario.setNombreCalendario(calendarioDTO.getNombreCalendario());
        calendario.setFechaInicio(calendarioDTO.getFechaInicio());
        calendario.setFechaFin(calendarioDTO.getFechaFin());
        calendario.setDescripcion(calendarioDTO.getDescripcion());
        calendario.setActivoCalendario(calendarioDTO.getActivoCalendario());
        
        Calendario calendarioActualizado = calendarioRepository.save(calendario);
        return convertirADTO(calendarioActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        calendarioRepository.deleteById(id);
    }

    private CalendarioDTO convertirADTO(Calendario calendario) {
        return new CalendarioDTO(
                calendario.getIdCalendario(),
                calendario.getNombreCalendario(),
                calendario.getFechaInicio(),
                calendario.getFechaFin(),
                calendario.getDescripcion(),
                calendario.getActivoCalendario()
        );
    }

    private Calendario convertirAEntidad(CalendarioDTO dto) {
        return new Calendario(
                dto.getIdCalendario(),
                dto.getNombreCalendario(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getDescripcion(),
                dto.getActivoCalendario()
        );
    }
}
