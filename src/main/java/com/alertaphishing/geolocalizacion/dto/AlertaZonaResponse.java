package com.alertaphishing.geolocalizacion.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AlertaZonaResponse(
        UUID id,
        double latitud,
        double longitud,
        int cantidadReportes,
        String nivelRiesgo,
        LocalDateTime fechaGeneracion
) {}
