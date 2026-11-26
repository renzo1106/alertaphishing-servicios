package com.alertaphishing.geolocalizacion.repository;

import com.alertaphishing.geolocalizacion.model.AlertaZona;
import com.alertaphishing.geolocalizacion.model.UbicacionUsuario;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GeolocalizacionRepository {

    private final Map<UUID, UbicacionUsuario> ubicaciones =
            new ConcurrentHashMap<>();

    private final Map<UUID, AlertaZona> alertas =
            new ConcurrentHashMap<>();

    public UbicacionUsuario guardarUbicacion(
            UbicacionUsuario ubicacion) {

        ubicaciones.put(ubicacion.getUsuarioId(), ubicacion);
        return ubicacion;
    }

    public Optional<UbicacionUsuario> buscarUbicacion(UUID usuarioId) {
        return Optional.ofNullable(ubicaciones.get(usuarioId));
    }

    public AlertaZona guardarAlerta(AlertaZona alerta) {
        alertas.put(alerta.getId(), alerta);
        return alerta;
    }

    public List<AlertaZona> listarAlertas() {
        return new ArrayList<>(alertas.values());
    }
}
