package com.example.demo.controller;

import com.example.demo.dto.HorarioDTO;
import com.example.demo.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(horarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(horarioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<HorarioDTO> crear(@RequestBody HorarioDTO horarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.crear(horarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioDTO> actualizar(@PathVariable Integer id, @RequestBody HorarioDTO horarioDTO) {
        return ResponseEntity.ok(horarioService.actualizar(id, horarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
