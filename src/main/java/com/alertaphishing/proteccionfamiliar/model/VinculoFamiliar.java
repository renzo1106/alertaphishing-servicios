
package com.alertaphishing.proteccionfamiliar.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class VinculoFamiliar {

    private final UUID id;
    private final UUID usuarioSolicitanteId;
    private final UUID usuarioInvitadoId;
    private EstadoVinculo estado;
    private final LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;

    public VinculoFamiliar(
            UUID usuarioSolicitanteId,
            UUID usuarioInvitadoId) {

        if (usuarioSolicitanteId == null || usuarioInvitadoId == null) {
            throw new IllegalArgumentException(
                    "Los identificadores de ambos usuarios son obligatorios");
        }

        if (usuarioSolicitanteId.equals(usuarioInvitadoId)) {
            throw new IllegalArgumentException(
                    "Un usuario no puede crear un vínculo consigo mismo");
        }

        this.id = UUID.randomUUID();
        this.usuarioSolicitanteId = usuarioSolicitanteId;
        this.usuarioInvitadoId = usuarioInvitadoId;
        this.estado = EstadoVinculo.PENDIENTE;
        this.fechaSolicitud = LocalDateTime.now();
        this.fechaRespuesta = null;
    }

    public void aceptar() {
        validarPendiente();
        this.estado = EstadoVinculo.ACEPTADO;
        this.fechaRespuesta = LocalDateTime.now();
    }

    public void rechazar() {
        validarPendiente();
        this.estado = EstadoVinculo.RECHAZADO;
        this.fechaRespuesta = LocalDateTime.now();
    }

    public void eliminar() {
        if (this.estado != EstadoVinculo.ACEPTADO) {
            throw new IllegalStateException(
                    "Solo se puede eliminar un vínculo aceptado");
        }

        this.estado = EstadoVinculo.ELIMINADO;
        this.fechaRespuesta = LocalDateTime.now();
    }

    private void validarPendiente() {
        if (this.estado != EstadoVinculo.PENDIENTE) {
            throw new IllegalStateException(
                    "La solicitud ya fue procesada");
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioSolicitanteId() {
        return usuarioSolicitanteId;
    }

    public UUID getUsuarioInvitadoId() {
        return usuarioInvitadoId;
    }

    public EstadoVinculo getEstado() {
        return estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }
}
