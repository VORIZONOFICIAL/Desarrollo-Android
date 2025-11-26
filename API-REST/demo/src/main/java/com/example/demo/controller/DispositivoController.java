package com.example.demo.controller;

import com.example.demo.dto.DispositivoDTO;
import com.example.demo.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dispositivos")
@CrossOrigin(origins = "*")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping
    public ResponseEntity<List<DispositivoDTO>> obtenerTodos() {
        return ResponseEntity.ok(dispositivoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispositivoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(dispositivoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<DispositivoDTO> crear(@RequestBody DispositivoDTO dispositivoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dispositivoService.crear(dispositivoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DispositivoDTO> actualizar(@PathVariable Integer id, @RequestBody DispositivoDTO dispositivoDTO) {
        return ResponseEntity.ok(dispositivoService.actualizar(id, dispositivoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        dispositivoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
