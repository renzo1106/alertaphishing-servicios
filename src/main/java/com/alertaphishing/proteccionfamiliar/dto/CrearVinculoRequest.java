package com.alertaphishing.proteccionfamiliar.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CrearVinculoRequest(

        @NotNull(message = "El usuario solicitante es obligatorio")
        UUID usuarioSolicitanteId,

        @NotNull(message = "El usuario invitado es obligatorio")
        UUID usuarioInvitadoId

) {
}
