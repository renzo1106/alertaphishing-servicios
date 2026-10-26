package com.alertaphishing.verificacion.dto;

import com.alertaphishing.verificacion.model.TipoDato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerificacionRequest(
        @NotNull TipoDato tipo,
        @NotBlank String valor) {
}
