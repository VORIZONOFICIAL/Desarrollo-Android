package com.example.demo.controller;

import com.example.demo.dto.CalendarioDTO;
import com.example.demo.service.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/calendarios")
@CrossOrigin(origins = "*")
public class CalendarioController {

    @Autowired
    private CalendarioService calendarioService;

    @GetMapping
    public ResponseEntity<List<CalendarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(calendarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarioDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(calendarioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CalendarioDTO> crear(@RequestBody CalendarioDTO calendarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarioService.crear(calendarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarioDTO> actualizar(@PathVariable Integer id, @RequestBody CalendarioDTO calendarioDTO) {
        return ResponseEntity.ok(calendarioService.actualizar(id, calendarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        calendarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
