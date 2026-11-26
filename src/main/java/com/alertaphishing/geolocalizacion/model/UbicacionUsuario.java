package com.alertaphishing.geolocalizacion.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class UbicacionUsuario {

    private final UUID usuarioId;
    private EstadoConsentimiento consentimiento;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaActualizacion;

    public UbicacionUsuario(UUID usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "El identificador del usuario es obligatorio");
        }

        this.usuarioId = usuarioId;
        this.consentimiento = EstadoConsentimiento.DENEGADO;
    }

    public void otorgarConsentimiento() {
        this.consentimiento = EstadoConsentimiento.OTORGADO;
    }

    public void denegarConsentimiento() {
        this.consentimiento = EstadoConsentimiento.DENEGADO;
        this.latitud = null;
        this.longitud = null;
        this.fechaActualizacion = null;
    }

    public void actualizarUbicacion(
            double latitud, double longitud) {

        if (consentimiento != EstadoConsentimiento.OTORGADO) {
            throw new IllegalStateException(
                    "El usuario debe otorgar su consentimiento");
        }

        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException(
                    "La latitud debe estar entre -90 y 90");
        }

        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException(
                    "La longitud debe estar entre -180 y 180");
        }

        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public EstadoConsentimiento getConsentimiento() {
        return consentimiento;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
}
