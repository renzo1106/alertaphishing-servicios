package com.alertaphishing.verificacion.controller;

import com.alertaphishing.verificacion.dto.VerificacionRequest;
import com.alertaphishing.verificacion.dto.VerificacionResponse;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.model.TipoDato;
import com.alertaphishing.verificacion.service.ServicioVerificacion;

import com.alertaphishing.historialeducacion.dto.RegistrarHistorialRequest;
import com.alertaphishing.historialeducacion.model.TipoDatoVerificado;
import com.alertaphishing.historialeducacion.service.HistorialEducacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verificaciones")
@Tag(name = "Verificación de amenazas")
public class VerificacionController {

    private final ServicioVerificacion servicio;
    private final HistorialEducacionService historialService;
    private final MessageSource mensajes;

    public VerificacionController(
            ServicioVerificacion servicio,
            HistorialEducacionService historialService,
            MessageSource mensajes) {
        this.servicio = servicio;
        this.historialService = historialService;
        this.mensajes = mensajes;
    }

    @PostMapping
    @Operation(
            summary = "Verifica un enlace, número o cuenta CCI",
            description = "Verifica el dato y registra automáticamente "
                    + "el resultado en el historial del usuario."
    )
    public VerificacionResponse verificar(
            @Valid @RequestBody VerificacionRequest solicitud) {

        ResultadoVerificacion resultado =
                servicio.verificar(solicitud.tipo(), solicitud.valor());

        String clave = "verificacion.resultado."
                + resultado.nivel().name().toLowerCase();

        String texto = mensajes.getMessage(
                clave,
                null,
                LocaleContextHolder.getLocale()
        );

        TipoDatoVerificado tipoHistorial = switch (solicitud.tipo()) {
            case ENLACE -> TipoDatoVerificado.URL;
            case NUMERO -> TipoDatoVerificado.TELEFONO;
            case CUENTA -> TipoDatoVerificado.CUENTA;
        };

        RegistrarHistorialRequest registro =
                new RegistrarHistorialRequest(
                        solicitud.usuarioId(),
                        tipoHistorial,
                        solicitud.valor(),
                        resultado.nivel().name(),
                        texto
                );

        historialService.registrarHistorial(registro);

        return new VerificacionResponse(
                resultado.nivel(),
                resultado.reportes(),
                texto
        );
    }
}
