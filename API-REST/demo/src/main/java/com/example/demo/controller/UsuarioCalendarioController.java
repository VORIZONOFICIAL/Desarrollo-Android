package com.example.demo.controller;

import com.example.demo.dto.UsuarioCalendarioCompleto;
import com.example.demo.service.UsuarioCalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios-calendario")
@CrossOrigin(origins = "*")
public class UsuarioCalendarioController {

    @Autowired
    private UsuarioCalendarioService usuarioCalendarioService;

    @GetMapping("/usuario/{matricula}/completo")
    public ResponseEntity<UsuarioCalendarioCompleto> obtenerCalendariosCompletos(@PathVariable Integer matricula) {
        return ResponseEntity.ok(usuarioCalendarioService.obtenerCalendariosCompletos(matricula));
    }
}
