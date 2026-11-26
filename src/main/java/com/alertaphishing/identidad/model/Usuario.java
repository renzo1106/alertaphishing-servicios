package com.alertaphishing.identidad.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(name = "uk_usuario_correo", columnNames = "correo"))
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 180)
    private String correo;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    protected Usuario() {}

    public Usuario(String nombres, String apellidos, String correo, String passwordHash, String telefono) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.telefono = telefono;
        this.fechaRegistro = LocalDateTime.now();
    }

    public void actualizarPerfil(String nombres, String apellidos, String telefono) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public String getPasswordHash() { return passwordHash; }
}
