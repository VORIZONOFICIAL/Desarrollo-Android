package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CambioContrasenaRequest;
import com.example.demo.dto.UsuarioDTO;

public interface UsuarioService {
    List<UsuarioDTO> obtenerTodos();
    UsuarioDTO obtenerPorId(Integer matricula);
    UsuarioDTO crear(UsuarioDTO usuarioDTO);
    UsuarioDTO actualizar(Integer matricula, UsuarioDTO usuarioDTO);
    void eliminar(Integer matricula);
    boolean cambiarContrasena(CambioContrasenaRequest request);
}
