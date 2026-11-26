package com.alertaphishing.identidad.dto;

import com.alertaphishing.identidad.model.Usuario;
import java.time.LocalDateTime;

public record PerfilResponse(Long id, String nombres, String apellidos, String correo,
                             String telefono, LocalDateTime fechaRegistro) {
    public static PerfilResponse desde(Usuario usuario) {
        return new PerfilResponse(usuario.getId(), usuario.getNombres(), usuario.getApellidos(),
                usuario.getCorreo(), usuario.getTelefono(), usuario.getFechaRegistro());
    }
}
