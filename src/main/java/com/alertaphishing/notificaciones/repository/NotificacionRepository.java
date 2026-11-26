package com.alertaphishing.notificaciones.repository;

import com.alertaphishing.notificaciones.model.Notificacion;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class NotificacionRepository {

    private final Map<UUID, Notificacion> notificaciones =
            new ConcurrentHashMap<>();

    public Notificacion guardar(Notificacion notificacion) {
        notificaciones.put(notificacion.getId(), notificacion);
        return notificacion;
    }

    public Optional<Notificacion> buscarPorId(UUID id) {
        return Optional.ofNullable(notificaciones.get(id));
    }

    public List<Notificacion> listarPorUsuario(UUID usuarioId) {
        return notificaciones.values()
                .stream()
                .filter(n -> n.getUsuarioId().equals(usuarioId))
                .sorted((a, b) ->
                        b.getFechaCreacion()
                                .compareTo(a.getFechaCreacion()))
                .toList();
    }
}
