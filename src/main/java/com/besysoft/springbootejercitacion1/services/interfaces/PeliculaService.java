package com.besysoft.springbootejercitacion1.services.interfaces;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;

import java.time.LocalDate;
import java.util.Optional;

public interface PeliculaService {

    Pelicula createPelicula(Pelicula pelicula);
    Optional<Pelicula> buscarPorId(Long id);
    Optional<Pelicula> buscarPorTitulo(String titulo);
    Iterable<Pelicula> obtenerTodos();
    Pelicula updatePelicula(Long id, Pelicula pelicula);
    Iterable<Pelicula> obtenerPeliculasDesdeFechaHastaFecha(LocalDate desde, LocalDate hasta);
    Iterable<Pelicula> obtenerPeliculasDesdeCalificacionHastaCalificacion(Integer desde, Integer hasta);
}
