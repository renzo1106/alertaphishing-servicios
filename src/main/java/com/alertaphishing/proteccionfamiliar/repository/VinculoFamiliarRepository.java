package com.alertaphishing.proteccionfamiliar.repository;

import com.alertaphishing.proteccionfamiliar.model.VinculoFamiliar;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VinculoFamiliarRepository {

    private final Map<UUID, VinculoFamiliar> vinculos =
            new ConcurrentHashMap<>();

    public VinculoFamiliar guardar(VinculoFamiliar vinculo) {
        vinculos.put(vinculo.getId(), vinculo);
        return vinculo;
    }

    public Optional<VinculoFamiliar> buscarPorId(UUID id) {
        return Optional.ofNullable(vinculos.get(id));
    }

    public List<VinculoFamiliar> listarTodos() {
        return new ArrayList<>(vinculos.values());
    }

    public List<VinculoFamiliar> buscarPorUsuario(UUID usuarioId) {
        return vinculos.values()
                .stream()
                .filter(v ->
                        v.getUsuarioSolicitanteId().equals(usuarioId)
                        || v.getUsuarioInvitadoId().equals(usuarioId))
                .toList();
    }
}
