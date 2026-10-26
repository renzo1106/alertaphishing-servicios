package com.alertaphishing.verificacion.strategy;

import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;

/** Patrón Strategy (comportamiento): una estrategia de verificación por tipo de dato. */
public interface Verificador {
    TipoDato tipo();
    ResultadoVerificacion verificar(String valor);
}
