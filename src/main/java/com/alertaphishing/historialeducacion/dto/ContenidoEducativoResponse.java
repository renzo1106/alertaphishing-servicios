package com.alertaphishing.historialeducacion.dto;

import java.util.UUID;

public record ContenidoEducativoResponse(
        UUID id,
        String titulo,
        String categoria,
        String descripcion,
        String contenido
) {
}
