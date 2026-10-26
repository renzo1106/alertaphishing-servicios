package com.alertaphishing.verificacion.factory;

import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.strategy.Verificador;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Patrón Factory (creacional, fábrica simple): entrega el verificador adecuado
 * según el tipo de dato, sin que el servicio conozca las clases concretas.
 */
@Component
public class VerificadorFactory {

    private final Map<TipoDato, Verificador> verificadores = new EnumMap<>(TipoDato.class);

    public VerificadorFactory(List<Verificador> disponibles) {
        disponibles.forEach(v -> verificadores.put(v.tipo(), v));
    }

    public Verificador crear(TipoDato tipo) {
        Verificador verificador = verificadores.get(tipo);
        if (verificador == null) {
            throw new IllegalArgumentException("verificacion.error.tipo.noSoportado");
        }
        return verificador;
    }
}
