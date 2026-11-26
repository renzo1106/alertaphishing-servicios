package com.alertaphishing.reportes.repository;

import com.alertaphishing.reportes.model.Reporte;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReporteRepository {

    private final Map<UUID, Reporte> reportes =
            new ConcurrentHashMap<>();

    public Reporte guardar(Reporte reporte) {
        reportes.put(reporte.getId(), reporte);
        return reporte;
    }

    public Optional<Reporte> buscarPorId(UUID id) {
        return Optional.ofNullable(reportes.get(id));
    }

    public List<Reporte> listarTodos() {
        return new ArrayList<>(reportes.values());
    }
}
