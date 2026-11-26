package com.alertaphishing.analisis.model;

import java.time.LocalDateTime;

public class AnalisisRiesgo {

    private final int scoreRiesgo;
    private final NivelRiesgo nivelRiesgo;
    private final String explicacion;
    private final String recomendacion;
    private final LocalDateTime fechaAnalisis;

    public AnalisisRiesgo(
            int scoreRiesgo,
            NivelRiesgo nivelRiesgo,
            String explicacion,
            String recomendacion) {

        if (scoreRiesgo < 0 || scoreRiesgo > 100) {
            throw new IllegalArgumentException(
                    "El puntaje debe estar entre 0 y 100");
        }

        this.scoreRiesgo = scoreRiesgo;
        this.nivelRiesgo = nivelRiesgo;
        this.explicacion = explicacion;
        this.recomendacion = recomendacion;
        this.fechaAnalisis = LocalDateTime.now();
    }

    public int getScoreRiesgo() {
        return scoreRiesgo;
    }

    public NivelRiesgo getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public LocalDateTime getFechaAnalisis() {
        return fechaAnalisis;
    }
}
