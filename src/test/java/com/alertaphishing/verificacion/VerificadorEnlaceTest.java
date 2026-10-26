package com.alertaphishing.verificacion;

import com.alertaphishing.verificacion.adapter.FuenteAmenazas;
import com.alertaphishing.verificacion.model.Amenaza;
import com.alertaphishing.verificacion.model.NivelRiesgo;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.repository.AmenazaRepository;
import com.alertaphishing.verificacion.strategy.VerificadorEnlace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificadorEnlaceTest {

    @Mock
    AmenazaRepository repositorio;

    @Mock
    FuenteAmenazas fuente;

    @Test
    void enlaceConMuchosReportesEsPeligroso() {
        when(repositorio.findByTipoAndValor(TipoDato.ENLACE, "banco-verificacion-pe.com"))
                .thenReturn(Optional.of(new Amenaza(TipoDato.ENLACE, "banco-verificacion-pe.com", 5)));
        VerificadorEnlace verificador = new VerificadorEnlace(repositorio, fuente);

        ResultadoVerificacion resultado = verificador.verificar("https://www.banco-verificacion-pe.com/login");

        assertEquals(NivelRiesgo.PELIGROSO, resultado.nivel());
        assertEquals(5, resultado.reportes());
    }

    @Test
    void enlaceSinReportesNiAmenazaExternaEsSeguro() {
        when(repositorio.findByTipoAndValor(TipoDato.ENLACE, "ejemplo.com")).thenReturn(Optional.empty());
        when(fuente.esAmenaza("ejemplo.com")).thenReturn(false);
        VerificadorEnlace verificador = new VerificadorEnlace(repositorio, fuente);

        assertEquals(NivelRiesgo.SEGURO, verificador.verificar("ejemplo.com").nivel());
    }

    @Test
    void enlaceConUnReporteEsSospechoso() {
        when(repositorio.findByTipoAndValor(TipoDato.ENLACE, "promo-yape-gratis.net"))
                .thenReturn(Optional.of(new Amenaza(TipoDato.ENLACE, "promo-yape-gratis.net", 1)));
        when(fuente.esAmenaza("promo-yape-gratis.net")).thenReturn(false);
        VerificadorEnlace verificador = new VerificadorEnlace(repositorio, fuente);

        assertEquals(NivelRiesgo.SOSPECHOSO, verificador.verificar("promo-yape-gratis.net").nivel());
    }

    @Test
    void enlaceInvalidoLanzaExcepcion() {
        VerificadorEnlace verificador = new VerificadorEnlace(repositorio, fuente);

        assertThrows(IllegalArgumentException.class, () -> verificador.verificar("http://"));
    }
}
