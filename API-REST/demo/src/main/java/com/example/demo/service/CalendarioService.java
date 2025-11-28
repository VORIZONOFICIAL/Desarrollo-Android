package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CalendarioDTO;

public interface CalendarioService {
    List<CalendarioDTO> obtenerTodos();
    CalendarioDTO obtenerPorId(Integer id);
    CalendarioDTO crear(CalendarioDTO calendarioDTO);
    CalendarioDTO actualizar(Integer id, CalendarioDTO calendarioDTO);
    void eliminar(Integer id);
}
