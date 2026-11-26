package com.alertaphishing.notificaciones.controller;

import com.alertaphishing.notificaciones.dto.CrearNotificacionRequest;
import com.alertaphishing.notificaciones.dto.NotificacionResponse;
import com.alertaphishing.notificaciones.service.NotificacionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService servicio;

    public NotificacionController(
            NotificacionService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public NotificacionResponse crearNotificacion(
            @Valid @RequestBody CrearNotificacionRequest request) {
        return servicio.crearNotificacion(request);
    }

    @GetMapping("/usuarios/{usuarioId}")
    public List<NotificacionResponse> listarPorUsuario(
            @PathVariable UUID usuarioId) {
        return servicio.listarPorUsuario(usuarioId);
    }

    @GetMapping("/usuarios/{usuarioId}/no-leidas")
    public List<NotificacionResponse> listarNoLeidas(
            @PathVariable UUID usuarioId) {
        return servicio.listarNoLeidas(usuarioId);
    }

    @PatchMapping("/{notificacionId}/usuarios/{usuarioId}/leer")
    public NotificacionResponse marcarComoLeida(
            @PathVariable UUID notificacionId,
            @PathVariable UUID usuarioId) {
        return servicio.marcarComoLeida(
                notificacionId, usuarioId);
    }
}
