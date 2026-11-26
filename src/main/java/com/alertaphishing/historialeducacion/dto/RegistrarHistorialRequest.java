package com.alertaphishing.historialeducacion.dto;

import com.alertaphishing.historialeducacion.model.TipoDatoVerificado;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RegistrarHistorialRequest(

        @NotNull
        UUID usuarioId,

        @NotNull
        TipoDatoVerificado tipoDato,

        @NotBlank
        @Size(max = 5000)
        String datoConsultado,

        @Min(0)
        @Max(100)
        int scoreRiesgo,

        @NotBlank
        String nivelRiesgo,

        @NotBlank
        String resultado

) {
}
