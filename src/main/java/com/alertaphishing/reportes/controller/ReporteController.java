package com.alertaphishing.reportes.controller;

import com.alertaphishing.reportes.dto.RegistrarReporteRequest;
import com.alertaphishing.reportes.dto.ReporteResponse;
import com.alertaphishing.reportes.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    private final ReporteService servicio;

    public ReporteController(ReporteService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ReporteResponse registrar(
            @Valid @RequestBody RegistrarReporteRequest request) {
        return servicio.registrar(request);
    }

    @GetMapping
    public List<ReporteResponse> listarTodos() {
        return servicio.listarTodos();
    }

    @GetMapping("/{id}")
    public ReporteResponse buscarPorId(@PathVariable UUID id) {
        return servicio.buscarPorId(id);
    }

    @PatchMapping("/{id}/calificacion")
    public ReporteResponse calificar(
            @PathVariable UUID id,
            @RequestParam boolean veraz) {
        return servicio.calificar(id, veraz);
    }
}
