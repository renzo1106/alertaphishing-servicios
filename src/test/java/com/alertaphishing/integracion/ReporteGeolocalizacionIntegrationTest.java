
package com.alertaphishing.integracion;

import com.alertaphishing.reportes.dto.RegistrarReporteRequest;
import com.alertaphishing.reportes.service.ReporteService;
import com.alertaphishing.geolocalizacion.dto.AlertaZonaResponse;
import com.alertaphishing.geolocalizacion.service.GeolocalizacionService;
import com.alertaphishing.reportes.model.EstadoReporte;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReporteGeolocalizacionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private GeolocalizacionService geolocalizacionService;

    @Test
    void debeGenerarAlertaConReportesValidadosCercanos() throws Exception {

        UUID usuarioId = UUID.randomUUID();

        // Reporte 1: dentro del radio y validado
        var reporte1 = reporteService.registrar(
                new RegistrarReporteRequest(
                        usuarioId,
                        "ENLACE",
                        "https://fraude-cercano-1.com",
                        "Reporte cercano validado",
                        -12.0464,
                        -77.0428
                )
        );

        reporteService.calificar(reporte1.id(), true);

        // Reporte 2: dentro del radio, pero pendiente
        reporteService.registrar(
                new RegistrarReporteRequest(
                        usuarioId,
                        "ENLACE",
                        "https://fraude-pendiente.com",
                        "Reporte pendiente",
                        -12.0465,
                        -77.0429
                )
        );

        // Reporte 3: validado, pero fuera del radio
        var reporte3 = reporteService.registrar(
                new RegistrarReporteRequest(
                        usuarioId,
                        "ENLACE",
                        "https://fraude-lejano.com",
                        "Reporte lejano validado",
                        -12.20,
                        -77.20
                )
        );

        reporteService.calificar(reporte3.id(), true);

        // Consultar alertas cercanas mediante el endpoint HTTP
        mockMvc.perform(post("/api/v1/geolocalizacion/alertas/cercanas")
                        .param("latitud", "-12.0464")
                        .param("longitud", "-77.0428")
                        .param("radioKm", "2.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadReportes").value(1));
    }
}
