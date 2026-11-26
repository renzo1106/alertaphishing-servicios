package com.alertaphishing.reportes.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReporteResponse(
        UUID id,
        UUID reportanteId,
        String tipoDato,
        String datoReportado,
        String descripcion,
        String estado,
        LocalDateTime fechaRegistro,
        Double latitud,
        Double longitud
) {}