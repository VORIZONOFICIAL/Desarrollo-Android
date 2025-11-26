package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.HorarioDTO;

public interface HorarioService {
    List<HorarioDTO> obtenerTodos();
    HorarioDTO obtenerPorId(Integer id);
    HorarioDTO crear(HorarioDTO horarioDTO);
    HorarioDTO actualizar(Integer id, HorarioDTO horarioDTO);
    void eliminar(Integer id);
}
