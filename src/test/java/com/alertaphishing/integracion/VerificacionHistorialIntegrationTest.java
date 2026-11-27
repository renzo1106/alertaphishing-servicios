
package com.alertaphishing.integracion;

import com.alertaphishing.verificacion.dto.VerificacionRequest;
import com.alertaphishing.verificacion.model.NivelRiesgo;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.service.ServicioVerificacion;
import com.alertaphishing.historialeducacion.service.HistorialEducacionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VerificacionHistorialIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HistorialEducacionService historialService;

    @MockBean
    private ServicioVerificacion servicioVerificacion;

    @Test
    void debeRegistrarVerificacionEnHistorial() throws Exception {

        UUID usuarioId = UUID.randomUUID();
        String enlace = "https://ejemplo.com";

        when(servicioVerificacion.verificar(TipoDato.ENLACE, enlace))
                .thenReturn(new ResultadoVerificacion(NivelRiesgo.SEGURO, 0));

        String solicitud = """
                {
                    "usuarioId": "%s",
                    "tipo": "ENLACE",
                    "valor": "%s"
                }
                """.formatted(usuarioId, enlace);

        mockMvc.perform(post("/api/v1/verificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(solicitud))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nivel").value("SEGURO"))
                .andExpect(jsonPath("$.reportes").value(0))
                .andExpect(jsonPath("$.mensaje").isNotEmpty());

        var historiales = historialService.listarHistorial(usuarioId);

        assertEquals(1, historiales.size());

        var historial = historiales.get(0);

        assertEquals(usuarioId, historial.usuarioId());
        assertEquals("URL", historial.tipoDato());
        assertEquals(enlace, historial.datoConsultado());
        assertEquals("SEGURO", historial.nivelRiesgo());
    }
}
