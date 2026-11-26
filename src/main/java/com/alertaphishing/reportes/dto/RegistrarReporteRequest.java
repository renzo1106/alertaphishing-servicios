package com.alertaphishing.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.util.UUID;

public record RegistrarReporteRequest(

        @NotNull(message = "El identificador del reportante es obligatorio")
        UUID reportanteId,

        @NotBlank(message = "El tipo de dato es obligatorio")
        String tipoDato,

        @NotBlank(message = "El dato reportado es obligatorio")
        @Size(max = 500)
        String datoReportado,

        @Size(max = 1000)
        String descripcion,

        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        Double latitud,

        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        Double longitud

) {}
