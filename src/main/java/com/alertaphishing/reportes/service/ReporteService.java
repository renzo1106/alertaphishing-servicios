
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

    private final ReporteRepository repositorio;

    public ReporteService(ReporteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public ReporteResponse registrar(
            RegistrarReporteRequest request) {

        Reporte reporte = new Reporte(
                request.reportanteId(),
                request.tipoDato(),
                request.datoReportado(),
                request.descripcion()
        );

        return convertirRespuesta(repositorio.guardar(reporte));
    }

    public List<ReporteResponse> listarTodos() {
        return repositorio.listarTodos()
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    public ReporteResponse buscarPorId(UUID id) {
        Reporte reporte = repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el reporte"));

        return convertirRespuesta(reporte);
    }

    public ReporteResponse calificar(
            UUID id, boolean veraz) {

        Reporte reporte = repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el reporte"));

        reporte.cambiarEstado(
                veraz ? EstadoReporte.VALIDADO : EstadoReporte.RECHAZADO
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
                reporte.getFechaRegistro()
        );
    }
}
