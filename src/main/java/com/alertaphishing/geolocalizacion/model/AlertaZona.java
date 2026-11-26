package com.alertaphishing.geolocalizacion.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class AlertaZona {

    private final UUID id;
    private final double latitud;
    private final double longitud;
    private final int cantidadReportes;
    private final String nivelRiesgo;
    private final LocalDateTime fechaGeneracion;

    public AlertaZona(
            double latitud,
            double longitud,
            int cantidadReportes) {

        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("Latitud inválida");
        }

        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("Longitud inválida");
        }

        if (cantidadReportes < 1) {
            throw new IllegalArgumentException(
                    "Debe existir al menos un reporte");
        }

        this.id = UUID.randomUUID();
        this.latitud = latitud;
        this.longitud = longitud;
        this.cantidadReportes = cantidadReportes;
        this.nivelRiesgo = determinarNivel(cantidadReportes);
        this.fechaGeneracion = LocalDateTime.now();
    }

    private String determinarNivel(int cantidad) {
        if (cantidad >= 10) {
            return "ALTO";
        }

        if (cantidad >= 5) {
            return "MEDIO";
        }

        return "BAJO";
    }

    public UUID getId() {
        return id;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public int getCantidadReportes() {
        return cantidadReportes;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }
}
