
package com.alertaphishing.integracion;

import com.alertaphishing.analisis.dto.AnalizarRiesgoRequest;
import com.alertaphishing.notificaciones.dto.NotificacionResponse;
import com.alertaphishing.proteccionfamiliar.dto.CrearVinculoRequest;
import com.alertaphishing.proteccionfamiliar.service.VinculoFamiliarService;
import com.alertaphishing.notificaciones.service.NotificacionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnalisisRiesgoNotificacionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VinculoFamiliarService vinculoService;

    @Autowired
    private NotificacionService notificacionService;

    @Test
    void debeNotificarAlFamiliarCuandoElRiesgoEsAlto() throws Exception {

        UUID usuarioId = UUID.randomUUID();
        UUID familiarId = UUID.randomUUID();

        // Crear y aceptar el vínculo familiar
        var vinculo = vinculoService.crearSolicitud(
                new CrearVinculoRequest(usuarioId, familiarId)
        );

        vinculoService.aceptarSolicitud(vinculo.id());

        // Mensaje que obtiene 90 puntos y nivel CRITICO
        String solicitud = """
                {
                    "usuarioId": "%s",
                    "texto": "Su cuenta será bloqueada urgentemente. Ingrese su contraseña y realice una transferencia inmediata en https://banco-falso.com"
                }
                """.formatted(usuarioId);

        // Ejecutar el análisis mediante el endpoint real
        mockMvc.perform(post("/api/v1/analisis-riesgo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(solicitud))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nivelRiesgo").value("CRITICO"))
                .andExpect(jsonPath("$.scoreRiesgo").value(90));

        // Consultar las notificaciones del familiar
        List<NotificacionResponse> notificaciones =
                notificacionService.listarPorUsuario(familiarId);

        assertEquals(1, notificaciones.size());

        NotificacionResponse notificacion = notificaciones.get(0);

        assertEquals(familiarId, notificacion.usuarioId());
        assertEquals("ALERTA_SEGURIDAD", notificacion.tipo());
        assertEquals("Alerta de seguridad familiar", notificacion.titulo());
        assertEquals("NO_LEIDA", notificacion.estado());
    }
}
