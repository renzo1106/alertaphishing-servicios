package com.alertaphishing.verificacion.adapter;

/** Interfaz propia del sistema (Target del patrón Adapter). */
public interface FuenteAmenazas {
    boolean esAmenaza(String valor);
}
