package com.alertaphishing.historialeducacion.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class HistorialVerificacion {

    private final UUID id;
    private final UUID usuarioId;
    private final TipoDatoVerificado tipoDato;
    private final String datoConsultado;
    private final String nivelRiesgo;
    private final String resultado;
    private final LocalDateTime fechaVerificacion;

    public HistorialVerificacion(
            UUID usuarioId,
            TipoDatoVerificado tipoDato,
            String datoConsultado,
            String nivelRiesgo,
            String resultado) {

        if (usuarioId == null || tipoDato == null) {
            throw new IllegalArgumentException(
                    "El usuario y el tipo de dato son obligatorios");
        }

        if (datoConsultado == null || datoConsultado.isBlank()) {
            throw new IllegalArgumentException(
                    "El dato consultado es obligatorio");
        }

        if (nivelRiesgo == null || nivelRiesgo.isBlank()
                || resultado == null || resultado.isBlank()) {
            throw new IllegalArgumentException(
                    "El nivel de riesgo y el resultado son obligatorios");
        }

        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.tipoDato = tipoDato;
        this.datoConsultado = datoConsultado;
        this.nivelRiesgo = nivelRiesgo;
        this.resultado = resultado;
        this.fechaVerificacion = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public TipoDatoVerificado getTipoDato() {
        return tipoDato;
    }

    public String getDatoConsultado() {
        return datoConsultado;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getResultado() {
        return resultado;
    }

    public LocalDateTime getFechaVerificacion() {
        return fechaVerificacion;
    }
}
