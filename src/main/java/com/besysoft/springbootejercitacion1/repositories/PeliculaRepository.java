package com.besysoft.springbootejercitacion1.repositories;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.dominio.Personaje;

import java.time.LocalDate;
import java.util.Optional;

public interface PeliculaRepository {

    Pelicula createPelicula(Pelicula pelicula);
    Optional<Pelicula> buscarPorTitulo(String titulo);
    Optional<Pelicula> buscarPorId(Long id);
    Iterable<Pelicula> obtenerTodos();
    Pelicula agregarPersonaje(Pelicula pelicula, Personaje personaje);
    Pelicula updatePelicula(Long id, Pelicula pelicula);
    Iterable<Pelicula> obtenerPeliculasDesdeFechaHastaFecha(LocalDate desde, LocalDate hasta);
    Iterable<Pelicula> obtenerPeliculasDesdeCalificacionHastaCalificacion(Integer desde, Integer hasta);
}
