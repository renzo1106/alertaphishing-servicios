package com.alertaphishing.analisis.controller;

import com.alertaphishing.analisis.dto.AnalizarRiesgoRequest;
import com.alertaphishing.analisis.dto.AnalizarRiesgoResponse;
import com.alertaphishing.analisis.service.AnalisisRiesgoService;

import com.alertaphishing.proteccionfamiliar.dto.VinculoFamiliarResponse;
import com.alertaphishing.proteccionfamiliar.service.VinculoFamiliarService;

import com.alertaphishing.notificaciones.dto.CrearNotificacionRequest;
import com.alertaphishing.notificaciones.model.TipoNotificacion;
import com.alertaphishing.notificaciones.service.NotificacionService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analisis-riesgo")
public class AnalisisRiesgoController {

    private final AnalisisRiesgoService servicio;
    private final VinculoFamiliarService vinculoService;
    private final NotificacionService notificacionService;

    public AnalisisRiesgoController(
            AnalisisRiesgoService servicio,
            VinculoFamiliarService vinculoService,
            NotificacionService notificacionService) {

        this.servicio = servicio;
        this.vinculoService = vinculoService;
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public AnalizarRiesgoResponse analizar(
            @Valid @RequestBody AnalizarRiesgoRequest request) {

        AnalizarRiesgoResponse resultado =
                servicio.analizar(request.texto());

        if (resultado.nivelRiesgo().equals("ALTO")
                || resultado.nivelRiesgo().equals("CRITICO")) {

            var vinculos = vinculoService.listarAceptados(
                    request.usuarioId());

            for (VinculoFamiliarResponse vinculo : vinculos) {

                UUID familiarId;

                if (vinculo.usuarioSolicitanteId()
                        .equals(request.usuarioId())) {

                    familiarId = vinculo.usuarioInvitadoId();

                } else {

                    familiarId = vinculo.usuarioSolicitanteId();
                }

                CrearNotificacionRequest notificacion =
                        new CrearNotificacionRequest(
                                familiarId,
                                TipoNotificacion.ALERTA_SEGURIDAD,
                                "Alerta de seguridad familiar",
                                "Un familiar vinculado ha detectado un "
                                + "mensaje con riesgo "
                                + resultado.nivelRiesgo()
                                + ". Recomendación: "
                                + resultado.recomendacion()
                        );

                notificacionService.crearNotificacion(notificacion);
            }
        }

        return resultado;
    }
}
