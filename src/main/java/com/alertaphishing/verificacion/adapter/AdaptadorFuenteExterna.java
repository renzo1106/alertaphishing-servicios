package com.alertaphishing.verificacion.adapter;

import org.springframework.stereotype.Component;

/**
 * Patrón Adapter (estructural): traduce el formato de la fuente externa
 * a la interfaz FuenteAmenazas que usa el resto del sistema.
 */
@Component
public class AdaptadorFuenteExterna implements FuenteAmenazas {

    private final ApiFuenteExterna api;

    public AdaptadorFuenteExterna(ApiFuenteExterna api) {
        this.api = api;
    }

    @Override
    public boolean esAmenaza(String valor) {
        return "MALICIOSO".equals(api.consultarReputacion(valor));
    }
}
