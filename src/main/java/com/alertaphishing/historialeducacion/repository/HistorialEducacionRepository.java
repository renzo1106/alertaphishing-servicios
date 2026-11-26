package com.alertaphishing.historialeducacion.repository;

import com.alertaphishing.historialeducacion.model.ContenidoEducativo;
import com.alertaphishing.historialeducacion.model.HistorialVerificacion;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HistorialEducacionRepository {

    private final Map<UUID, HistorialVerificacion> historiales =
            new ConcurrentHashMap<>();

    private final Map<UUID, ContenidoEducativo> contenidos =
            new ConcurrentHashMap<>();

    public HistorialEducacionRepository() {

        agregarContenido(
                new ContenidoEducativo(
                        "Cómo identificar un mensaje de phishing",
                        "PHISHING",
                        "Aprende a reconocer mensajes fraudulentos.",
                        "Desconfía de mensajes que solicitan contraseñas, "
                        + "datos bancarios o códigos de verificación. "
                        + "Comprueba siempre el remitente antes de responder."
                )
        );

        agregarContenido(
                new ContenidoEducativo(
                        "Protege tus cuentas digitales",
                        "SEGURIDAD",
                        "Consejos para mantener seguras tus cuentas.",
                        "Utiliza contraseñas únicas y seguras, "
                        + "activa la autenticación en dos pasos "
                        + "y evita compartir tus credenciales."
                )
        );

        agregarContenido(
                new ContenidoEducativo(
                        "Cómo reconocer enlaces fraudulentos",
                        "ENLACES",
                        "Identifica páginas web sospechosas.",
                        "Revisa cuidadosamente el dominio, "
                        + "evita enlaces desconocidos y no ingreses "
                        + "información personal en páginas sospechosas."
                )
        );

        agregarContenido(
                new ContenidoEducativo(
                        "Evita las estafas por premios falsos",
                        "ESTAFAS",
                        "Reconoce falsas promociones y premios.",
                        "Desconfía de premios que exigen pagos "
                        + "anticipados o información bancaria "
                        + "para recibir una supuesta recompensa."
                )
        );
    }

    public HistorialVerificacion guardarHistorial(
            HistorialVerificacion historial) {

        historiales.put(historial.getId(), historial);
        return historial;
    }

    public List<HistorialVerificacion> listarPorUsuario(UUID usuarioId) {

        return historiales.values()
                .stream()
                .filter(h -> h.getUsuarioId().equals(usuarioId))
                .sorted((a, b) ->
                        b.getFechaVerificacion()
                                .compareTo(a.getFechaVerificacion()))
                .toList();
    }

    public Optional<HistorialVerificacion> buscarHistorial(UUID id) {
        return Optional.ofNullable(historiales.get(id));
    }

    private void agregarContenido(ContenidoEducativo contenido) {
        contenidos.put(contenido.getId(), contenido);
    }

    public List<ContenidoEducativo> listarContenidos() {
        return new ArrayList<>(contenidos.values());
    }

    public Optional<ContenidoEducativo> buscarContenido(UUID id) {
        return Optional.ofNullable(contenidos.get(id));
    }

    public List<ContenidoEducativo> buscarPorCategoria(
            String categoria) {

        return contenidos.values()
                .stream()
                .filter(c -> c.getCategoria()
                        .equalsIgnoreCase(categoria))
                .toList();
    }
}
