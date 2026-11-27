package com.alertaphishing.verificacion.dto;

import com.alertaphishing.verificacion.model.TipoDato;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record VerificacionRequest(
        @NotNull UUID usuarioId,
        @NotNull TipoDato tipo,
        @NotBlank String valor) {
}
