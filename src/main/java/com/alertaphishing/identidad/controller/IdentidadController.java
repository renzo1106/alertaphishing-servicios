package com.alertaphishing.identidad.controller;

import com.alertaphishing.identidad.dto.ActualizarPerfilRequest;
import com.alertaphishing.identidad.dto.PerfilResponse;
import com.alertaphishing.identidad.dto.RegistroUsuarioRequest;
import com.alertaphishing.identidad.service.IdentidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identidad")
public class IdentidadController {
    private final IdentidadService servicio;

    public IdentidadController(IdentidadService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public PerfilResponse registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
        return servicio.registrar(request);
    }

    @GetMapping("/usuarios/{id}/perfil")
    public PerfilResponse obtenerPerfil(@PathVariable Long id) {
        return servicio.obtenerPerfil(id);
    }

    @PutMapping("/usuarios/{id}/perfil")
    public PerfilResponse actualizarPerfil(@PathVariable Long id,
                                           @Valid @RequestBody ActualizarPerfilRequest request) {
        return servicio.actualizarPerfil(id, request);
    }
}
