package com.alertaphishing.historialeducacion.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record HistorialVerificacionResponse(
        UUID id,
        UUID usuarioId,
        String tipoDato,
        String datoConsultado,
        int scoreRiesgo,
        String nivelRiesgo,
        String resultado,
        LocalDateTime fechaVerificacion
) {
}
