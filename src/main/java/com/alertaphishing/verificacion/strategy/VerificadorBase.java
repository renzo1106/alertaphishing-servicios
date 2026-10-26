package com.alertaphishing.verificacion.strategy;

import com.alertaphishing.verificacion.adapter.FuenteAmenazas;
import com.alertaphishing.verificacion.model.Amenaza;
import com.alertaphishing.verificacion.model.NivelRiesgo;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.repository.AmenazaRepository;

/** Lógica común de clasificación de riesgo que comparten todas las estrategias. */
public abstract class VerificadorBase implements Verificador {

    private static final long REPORTES_PARA_PELIGRO = 3;

    private final AmenazaRepository repositorio;
    private final FuenteAmenazas fuenteExterna;

    protected VerificadorBase(AmenazaRepository repositorio, FuenteAmenazas fuenteExterna) {
        this.repositorio = repositorio;
        this.fuenteExterna = fuenteExterna;
    }

    protected ResultadoVerificacion evaluar(String valorNormalizado) {
        long reportes = repositorio.findByTipoAndValor(tipo(), valorNormalizado)
                .map(Amenaza::getReportes)
                .orElse(0L);

        if (reportes >= REPORTES_PARA_PELIGRO || fuenteExterna.esAmenaza(valorNormalizado)) {
            return new ResultadoVerificacion(NivelRiesgo.PELIGROSO, reportes);
        }
        if (reportes > 0) {
            return new ResultadoVerificacion(NivelRiesgo.SOSPECHOSO, reportes);
        }
        return new ResultadoVerificacion(NivelRiesgo.SEGURO, 0);
    }
}
