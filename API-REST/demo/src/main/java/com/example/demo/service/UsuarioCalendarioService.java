package com.example.demo.service;

import com.example.demo.dto.UsuarioCalendarioCompleto;

public interface UsuarioCalendarioService {
    UsuarioCalendarioCompleto obtenerCalendariosCompletos(Integer matricula);
}
