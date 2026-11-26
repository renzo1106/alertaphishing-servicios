package com.alertaphishing.identidad.service;

import com.alertaphishing.identidad.dto.ActualizarPerfilRequest;
import com.alertaphishing.identidad.dto.PerfilResponse;
import com.alertaphishing.identidad.dto.RegistroUsuarioRequest;
import com.alertaphishing.identidad.model.Usuario;
import com.alertaphishing.identidad.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IdentidadService {
    private final UsuarioRepository usuarios;
    private final BCryptPasswordEncoder passwordEncoder;

    public IdentidadService(UsuarioRepository usuarios, BCryptPasswordEncoder passwordEncoder) {
        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
    }

    public PerfilResponse registrar(RegistroUsuarioRequest request) {
        String correo = request.correo().trim().toLowerCase();
        if (usuarios.existsByCorreo(correo)) {
            throw new IllegalArgumentException("identidad.error.correo.existente");
        }
        Usuario usuario = new Usuario(request.nombres().trim(), request.apellidos().trim(),
                correo, passwordEncoder.encode(request.password()), normalizarTelefono(request.telefono()));
        return PerfilResponse.desde(usuarios.save(usuario));
    }

    @Transactional(readOnly = true)
    public PerfilResponse obtenerPerfil(Long id) {
        return PerfilResponse.desde(buscarUsuario(id));
    }

    public PerfilResponse actualizarPerfil(Long id, ActualizarPerfilRequest request) {
        Usuario usuario = buscarUsuario(id);
        usuario.actualizarPerfil(request.nombres().trim(), request.apellidos().trim(),
                normalizarTelefono(request.telefono()));
        return PerfilResponse.desde(usuarios.save(usuario));
    }

    private Usuario buscarUsuario(Long id) {
        return usuarios.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("identidad.error.usuario.noEncontrado"));
    }

    private String normalizarTelefono(String telefono) {
        return telefono == null || telefono.isBlank() ? null : telefono.trim();
    }
}
