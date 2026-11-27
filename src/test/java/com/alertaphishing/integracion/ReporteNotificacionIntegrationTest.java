package com.alertaphishing.integracion;

import com.alertaphishing.notificaciones.dto.NotificacionResponse;
import com.alertaphishing.notificaciones.service.NotificacionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReporteNotificacionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificacionService notificacionService;

    @Test
    void debeNotificarAlUsuarioCuandoRegistraYValidaUnReporte()
            throws Exception {

        UUID usuarioId = UUID.randomUUID();

        String solicitud = """
                {
                    "reportanteId": "%s",
                    "tipoDato": "ENLACE",
                    "datoReportado": "https://banco-falso.com",
                    "descripcion": "Enlace sospechoso recibido"
                }
                """.formatted(usuarioId);

        // Registrar reporte
        String respuesta = mockMvc.perform(post("/api/v1/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(solicitud))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID reporteId = UUID.fromString(
                com.fasterxml.jackson.databind.json.JsonMapper.builder()
                        .build()
                        .readTree(respuesta)
                        .get("id")
                        .asText()
        );

        // Comprobar notificación de registro
        List<NotificacionResponse> notificaciones =
                notificacionService.listarPorUsuario(usuarioId);

        assertEquals(1, notificaciones.size());
        assertEquals("Reporte recibido", notificaciones.get(0).titulo());
        assertEquals("GENERAL", notificaciones.get(0).tipo());

        // Validar reporte
        mockMvc.perform(patch(
                        "/api/v1/reportes/" + reporteId + "/calificacion")
                        .param("veraz", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("VALIDADO"));

        // Comprobar segunda notificación
        notificaciones =
                notificacionService.listarPorUsuario(usuarioId);

        assertEquals(2, notificaciones.size());

        assertTrue(notificaciones.stream()
                .anyMatch(n -> n.titulo().equals("Reporte validado")));
    }
}