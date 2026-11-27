package com.alertaphishing.historialeducacion.service;

import com.alertaphishing.historialeducacion.dto.*;
import com.alertaphishing.historialeducacion.model.*;
import com.alertaphishing.historialeducacion.repository.HistorialEducacionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HistorialEducacionService {

    private final HistorialEducacionRepository repositorio;

    public HistorialEducacionService(
            HistorialEducacionRepository repositorio) {
        this.repositorio = repositorio;
    }

    public HistorialVerificacionResponse registrarHistorial(
            RegistrarHistorialRequest request) {

        HistorialVerificacion historial =
                new HistorialVerificacion(
                        request.usuarioId(),
                        request.tipoDato(),
                        request.datoConsultado(),
                        request.nivelRiesgo(),
                        request.resultado()
                );

        return convertirHistorial(
                repositorio.guardarHistorial(historial));
    }

    public List<HistorialVerificacionResponse> listarHistorial(
            UUID usuarioId) {

        return repositorio.listarPorUsuario(usuarioId)
                .stream()
                .map(this::convertirHistorial)
                .toList();
    }

    public HistorialVerificacionResponse buscarHistorial(UUID id) {

        HistorialVerificacion historial =
                repositorio.buscarHistorial(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No se encontró el historial solicitado"));

        return convertirHistorial(historial);
    }

    public List<ContenidoEducativoResponse> listarContenidos() {

        return repositorio.listarContenidos()
                .stream()
                .map(this::convertirContenido)
                .toList();
    }

    public List<ContenidoEducativoResponse> listarPorCategoria(
            String categoria) {

        return repositorio.buscarPorCategoria(categoria)
                .stream()
                .map(this::convertirContenido)
                .toList();
    }

    public ContenidoEducativoResponse buscarContenido(UUID id) {

        ContenidoEducativo contenido =
                repositorio.buscarContenido(id)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No se encontró el contenido educativo"));

        return convertirContenido(contenido);
    }

    private HistorialVerificacionResponse convertirHistorial(
            HistorialVerificacion historial) {

        return new HistorialVerificacionResponse(
                historial.getId(),
                historial.getUsuarioId(),
                historial.getTipoDato().name(),
                historial.getDatoConsultado(),
                historial.getNivelRiesgo(),
                historial.getResultado(),
                historial.getFechaVerificacion()
        );
    }

    private ContenidoEducativoResponse convertirContenido(
            ContenidoEducativo contenido) {

        return new ContenidoEducativoResponse(
                contenido.getId(),
                contenido.getTitulo(),
                contenido.getCategoria(),
                contenido.getDescripcion(),
                contenido.getContenido()
        );
    }
}
