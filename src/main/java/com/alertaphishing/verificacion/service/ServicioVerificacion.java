package com.alertaphishing.verificacion.service;

import com.alertaphishing.verificacion.factory.VerificadorFactory;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;
import org.springframework.stereotype.Service;

@Service
public class ServicioVerificacion {

    private final VerificadorFactory factory;

    public ServicioVerificacion(VerificadorFactory factory) {
        this.factory = factory;
    }

    public ResultadoVerificacion verificar(TipoDato tipo, String valor) {
        return factory.crear(tipo).verificar(valor);
    }
}
