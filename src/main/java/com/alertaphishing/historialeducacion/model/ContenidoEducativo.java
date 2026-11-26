package com.alertaphishing.historialeducacion.model;

import java.util.UUID;

public class ContenidoEducativo {

    private final UUID id;
    private final String titulo;
    private final String categoria;
    private final String descripcion;
    private final String contenido;

    public ContenidoEducativo(
            String titulo,
            String categoria,
            String descripcion,
            String contenido) {

        if (titulo == null || titulo.isBlank()
                || categoria == null || categoria.isBlank()
                || descripcion == null || descripcion.isBlank()
                || contenido == null || contenido.isBlank()) {
            throw new IllegalArgumentException(
                    "Todos los campos educativos son obligatorios");
        }

        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getContenido() {
        return contenido;
    }
}
