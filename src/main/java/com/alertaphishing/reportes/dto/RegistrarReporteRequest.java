package com.alertaphishing.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RegistrarReporteRequest(

        @NotNull(message = "El identificador del reportante es obligatorio")
        UUID reportanteId,

        @NotBlank(message = "El tipo de dato es obligatorio")
        String tipoDato,

        @NotBlank(message = "El dato reportado es obligatorio")
        @Size(max = 500, message = "El dato reportado no puede superar 500 caracteres")
        String datoReportado,

        @Size(max = 1000, message = "La descripción no puede superar 1000 caracteres")
        String descripcion

) {}
