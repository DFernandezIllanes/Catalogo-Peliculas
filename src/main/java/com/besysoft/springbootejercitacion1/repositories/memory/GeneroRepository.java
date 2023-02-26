package com.besysoft.springbootejercitacion1.repositories.memory;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;

import java.util.Optional;

public interface GeneroRepository {

    Genero createGenero(Genero genero);
    Optional<Genero> buscarPorNombre(String nombre);
    Optional<Genero> buscarPorId(Long id);
    Iterable<Genero> obtenerTodos();
    Genero updateGenero(Long id, Genero genero);
    Genero agregarPelicula(Long id, Pelicula pelicula);
}
