package com.alertaphishing.analisis;

import com.alertaphishing.analisis.dto.AnalizarRiesgoResponse;
import com.alertaphishing.analisis.service.AnalisisRiesgoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnalisisRiesgoServiceTest {

    private final AnalisisRiesgoService servicio =
            new AnalisisRiesgoService();

    @Test
    void debeDetectarMensajeDeAltoRiesgo() {
        String mensaje = "Su cuenta será bloqueada. " +
                "Ingrese su contraseña urgentemente en " +
                "https://banco-seguridad.com";

        AnalizarRiesgoResponse resultado =
                servicio.analizar(mensaje);

        assertTrue(resultado.scoreRiesgo() >= 50);
        assertTrue(
                resultado.nivelRiesgo().equals("ALTO") ||
                resultado.nivelRiesgo().equals("CRITICO")
        );
    }

    @Test
    void debeClasificarMensajeNormalComoRiesgoBajo() {
        String mensaje =
                "Hola, nos vemos mañana para conversar.";

        AnalizarRiesgoResponse resultado =
                servicio.analizar(mensaje);

        assertEquals(0, resultado.scoreRiesgo());
        assertEquals("BAJO", resultado.nivelRiesgo());
    }
}