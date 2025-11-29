package com.example.demo.service;

import com.example.demo.dto.BloqueHorarioDTO;
import java.util.List;

public interface BloqueHorarioService {
    List<BloqueHorarioDTO> obtenerTodos();
    BloqueHorarioDTO obtenerPorId(Integer id);
    BloqueHorarioDTO crear(BloqueHorarioDTO bloqueHorarioDTO);
    BloqueHorarioDTO actualizar(Integer id, BloqueHorarioDTO bloqueHorarioDTO);
    void eliminar(Integer id);
    List<BloqueHorarioDTO> obtenerPorIdHorario(Integer idHorario);
}
