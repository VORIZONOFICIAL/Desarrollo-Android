package com.example.demo.controller;

import com.example.demo.dto.BloqueHorarioDTO;
import com.example.demo.service.BloqueHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bloques-horarios")
@CrossOrigin(origins = "*")
public class BloqueHorarioController {

    @Autowired
    private BloqueHorarioService bloqueHorarioService;

    @GetMapping
    public ResponseEntity<List<BloqueHorarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(bloqueHorarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloqueHorarioDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(bloqueHorarioService.obtenerPorId(id));
    }

    @GetMapping("/horario/{idHorario}")
    public ResponseEntity<List<BloqueHorarioDTO>> obtenerPorIdHorario(@PathVariable Integer idHorario) {
        return ResponseEntity.ok(bloqueHorarioService.obtenerPorIdHorario(idHorario));
    }

    @PostMapping
    public ResponseEntity<BloqueHorarioDTO> crear(@RequestBody BloqueHorarioDTO bloqueHorarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bloqueHorarioService.crear(bloqueHorarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloqueHorarioDTO> actualizar(@PathVariable Integer id, @RequestBody BloqueHorarioDTO bloqueHorarioDTO) {
        return ResponseEntity.ok(bloqueHorarioService.actualizar(id, bloqueHorarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        bloqueHorarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
