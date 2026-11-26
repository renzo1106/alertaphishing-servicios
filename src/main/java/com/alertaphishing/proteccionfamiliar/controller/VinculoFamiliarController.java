package com.alertaphishing.proteccionfamiliar.controller;

import com.alertaphishing.proteccionfamiliar.dto.CrearVinculoRequest;
import com.alertaphishing.proteccionfamiliar.dto.VinculoFamiliarResponse;
import com.alertaphishing.proteccionfamiliar.service.VinculoFamiliarService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/proteccion-familiar")
public class VinculoFamiliarController {

    private final VinculoFamiliarService servicio;

    public VinculoFamiliarController(
            VinculoFamiliarService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/solicitudes")
    public VinculoFamiliarResponse crearSolicitud(
            @Valid @RequestBody CrearVinculoRequest request) {
        return servicio.crearSolicitud(request);
    }

    @PatchMapping("/solicitudes/{id}/aceptar")
    public VinculoFamiliarResponse aceptarSolicitud(
            @PathVariable UUID id) {
        return servicio.aceptarSolicitud(id);
    }

    @PatchMapping("/solicitudes/{id}/rechazar")
    public VinculoFamiliarResponse rechazarSolicitud(
            @PathVariable UUID id) {
        return servicio.rechazarSolicitud(id);
    }

    @DeleteMapping("/vinculos/{id}")
    public VinculoFamiliarResponse eliminarVinculo(
            @PathVariable UUID id) {
        return servicio.eliminarVinculo(id);
    }

    @GetMapping("/usuarios/{usuarioId}/vinculos")
    public List<VinculoFamiliarResponse> listarPorUsuario(
            @PathVariable UUID usuarioId) {
        return servicio.listarPorUsuario(usuarioId);
    }

    @GetMapping("/usuarios/{usuarioId}/vinculos-aceptados")
    public List<VinculoFamiliarResponse> listarAceptados(
            @PathVariable UUID usuarioId) {
        return servicio.listarAceptados(usuarioId);
    }
}
