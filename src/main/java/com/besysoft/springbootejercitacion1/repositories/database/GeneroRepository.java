package com.besysoft.springbootejercitacion1.repositories.database;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    Optional<Genero> findByNombre(String nombre);
}
