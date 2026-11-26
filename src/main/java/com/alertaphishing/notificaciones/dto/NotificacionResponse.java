package com.alertaphishing.notificaciones.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificacionResponse(
        UUID id,
        UUID usuarioId,
        String tipo,
        String titulo,
        String mensaje,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaLectura
) {
}
