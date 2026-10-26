package com.alertaphishing.verificacion;

import com.alertaphishing.verificacion.adapter.AdaptadorFuenteExterna;
import com.alertaphishing.verificacion.adapter.ApiFuenteExterna;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdaptadorFuenteExternaTest {

    @Test
    void traduceMaliciosoAVerdadero() {
        ApiFuenteExterna api = mock(ApiFuenteExterna.class);
        when(api.consultarReputacion("malo.com")).thenReturn("MALICIOSO");

        assertTrue(new AdaptadorFuenteExterna(api).esAmenaza("malo.com"));
    }

    @Test
    void traduceLimpioAFalso() {
        ApiFuenteExterna api = mock(ApiFuenteExterna.class);
        when(api.consultarReputacion("bueno.com")).thenReturn("LIMPIO");

        assertFalse(new AdaptadorFuenteExterna(api).esAmenaza("bueno.com"));
    }
}
