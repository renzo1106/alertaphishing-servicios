package com.alertaphishing.geolocalizacion.controller;

import com.alertaphishing.geolocalizacion.dto.ActualizarUbicacionRequest;
import com.alertaphishing.geolocalizacion.dto.AlertaZonaResponse;
import com.alertaphishing.geolocalizacion.dto.UbicacionResponse;
import com.alertaphishing.geolocalizacion.service.GeolocalizacionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/geolocalizacion")
public class GeolocalizacionController {

    private final GeolocalizacionService servicio;

    public GeolocalizacionController(
            GeolocalizacionService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/{usuarioId}/consentimiento")
    public UbicacionResponse otorgarConsentimiento(
            @PathVariable UUID usuarioId) {
        return servicio.otorgarConsentimiento(usuarioId);
    }

    @DeleteMapping("/{usuarioId}/consentimiento")
    public UbicacionResponse denegarConsentimiento(
            @PathVariable UUID usuarioId) {
        return servicio.denegarConsentimiento(usuarioId);
    }

    @PutMapping("/{usuarioId}/ubicacion")
    public UbicacionResponse actualizarUbicacion(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody ActualizarUbicacionRequest request) {
        return servicio.actualizarUbicacion(usuarioId, request);
    }

    @GetMapping("/{usuarioId}/ubicacion")
    public UbicacionResponse consultarUbicacion(
            @PathVariable UUID usuarioId) {
        return servicio.consultarUbicacion(usuarioId);
    }

    @PostMapping("/alertas")
    public AlertaZonaResponse generarAlerta(
            @RequestParam double latitud,
            @RequestParam double longitud,
            @RequestParam int cantidadReportes) {
        return servicio.generarAlerta(
                latitud, longitud, cantidadReportes);
    }

    @PostMapping("/alertas/cercanas")
    public AlertaZonaResponse generarAlertaCercana(
            @RequestParam double latitud,
            @RequestParam double longitud,
            @RequestParam(defaultValue = "2.0") double radioKm) {
        return servicio.generarAlertaCercana(
                latitud, longitud, radioKm);
    }

    @GetMapping("/alertas")
    public List<AlertaZonaResponse> listarAlertas() {
        return servicio.listarAlertas();
    }
}
