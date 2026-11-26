package com.alertaphishing.reportes.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reporte {

    private final UUID id;
    private final UUID reportanteId;
    private final String tipoDato;
    private final String datoReportado;
    private final String descripcion;
    private final LocalDateTime fechaRegistro;
    private EstadoReporte estado;

    public Reporte(
            UUID reportanteId,
            String tipoDato,
            String datoReportado,
            String descripcion) {

        if (reportanteId == null) {
            throw new IllegalArgumentException(
                    "El reportante es obligatorio");
        }

        if (tipoDato == null || tipoDato.isBlank()) {
            throw new IllegalArgumentException(
                    "El tipo de dato es obligatorio");
        }

        if (datoReportado == null || datoReportado.isBlank()) {
            throw new IllegalArgumentException(
                    "El dato reportado es obligatorio");
        }

        this.id = UUID.randomUUID();
        this.reportanteId = reportanteId;
        this.tipoDato = tipoDato;
        this.datoReportado = datoReportado;
        this.descripcion = descripcion;
        this.fechaRegistro = LocalDateTime.now();
        this.estado = EstadoReporte.PENDIENTE;
    }

    public UUID getId() {
        return id;
    }

    public UUID getReportanteId() {
        return reportanteId;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getDatoReportado() {
        return datoReportado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public EstadoReporte getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoReporte estado) {
        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado no puede ser nulo");
        }
        this.estado = estado;
    }
}
