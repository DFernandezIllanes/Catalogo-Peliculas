package com.besysoft.springbootejercitacion1.services.interfaces;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;

import java.util.Optional;

public interface GeneroService {

    Genero createGenero(Genero genero);
    Optional<Genero> buscarPorId(Long id);
    Optional<Genero> buscarPorNombre(String nombre);
    Iterable<Genero> obtenerTodos();
    Genero updateGenero(Long id, Genero newGenero);
    Genero agregarPelicula(Long id, Pelicula pelicula);
}
