package com.alertaphishing.notificaciones.dto;

import com.alertaphishing.notificaciones.model.TipoNotificacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CrearNotificacionRequest(

        @NotNull(message = "El usuario es obligatorio")
        UUID usuarioId,

        @NotNull(message = "El tipo de notificación es obligatorio")
        TipoNotificacion tipo,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 150)
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        @Size(max = 1000)
        String mensaje

) {
}
