package com.besysoft.springbootejercitacion1.repositories.database;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonajeRepository extends JpaRepository<Personaje, Long> {

    Optional<Personaje> findByNombre(String nombre);

    @Query("SELECT p FROM Personaje p WHERE p.nombre LIKE CONCAT('%', :nombre, '%')")
    Iterable<Personaje> findAllByNombre(String nombre);

    @Query("SELECT p FROM Personaje p WHERE p.edad BETWEEN :desde AND :hasta")
    Iterable<Personaje> buscarDesdeEdadHastaEdad(Integer desde, Integer hasta);

    Iterable<Personaje> findByEdad(Integer edad);
}
