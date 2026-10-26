package com.alertaphishing.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ManejadorErrores {

    private final MessageSource mensajes;

    public ManejadorErrores(MessageSource mensajes) {
        this.mensajes = mensajes;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> datoInvalido(IllegalArgumentException e) {
        String texto = mensajes.getMessage(e.getMessage(), null, e.getMessage(), LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", texto));
    }
}
