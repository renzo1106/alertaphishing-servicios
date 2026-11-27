package com.alertaphishing.historialeducacion.dto;

import com.alertaphishing.historialeducacion.model.TipoDatoVerificado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegistrarHistorialRequest(

        @NotNull UUID usuarioId,

        @NotNull TipoDatoVerificado tipoDato,

        @NotBlank String datoConsultado,

        @NotBlank String nivelRiesgo,

        @NotBlank String resultado
) {
}