package com.alertaphishing.verificacion;

import com.alertaphishing.verificacion.factory.VerificadorFactory;
import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.strategy.Verificador;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VerificadorFactoryTest {

    @Test
    void devuelveElVerificadorDelTipoPedido() {
        Verificador enlace = mock(Verificador.class);
        when(enlace.tipo()).thenReturn(TipoDato.ENLACE);
        VerificadorFactory factory = new VerificadorFactory(List.of(enlace));

        assertSame(enlace, factory.crear(TipoDato.ENLACE));
    }

    @Test
    void tipoSinVerificadorLanzaExcepcion() {
        VerificadorFactory factory = new VerificadorFactory(List.of());

        assertThrows(IllegalArgumentException.class, () -> factory.crear(TipoDato.CUENTA));
    }
}
