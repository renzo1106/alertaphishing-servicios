package com.alertaphishing.geolocalizacion.service;

import com.alertaphishing.geolocalizacion.dto.ActualizarUbicacionRequest;
import com.alertaphishing.geolocalizacion.dto.AlertaZonaResponse;
import com.alertaphishing.geolocalizacion.dto.UbicacionResponse;
import com.alertaphishing.geolocalizacion.model.AlertaZona;
import com.alertaphishing.geolocalizacion.model.EstadoConsentimiento;
import com.alertaphishing.geolocalizacion.model.UbicacionUsuario;
import com.alertaphishing.geolocalizacion.repository.GeolocalizacionRepository;

import com.alertaphishing.reportes.model.EstadoReporte;
import com.alertaphishing.reportes.model.Reporte;
import com.alertaphishing.reportes.repository.ReporteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GeolocalizacionService {

    private final GeolocalizacionRepository repositorio;
    private final ReporteRepository reporteRepository;

    public GeolocalizacionService(
            GeolocalizacionRepository repositorio,
            ReporteRepository reporteRepository) {
        this.repositorio = repositorio;
        this.reporteRepository = reporteRepository;
    }

    public UbicacionResponse otorgarConsentimiento(UUID usuarioId) {

        UbicacionUsuario ubicacion = obtenerOCrear(usuarioId);
        ubicacion.otorgarConsentimiento();

        return convertirUbicacion(
                repositorio.guardarUbicacion(ubicacion));
    }

    public UbicacionResponse denegarConsentimiento(UUID usuarioId) {

        UbicacionUsuario ubicacion = obtenerOCrear(usuarioId);
        ubicacion.denegarConsentimiento();

        return convertirUbicacion(
                repositorio.guardarUbicacion(ubicacion));
    }

    public UbicacionResponse actualizarUbicacion(
            UUID usuarioId,
            ActualizarUbicacionRequest request) {

        UbicacionUsuario ubicacion = obtenerOCrear(usuarioId);

        ubicacion.actualizarUbicacion(
                request.latitud(),
                request.longitud());

        return convertirUbicacion(
                repositorio.guardarUbicacion(ubicacion));
    }

    public UbicacionResponse consultarUbicacion(UUID usuarioId) {

        UbicacionUsuario ubicacion =
                repositorio.buscarUbicacion(usuarioId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No existe información de ubicación"));

        return convertirUbicacion(ubicacion);
    }

    public AlertaZonaResponse generarAlerta(
            double latitud,
            double longitud,
            int cantidadReportes) {

        AlertaZona alerta = new AlertaZona(
                latitud, longitud, cantidadReportes);

        return convertirAlerta(
                repositorio.guardarAlerta(alerta));
    }

    public AlertaZonaResponse generarAlertaCercana(
            double latitud,
            double longitud,
            double radioKm) {

        if (radioKm <= 0) {
            throw new IllegalArgumentException(
                    "El radio de búsqueda debe ser mayor que cero");
        }

        int cantidadReportes = (int) reporteRepository.listarTodos()
                .stream()
                .filter(reporte ->
                        reporte.getEstado() == EstadoReporte.VALIDADO)
                .filter(reporte ->
                        reporte.getLatitud() != null
                        && reporte.getLongitud() != null)
                .filter(reporte ->
                        calcularDistancia(
                                latitud,
                                longitud,
                                reporte.getLatitud(),
                                reporte.getLongitud()
                        ) <= radioKm)
                .count();

        if (cantidadReportes == 0) {
            throw new IllegalArgumentException(
                    "No existen reportes validados dentro del radio indicado");
        }

        AlertaZona alerta = new AlertaZona(
                latitud,
                longitud,
                cantidadReportes
        );

        return convertirAlerta(
                repositorio.guardarAlerta(alerta));
    }

    private double calcularDistancia(
            double latitud1,
            double longitud1,
            double latitud2,
            double longitud2) {

        final double RADIO_TIERRA_KM = 6371.0;

        double diferenciaLatitud =
                Math.toRadians(latitud2 - latitud1);

        double diferenciaLongitud =
                Math.toRadians(longitud2 - longitud1);

        double a = Math.sin(diferenciaLatitud / 2)
                * Math.sin(diferenciaLatitud / 2)
                + Math.cos(Math.toRadians(latitud1))
                * Math.cos(Math.toRadians(latitud2))
                * Math.sin(diferenciaLongitud / 2)
                * Math.sin(diferenciaLongitud / 2);

        double c = 2 * Math.atan2(
                Math.sqrt(a),
                Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

    public List<AlertaZonaResponse> listarAlertas() {
        return repositorio.listarAlertas()
                .stream()
                .map(this::convertirAlerta)
                .toList();
    }

    private UbicacionUsuario obtenerOCrear(UUID usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException(
                    "El identificador del usuario es obligatorio");
        }

        return repositorio.buscarUbicacion(usuarioId)
                .orElseGet(() -> new UbicacionUsuario(usuarioId));
    }

    private UbicacionResponse convertirUbicacion(
            UbicacionUsuario ubicacion) {

        return new UbicacionResponse(
                ubicacion.getUsuarioId(),
                ubicacion.getConsentimiento().name(),
                ubicacion.getLatitud(),
                ubicacion.getLongitud(),
                ubicacion.getFechaActualizacion());
    }

    private AlertaZonaResponse convertirAlerta(AlertaZona alerta) {
        return new AlertaZonaResponse(
                alerta.getId(),
                alerta.getLatitud(),
                alerta.getLongitud(),
                alerta.getCantidadReportes(),
                alerta.getNivelRiesgo(),
                alerta.getFechaGeneracion());
    }
}
