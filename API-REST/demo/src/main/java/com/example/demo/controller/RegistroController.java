package com.example.demo.controller;

import com.example.demo.dto.ConsultaRegistrosRequest;
import com.example.demo.dto.ConsultaRegistrosResponse;
import com.example.demo.dto.RegistroDTO;
import com.example.demo.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/registros")
@CrossOrigin(origins = "*")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping
    public ResponseEntity<List<RegistroDTO>> obtenerTodos() {
        return ResponseEntity.ok(registroService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(registroService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RegistroDTO> crear(@RequestBody RegistroDTO registroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroService.crear(registroDTO));
    }

    @PostMapping("/consultar")
    public ResponseEntity<ConsultaRegistrosResponse> consultarRegistros(@RequestBody ConsultaRegistrosRequest request) {
        return ResponseEntity.ok(registroService.consultarRegistrosPorFiltros(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroDTO> actualizar(@PathVariable Integer id, @RequestBody RegistroDTO registroDTO) {
        return ResponseEntity.ok(registroService.actualizar(id, registroDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        registroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
