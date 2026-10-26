package com.alertaphishing.verificacion.strategy;

import com.alertaphishing.verificacion.adapter.FuenteAmenazas;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.repository.AmenazaRepository;
import org.springframework.stereotype.Component;

/** Verifica cuentas interbancarias (CCI, 20 dígitos). */
@Component
public class VerificadorCuenta extends VerificadorBase {

    public VerificadorCuenta(AmenazaRepository repositorio, FuenteAmenazas fuenteExterna) {
        super(repositorio, fuenteExterna);
    }

    @Override
    public TipoDato tipo() {
        return TipoDato.CUENTA;
    }

    @Override
    public ResultadoVerificacion verificar(String valor) {
        String digitos = valor.replaceAll("\\D", "");
        if (digitos.length() != 20) {
            throw new IllegalArgumentException("verificacion.error.cuenta.invalida");
        }
        return evaluar(digitos);
    }
}
