package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ConsultaRegistrosRequest;
import com.example.demo.dto.ConsultaRegistrosResponse;
import com.example.demo.dto.RegistroAccesoRequest;
import com.example.demo.dto.RegistroAccesoResponse;
import com.example.demo.dto.RegistroDTO;
import com.example.demo.service.RegistroAccesoService;
import com.example.demo.service.RegistroService;

@RestController
@RequestMapping("/api/registros")
@CrossOrigin(origins = "*")
public class RegistroController {

    @Autowired
    private RegistroService registroService;
    
    @Autowired
    private RegistroAccesoService registroAccesoService;

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
    
    @PostMapping("/registrar-acceso")
    public ResponseEntity<RegistroAccesoResponse> registrarAcceso(@RequestBody RegistroAccesoRequest request) {
        RegistroAccesoResponse response = registroAccesoService.registrarAcceso(request);
        
        if (response.isExito()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @GetMapping("/ultimos/{idDispositivo}")
    public ResponseEntity<List<RegistroDTO>> obtenerUltimos3(@PathVariable Integer idDispositivo) {
        return ResponseEntity.ok(registroService.obtenerUltimos3PorDispositivo(idDispositivo));
    }
    
    @GetMapping("/dispositivo/{idDispositivo}")
    public ResponseEntity<List<RegistroDTO>> obtenerTodosPorDispositivo(@PathVariable Integer idDispositivo) {
        return ResponseEntity.ok(registroService.obtenerTodosPorDispositivo(idDispositivo));
    }
}
