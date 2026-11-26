package com.alertaphishing.analisis.controller;

import com.alertaphishing.analisis.dto.AnalizarRiesgoRequest;
import com.alertaphishing.analisis.dto.AnalizarRiesgoResponse;
import com.alertaphishing.analisis.service.AnalisisRiesgoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analisis-riesgo")
public class AnalisisRiesgoController {

    private final AnalisisRiesgoService servicio;

    public AnalisisRiesgoController(AnalisisRiesgoService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public AnalizarRiesgoResponse analizar(
            @Valid @RequestBody AnalizarRiesgoRequest request) {

        return servicio.analizar(request.texto());
    }
}