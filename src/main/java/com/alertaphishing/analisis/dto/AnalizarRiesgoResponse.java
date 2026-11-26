package com.alertaphishing.analisis.dto;

public record AnalizarRiesgoResponse(
        int scoreRiesgo,
        String nivelRiesgo,
        String explicacion,
        String recomendacion
) {}
