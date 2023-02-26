package com.besysoft.springbootejercitacion1.repositories.database;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    Optional<Pelicula> findByTitulo(String titulo);

    @Query("SELECT p FROM Pelicula p WHERE p.fechaDeCreacion BETWEEN :desde AND :hasta")
    Iterable<Pelicula> buscarDesdeFechaHastaFecha(LocalDate desde, LocalDate hasta);

    @Query("SELECT p FROM Pelicula p WHERE p.calificacion BETWEEN :desde AND :hasta")
    Iterable<Pelicula> buscarDesdeCalificacionHastaCalificacion(Integer desde, Integer hasta);
}
