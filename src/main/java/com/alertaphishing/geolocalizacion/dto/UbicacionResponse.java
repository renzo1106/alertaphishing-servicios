package com.alertaphishing.geolocalizacion.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UbicacionResponse(
        UUID usuarioId,
        String consentimiento,
        Double latitud,
        Double longitud,
        LocalDateTime fechaActualizacion
) {}
