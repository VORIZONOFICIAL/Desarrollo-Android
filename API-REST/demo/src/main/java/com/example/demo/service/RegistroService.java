package com.example.demo.service;

import com.example.demo.dto.ConsultaRegistrosRequest;
import com.example.demo.dto.ConsultaRegistrosResponse;
import com.example.demo.dto.RegistroDTO;
import java.util.List;

public interface RegistroService {
    List<RegistroDTO> obtenerTodos();
    RegistroDTO obtenerPorId(Integer id);
    RegistroDTO crear(RegistroDTO registroDTO);
    RegistroDTO actualizar(Integer id, RegistroDTO registroDTO);
    void eliminar(Integer id);
    ConsultaRegistrosResponse consultarRegistrosPorFiltros(ConsultaRegistrosRequest request);
}
