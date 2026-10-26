package com.alertaphishing.verificacion.adapter;

import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Adaptee: simula la API de una fuente externa de amenazas, con su propio formato
 * (devuelve el texto "MALICIOSO" o "LIMPIO"). En un caso real aquí iría la llamada HTTP.
 */
@Component
public class ApiFuenteExterna {

    private static final Set<String> LISTA_NEGRA = Set.of(
            "ingreso-banca-segura.com",
            "premio-yape.xyz");

    public String consultarReputacion(String dominio) {
        return LISTA_NEGRA.contains(dominio) ? "MALICIOSO" : "LIMPIO";
    }
}
