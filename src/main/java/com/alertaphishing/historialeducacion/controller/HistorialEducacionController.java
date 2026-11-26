package com.alertaphishing.historialeducacion.controller;

import com.alertaphishing.historialeducacion.dto.*;
import com.alertaphishing.historialeducacion.service.HistorialEducacionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/historial-educacion")
public class HistorialEducacionController {

    private final HistorialEducacionService servicio;

    public HistorialEducacionController(
            HistorialEducacionService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/historial")
    public HistorialVerificacionResponse registrarHistorial(
            @Valid @RequestBody RegistrarHistorialRequest request) {
        return servicio.registrarHistorial(request);
    }

    @GetMapping("/usuarios/{usuarioId}/historial")
    public List<HistorialVerificacionResponse> listarHistorial(
            @PathVariable UUID usuarioId) {
        return servicio.listarHistorial(usuarioId);
    }

    @GetMapping("/historial/{id}")
    public HistorialVerificacionResponse buscarHistorial(
            @PathVariable UUID id) {
        return servicio.buscarHistorial(id);
    }

    @GetMapping("/contenidos")
    public List<ContenidoEducativoResponse> listarContenidos() {
        return servicio.listarContenidos();
    }

    @GetMapping("/contenidos/categoria/{categoria}")
    public List<ContenidoEducativoResponse> listarPorCategoria(
            @PathVariable String categoria) {
        return servicio.listarPorCategoria(categoria);
    }

    @GetMapping("/contenidos/{id}")
    public ContenidoEducativoResponse buscarContenido(
            @PathVariable UUID id) {
        return servicio.buscarContenido(id);
    }
}
