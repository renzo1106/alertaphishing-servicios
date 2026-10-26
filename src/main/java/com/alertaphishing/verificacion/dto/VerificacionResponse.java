package com.alertaphishing.verificacion.dto;

import com.alertaphishing.verificacion.model.NivelRiesgo;

public record VerificacionResponse(NivelRiesgo nivel, long reportes, String mensaje) {
}
