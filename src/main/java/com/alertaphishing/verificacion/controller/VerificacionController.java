package com.alertaphishing.verificacion.controller;

import com.alertaphishing.verificacion.dto.VerificacionRequest;
import com.alertaphishing.verificacion.dto.VerificacionResponse;
import com.alertaphishing.verificacion.model.ResultadoVerificacion;
import com.alertaphishing.verificacion.service.ServicioVerificacion;
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
    private final MessageSource mensajes;

    public VerificacionController(ServicioVerificacion servicio, MessageSource mensajes) {
        this.servicio = servicio;
        this.mensajes = mensajes;
    }

    @PostMapping
    @Operation(summary = "Verifica un enlace, número o cuenta CCI",
            description = "Devuelve el nivel de riesgo (SEGURO, SOSPECHOSO o PELIGROSO) con una explicación "
                    + "en el idioma indicado en el encabezado Accept-Language (es o en).")
    public VerificacionResponse verificar(@Valid @RequestBody VerificacionRequest solicitud) {
        ResultadoVerificacion resultado = servicio.verificar(solicitud.tipo(), solicitud.valor());
        String clave = "verificacion.resultado." + resultado.nivel().name().toLowerCase();
        String texto = mensajes.getMessage(clave, null, LocaleContextHolder.getLocale());
        return new VerificacionResponse(resultado.nivel(), resultado.reportes(), texto);
    }
}
