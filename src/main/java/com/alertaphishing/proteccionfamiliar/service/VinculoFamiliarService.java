package com.alertaphishing.proteccionfamiliar.service;

import com.alertaphishing.proteccionfamiliar.dto.CrearVinculoRequest;
import com.alertaphishing.proteccionfamiliar.dto.VinculoFamiliarResponse;
import com.alertaphishing.proteccionfamiliar.model.EstadoVinculo;
import com.alertaphishing.proteccionfamiliar.model.VinculoFamiliar;
import com.alertaphishing.proteccionfamiliar.repository.VinculoFamiliarRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VinculoFamiliarService {

    private final VinculoFamiliarRepository repositorio;

    public VinculoFamiliarService(
            VinculoFamiliarRepository repositorio) {
        this.repositorio = repositorio;
    }

    public VinculoFamiliarResponse crearSolicitud(
            CrearVinculoRequest request) {

        boolean existe = repositorio.listarTodos()
                .stream()
                .anyMatch(v ->
                        v.getEstado() != EstadoVinculo.RECHAZADO
                        && v.getEstado() != EstadoVinculo.ELIMINADO
                        && (
                            (v.getUsuarioSolicitanteId().equals(request.usuarioSolicitanteId())
                            && v.getUsuarioInvitadoId().equals(request.usuarioInvitadoId()))
                            ||
                            (v.getUsuarioSolicitanteId().equals(request.usuarioInvitadoId())
                            && v.getUsuarioInvitadoId().equals(request.usuarioSolicitanteId()))
                        )
                );

        if (existe) {
            throw new IllegalArgumentException(
                    "Ya existe una solicitud o vínculo entre estos usuarios");
        }

        VinculoFamiliar vinculo = new VinculoFamiliar(
                request.usuarioSolicitanteId(),
                request.usuarioInvitadoId()
        );

        return convertirRespuesta(repositorio.guardar(vinculo));
    }

    public VinculoFamiliarResponse aceptarSolicitud(UUID id) {

        VinculoFamiliar vinculo = buscarVinculo(id);
        vinculo.aceptar();

        return convertirRespuesta(repositorio.guardar(vinculo));
    }

    public VinculoFamiliarResponse rechazarSolicitud(UUID id) {

        VinculoFamiliar vinculo = buscarVinculo(id);
        vinculo.rechazar();

        return convertirRespuesta(repositorio.guardar(vinculo));
    }

    public VinculoFamiliarResponse eliminarVinculo(UUID id) {

        VinculoFamiliar vinculo = buscarVinculo(id);
        vinculo.eliminar();

        return convertirRespuesta(repositorio.guardar(vinculo));
    }

    public List<VinculoFamiliarResponse> listarPorUsuario(UUID usuarioId) {

        return repositorio.buscarPorUsuario(usuarioId)
                .stream()
                .map(this::convertirRespuesta)
                .toList();
    }

    public List<VinculoFamiliarResponse> listarAceptados(UUID usuarioId) {

        return repositorio.buscarPorUsuario(usuarioId)
                .stream()
                .filter(v -> v.getEstado() == EstadoVinculo.ACEPTADO)
                .map(this::convertirRespuesta)
                .toList();
    }

    private VinculoFamiliar buscarVinculo(UUID id) {

        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el vínculo familiar: " + id));
    }

    private VinculoFamiliarResponse convertirRespuesta(
            VinculoFamiliar vinculo) {

        return new VinculoFamiliarResponse(
                vinculo.getId(),
                vinculo.getUsuarioSolicitanteId(),
                vinculo.getUsuarioInvitadoId(),
                vinculo.getEstado().name(),
                vinculo.getFechaSolicitud(),
                vinculo.getFechaRespuesta()
        );
    }
}
