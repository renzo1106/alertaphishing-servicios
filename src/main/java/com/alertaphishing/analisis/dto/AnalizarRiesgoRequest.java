package com.alertaphishing.analisis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AnalizarRiesgoRequest(

        @NotBlank(message = "El texto a analizar no puede estar vacío")
        @Size(max = 5000, message = "El texto no puede superar los 5000 caracteres")
        String texto

) {}
