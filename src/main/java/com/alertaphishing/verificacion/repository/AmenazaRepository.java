package com.alertaphishing.verificacion.repository;

import com.alertaphishing.verificacion.model.Amenaza;
import com.alertaphishing.verificacion.model.TipoDato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** Patrón Repository: oculta el acceso a la base de datos detrás de una interfaz. */
public interface AmenazaRepository extends JpaRepository<Amenaza, Long> {
    Optional<Amenaza> findByTipoAndValor(TipoDato tipo, String valor);
}
