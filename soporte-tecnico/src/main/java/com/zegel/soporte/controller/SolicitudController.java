package com.zegel.soporte.controller;

import com.zegel.soporte.dto.SolicitudRequestDTO;
import com.zegel.soporte.model.EstadoSolicitud;
import com.zegel.soporte.model.Solicitud;
import com.zegel.soporte.service.ISolicitudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SolicitudController {

    private final ISolicitudService solicitudService;

    public SolicitudController(ISolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping("/solicitudes")
    public ResponseEntity<Solicitud> crear(@Valid @RequestBody SolicitudRequestDTO dto) {
        return new ResponseEntity<>(solicitudService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping("/solicitudes")
    public ResponseEntity<List<Solicitud>> listarTodas(
            @RequestParam(required = false) EstadoSolicitud estado) {
        return ResponseEntity.ok(solicitudService.listarTodas(estado));
    }

    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<Solicitud> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.obtenerPorId(id));
    }

    // Ruta jerárquica para consultar todas las solicitudes de un cliente específico
    @GetMapping("/clientes/{clienteId}/solicitudes")
    public ResponseEntity<List<Solicitud>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(solicitudService.listarPorCliente(clienteId));
    }

    @PutMapping("/solicitudes/{id}")
    public ResponseEntity<Solicitud> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudRequestDTO dto) {
        return ResponseEntity.ok(solicitudService.actualizar(id, dto));
    }

    // Ruta jerárquica para asignar un técnico a una solicitud
    @PatchMapping("/solicitudes/{id}/asignar-tecnico/{tecnicoId}")
    public ResponseEntity<Solicitud> asignarTecnico(
            @PathVariable Long id,
            @PathVariable Long tecnicoId) {
        return ResponseEntity.ok(solicitudService.asignarTecnico(id, tecnicoId));
    }

    @DeleteMapping("/solicitudes/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
