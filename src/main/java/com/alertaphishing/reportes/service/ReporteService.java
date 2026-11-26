package com.alertaphishing.reportes.service;

import com.alertaphishing.reportes.dto.RegistrarReporteRequest;
import com.alertaphishing.reportes.dto.ReporteResponse;
import com.alertaphishing.reportes.model.EstadoReporte;
import com.alertaphishing.reportes.model.Reporte;
import com.alertaphishing.reportes.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
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
                        new IllegalArgumentException("No se encontró el reporte con ID: " + id)
                );

        return convertirRespuesta(reporte);
    }

    public ReporteResponse calificar(UUID id, boolean veraz) {

        Reporte reporte = reporteRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("No se encontró el reporte con ID: " + id)
                );

        if (veraz) {
            reporte.cambiarEstado(EstadoReporte.VALIDADO);
        } else {
            reporte.cambiarEstado(EstadoReporte.RECHAZADO);
        }

        reporteRepository.guardar(reporte);

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
