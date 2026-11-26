package com.alertaphishing.proteccionfamiliar.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record VinculoFamiliarResponse(
        UUID id,
        UUID usuarioSolicitanteId,
        UUID usuarioInvitadoId,
        String estado,
        LocalDateTime fechaSolicitud,
        LocalDateTime fechaRespuesta
) {
}
