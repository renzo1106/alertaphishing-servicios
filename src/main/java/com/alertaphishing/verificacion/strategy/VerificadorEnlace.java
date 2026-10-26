package com.alertaphishing.verificacion.strategy;

import com.alertaphishing.verificacion.adapter.FuenteAmenazas;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.repository.AmenazaRepository;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class VerificadorEnlace extends VerificadorBase {

    private static final String ERROR_ENLACE = "verificacion.error.enlace.invalido";

    public VerificadorEnlace(AmenazaRepository repositorio, FuenteAmenazas fuenteExterna) {
        super(repositorio, fuenteExterna);
    }

    @Override
    public TipoDato tipo() {
        return TipoDato.ENLACE;
    }

    @Override
    public ResultadoVerificacion verificar(String valor) {
        return evaluar(extraerDominio(valor));
    }

    private String extraerDominio(String enlace) {
        String texto = enlace.trim().toLowerCase();
        if (!texto.contains("://")) {
            texto = "http://" + texto;
        }
        String host;
        try {
            host = URI.create(texto).getHost();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ERROR_ENLACE);
        }
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException(ERROR_ENLACE);
        }
        return host.startsWith("www.") ? host.substring(4) : host;
    }
}
