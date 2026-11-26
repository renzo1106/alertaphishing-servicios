package com.alertaphishing.notificaciones.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notificacion {

    private final UUID id;
    private final UUID usuarioId;
    private final TipoNotificacion tipo;
    private final String titulo;
    private final String mensaje;
    private final LocalDateTime fechaCreacion;
    private EstadoNotificacion estado;
    private LocalDateTime fechaLectura;

    public Notificacion(
            UUID usuarioId,
            TipoNotificacion tipo,
            String titulo,
            String mensaje) {

        if (usuarioId == null || tipo == null) {
            throw new IllegalArgumentException(
                    "El usuario y el tipo de notificación son obligatorios");
        }

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException(
                    "El título es obligatorio");
        }

        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException(
                    "El mensaje es obligatorio");
        }

        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoNotificacion.NO_LEIDA;
        this.fechaLectura = null;
    }

    public void marcarComoLeida() {
        if (this.estado == EstadoNotificacion.LEIDA) {
            throw new IllegalStateException(
                    "La notificación ya fue leída");
        }

        this.estado = EstadoNotificacion.LEIDA;
        this.fechaLectura = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public LocalDateTime getFechaLectura() {
        return fechaLectura;
    }
}
