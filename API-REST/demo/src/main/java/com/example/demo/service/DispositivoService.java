package com.example.demo.service;

import com.example.demo.dto.DispositivoDTO;
import java.util.List;

public interface DispositivoService {
    List<DispositivoDTO> obtenerTodos();
    DispositivoDTO obtenerPorId(Integer id);
    List<DispositivoDTO> obtenerPorArea(Integer idArea);
    DispositivoDTO crear(DispositivoDTO dispositivoDTO);
    DispositivoDTO actualizar(Integer id, DispositivoDTO dispositivoDTO);
    void eliminar(Integer id);
}
