package com.alertaphishing.notificaciones.service;

import com.alertaphishing.notificaciones.dto.CrearNotificacionRequest;
import com.alertaphishing.notificaciones.dto.NotificacionResponse;

import com.alertaphishing.notificaciones.model.EstadoNotificacion;
import com.alertaphishing.notificaciones.model.Notificacion;

import com.alertaphishing.notificaciones.repository.NotificacionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificacionService {

    private final NotificacionRepository repositorio;

    public NotificacionService(
            NotificacionRepository repositorio) {
        this.repositorio = repositorio;
    }

    public NotificacionResponse crearNotificacion(
            CrearNotificacionRequest request) {

        Notificacion notificacion = new Notificacion(
                request.usuarioId(),
                request.tipo(),
                request.titulo(),
                request.mensaje()
        );

        return convertirRespuesta(
                repositorio.guardar(notificacion));
    }

    public List<NotificacionResponse> listarPorUsuario(
            UUID usuarioId) {

        return repositorio.listarPorUsuario(usuarioId)
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    public List<NotificacionResponse> listarNoLeidas(
            UUID usuarioId) {

        return repositorio.listarPorUsuario(usuarioId)
                .stream()
                .filter(n ->
                        n.getEstado() == EstadoNotificacion.NO_LEIDA)
                .map(this::convertirRespuesta)
                .toList();
    }

    public NotificacionResponse marcarComoLeida(
            UUID notificacionId,
            UUID usuarioId) {

        Notificacion notificacion =
                repositorio.buscarPorId(notificacionId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No se encontró la notificación"));

        if (!notificacion.getUsuarioId().equals(usuarioId)) {
            throw new IllegalArgumentException(
                    "La notificación no pertenece a este usuario");
        }

        notificacion.marcarComoLeida();

        return convertirRespuesta(
                repositorio.guardar(notificacion));
    }

    private NotificacionResponse convertirRespuesta(
            Notificacion notificacion) {

        return new NotificacionResponse(
                notificacion.getId(),
                notificacion.getUsuarioId(),
                notificacion.getTipo().name(),
                notificacion.getTitulo(),
                notificacion.getMensaje(),
                notificacion.getEstado().name(),
                notificacion.getFechaCreacion(),
                notificacion.getFechaLectura()
        );
    }
}
