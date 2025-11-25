package com.example.demo.controller;

import com.example.demo.dto.BitacoraDTO;
import com.example.demo.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bitacoras")
@CrossOrigin(origins = "*")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping
    public ResponseEntity<List<BitacoraDTO>> obtenerTodas() {
        return ResponseEntity.ok(bitacoraService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BitacoraDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(bitacoraService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<BitacoraDTO> crear(@RequestBody BitacoraDTO bitacoraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bitacoraService.crear(bitacoraDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BitacoraDTO> actualizar(@PathVariable Integer id, @RequestBody BitacoraDTO bitacoraDTO) {
        return ResponseEntity.ok(bitacoraService.actualizar(id, bitacoraDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        bitacoraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
