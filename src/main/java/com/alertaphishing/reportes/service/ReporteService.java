package com.alertaphishing.reportes.service;

import com.alertaphishing.reportes.dto.RegistrarReporteRequest;
import com.alertaphishing.reportes.dto.ReporteResponse;
import com.alertaphishing.reportes.model.EstadoReporte;
import com.alertaphishing.reportes.model.Reporte;
import com.alertaphishing.reportes.repository.ReporteRepository;

import com.alertaphishing.notificaciones.dto.CrearNotificacionRequest;
import com.alertaphishing.notificaciones.model.TipoNotificacion;
import com.alertaphishing.notificaciones.service.NotificacionService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final NotificacionService notificacionService;

    public ReporteService(
            ReporteRepository reporteRepository,
            NotificacionService notificacionService) {
        this.reporteRepository = reporteRepository;
        this.notificacionService = notificacionService;
    }

    public ReporteResponse registrar(RegistrarReporteRequest request) {

        Reporte reporte = new Reporte(
                request.reportanteId(),
                request.tipoDato(),
                request.datoReportado(),
                request.descripcion(),
                request.latitud(),
                request.longitud()
        );

        Reporte reporteGuardado = reporteRepository.guardar(reporte);

        notificacionService.crearNotificacion(
                new CrearNotificacionRequest(
                        request.reportanteId(),
                        TipoNotificacion.GENERAL,
                        "Reporte recibido",
                        "Tu reporte comunitario ha sido registrado correctamente."
                )
        );

        return convertirRespuesta(reporteGuardado);
    }

    public List<ReporteResponse> listarTodos() {

        return reporteRepository.listarTodos()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    public ReporteResponse buscarPorId(UUID id) {

        Reporte reporte = reporteRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No se encontró el reporte con ID: " + id)
                );

        return convertirRespuesta(reporte);
    }

    public ReporteResponse calificar(UUID id, boolean veraz) {

        Reporte reporte = reporteRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No se encontró el reporte con ID: " + id)
                );

        if (veraz) {
            reporte.cambiarEstado(EstadoReporte.VALIDADO);
        } else {
            reporte.cambiarEstado(EstadoReporte.RECHAZADO);
        }

        reporteRepository.guardar(reporte);

        String titulo = veraz
                ? "Reporte validado"
                : "Reporte rechazado";

        String mensaje = veraz
                ? "Tu reporte comunitario ha sido validado correctamente."
                : "Tu reporte comunitario ha sido rechazado.";

        notificacionService.crearNotificacion(
                new CrearNotificacionRequest(
                        reporte.getReportanteId(),
                        TipoNotificacion.GENERAL,
                        titulo,
                        mensaje
                )
        );

        return convertirRespuesta(reporte);
    }

    private ReporteResponse convertirRespuesta(Reporte reporte) {

        return new ReporteResponse(
                reporte.getId(),
                reporte.getReportanteId(),
                reporte.getTipoDato(),
                reporte.getDatoReportado(),
                reporte.getDescripcion(),
                reporte.getEstado().name(),
                reporte.getFechaRegistro(),
                reporte.getLatitud(),
                reporte.getLongitud()
        );
    }
}
