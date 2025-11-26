package com.example.demo.service.impl;

import com.example.demo.dto.BloqueHorarioDTO;
import com.example.demo.model.Area;
import com.example.demo.model.BloqueHorario;
import com.example.demo.model.Horario;
import com.example.demo.respository.AreaRepository;
import com.example.demo.respository.BloqueHorarioRepository;
import com.example.demo.respository.HorarioRepository;
import com.example.demo.service.BloqueHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloqueHorarioServiceImpl implements BloqueHorarioService {

    @Autowired
    private BloqueHorarioRepository bloqueHorarioRepository;
    
    @Autowired
    private HorarioRepository horarioRepository;
    
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<BloqueHorarioDTO> obtenerTodos() {
        return bloqueHorarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public BloqueHorarioDTO obtenerPorId(Integer id) {
        BloqueHorario bloque = bloqueHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque de horario no encontrado"));
        return convertirADTO(bloque);
    }

    @Override
    public BloqueHorarioDTO crear(BloqueHorarioDTO bloqueHorarioDTO) {
        BloqueHorario bloque = convertirAEntidad(bloqueHorarioDTO);
        BloqueHorario bloqueGuardado = bloqueHorarioRepository.save(bloque);
        return convertirADTO(bloqueGuardado);
    }

    @Override
    public BloqueHorarioDTO actualizar(Integer id, BloqueHorarioDTO bloqueHorarioDTO) {
        BloqueHorario bloque = bloqueHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque de horario no encontrado"));
        
        Horario horario = horarioRepository.findById(bloqueHorarioDTO.getIdHorario())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        Area area = areaRepository.findById(bloqueHorarioDTO.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        bloque.setHorario(horario);
        bloque.setArea(area);
        bloque.setNombreBloque(bloqueHorarioDTO.getNombreBloque());
        bloque.setHoraInicio(bloqueHorarioDTO.getHoraInicio());
        bloque.setHoraFin(bloqueHorarioDTO.getHoraFin());
        
        BloqueHorario bloqueActualizado = bloqueHorarioRepository.save(bloque);
        return convertirADTO(bloqueActualizado);
    }

    @Override
    public void eliminar(Integer id) {
        bloqueHorarioRepository.deleteById(id);
    }

    @Override
    public List<BloqueHorarioDTO> obtenerPorIdHorario(Integer idHorario) {
        return bloqueHorarioRepository.findByHorario_IdHorario(idHorario).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private BloqueHorarioDTO convertirADTO(BloqueHorario bloque) {
        return new BloqueHorarioDTO(
                bloque.getIdBloque(),
                bloque.getHorario().getIdHorario(),
                bloque.getArea().getIdArea(),
                bloque.getNombreBloque(),
                bloque.getHoraInicio(),
                bloque.getHoraFin()
        );
    }

    private BloqueHorario convertirAEntidad(BloqueHorarioDTO dto) {
        Horario horario = horarioRepository.findById(dto.getIdHorario())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        Area area = areaRepository.findById(dto.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
        
        return new BloqueHorario(
                dto.getIdBloque(),
                horario,
                area,
                dto.getNombreBloque(),
                dto.getHoraInicio(),
                dto.getHoraFin()
        );
    }
}
