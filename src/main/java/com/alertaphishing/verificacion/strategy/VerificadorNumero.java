package com.alertaphishing.verificacion.strategy;

import com.alertaphishing.verificacion.adapter.FuenteAmenazas;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.repository.AmenazaRepository;
import org.springframework.stereotype.Component;

/** Verifica números de celular peruanos (Yape/Plin o llamadas). */
@Component
public class VerificadorNumero extends VerificadorBase {

    public VerificadorNumero(AmenazaRepository repositorio, FuenteAmenazas fuenteExterna) {
        super(repositorio, fuenteExterna);
    }

    @Override
    public TipoDato tipo() {
        return TipoDato.NUMERO;
    }

    @Override
    public ResultadoVerificacion verificar(String valor) {
        String digitos = valor.replaceAll("\\D", "");
        if (digitos.length() == 11 && digitos.startsWith("51")) {
            digitos = digitos.substring(2);
        }
        if (!digitos.matches("9\\d{8}")) {
            throw new IllegalArgumentException("verificacion.error.numero.invalido");
        }
        return evaluar(digitos);
    }
}
