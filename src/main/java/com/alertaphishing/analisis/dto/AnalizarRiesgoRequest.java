package com.alertaphishing.analisis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AnalizarRiesgoRequest(

        @NotNull(message = "El usuario es obligatorio")
        UUID usuarioId,

        @NotBlank(message = "El texto a analizar no puede estar vacío")
        @Size(max = 5000, message = "El texto no puede superar los 5000 caracteres")
        String texto

) {}
