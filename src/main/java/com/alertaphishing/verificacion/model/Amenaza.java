package com.alertaphishing.verificacion.model;

import jakarta.persistence.*;

/** Amenaza conocida (enlace, número o cuenta) con su cantidad de reportes. */
@Entity
@Table(name = "amenaza")
public class Amenaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDato tipo;

    @Column(nullable = false)
    private String valor;

    @Column(nullable = false)
    private long reportes;

    protected Amenaza() {
        // requerido por JPA
    }

    public Amenaza(TipoDato tipo, String valor, long reportes) {
        this.tipo = tipo;
        this.valor = valor;
        this.reportes = reportes;
    }

    public Long getId() { return id; }
    public TipoDato getTipo() { return tipo; }
    public String getValor() { return valor; }
    public long getReportes() { return reportes; }
}
