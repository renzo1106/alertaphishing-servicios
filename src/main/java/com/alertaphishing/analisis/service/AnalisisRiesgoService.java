package com.alertaphishing.analisis.service;

import com.alertaphishing.analisis.dto.AnalizarRiesgoResponse;
import com.alertaphishing.analisis.model.NivelRiesgo;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AnalisisRiesgoService {

    public AnalizarRiesgoResponse analizar(String texto) {

        String contenido = texto.toLowerCase(Locale.ROOT);
        int score = 0;
        StringBuilder motivos = new StringBuilder();

        // Detectar solicitudes de información confidencial
        String[] datosConfidenciales = {
                "contraseña", "clave", "token", "codigo de verificacion",
                "código de verificación", "datos bancarios"
        };

        for (String palabra : datosConfidenciales) {
            if (contenido.contains(palabra)) {
                score += 30;
                motivos.append("Solicita información confidencial. ");
                break;
            }
        }

        // Detectar mensajes de urgencia
        String[] urgencia = {
                "urgente", "inmediatamente", "última oportunidad",
                "ultimo aviso", "último aviso", "bloquearemos",
                "cuenta suspendida"
        };

        for (String palabra : urgencia) {
            if (contenido.contains(palabra)) {
                score += 20;
                motivos.append("Contiene mensajes de urgencia o presión. ");
                break;
            }
        }

        // Detectar premios o beneficios sospechosos
        String[] premios = {
                "ganaste", "has ganado", "premio", "regalo",
                "bono gratis", "reclama tu premio"
        };

        for (String palabra : premios) {
            if (contenido.contains(palabra)) {
                score += 20;
                motivos.append("Promete premios o beneficios sospechosos. ");
                break;
            }
        }

        // Detectar solicitudes de dinero
        String[] dinero = {
                "transferencia", "deposito", "depósito",
                "pago inmediato", "realiza el pago"
        };

        for (String palabra : dinero) {
            if (contenido.contains(palabra)) {
                score += 25;
                motivos.append("Solicita realizar pagos o transferencias. ");
                break;
            }
        }

        // Detectar presencia de enlaces
        if (contenido.contains("http://") || contenido.contains("https://")
                || contenido.contains("www.")) {
            score += 15;
            motivos.append("Contiene un enlace que debe verificarse. ");
        }

        // Limitar el puntaje a 100
        score = Math.min(score, 100);

        NivelRiesgo nivel;
        String recomendacion;

        if (score >= 75) {
            nivel = NivelRiesgo.CRITICO;
            recomendacion = "No interactúes con el mensaje ni compartas información.";
        } else if (score >= 50) {
            nivel = NivelRiesgo.ALTO;
            recomendacion = "No abras enlaces ni realices pagos. Verifica el remitente.";
        } else if (score >= 25) {
            nivel = NivelRiesgo.MEDIO;
            recomendacion = "Ten precaución y verifica la autenticidad del mensaje.";
        } else {
            nivel = NivelRiesgo.BAJO;
            recomendacion = "No se detectaron suficientes señales de riesgo. Mantén la precaución.";
        }

        String explicacion = motivos.length() == 0
                ? "No se identificaron patrones sospechosos en el texto."
                : motivos.toString().trim();

        return new AnalizarRiesgoResponse(
                score,
                nivel.name(),
                explicacion,
                recomendacion
        );
    }
}
