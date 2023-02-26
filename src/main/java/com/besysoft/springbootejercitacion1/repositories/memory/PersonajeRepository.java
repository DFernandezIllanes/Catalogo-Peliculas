package com.besysoft.springbootejercitacion1.repositories.memory;

import com.besysoft.springbootejercitacion1.dominio.Personaje;

import java.util.Optional;

public interface PersonajeRepository {

    Personaje createPersonaje(Personaje personaje);
    Optional<Personaje> buscarPorId(Long id);
    Optional<Personaje> buscarPorNombre(String nombre);
    Iterable<Personaje> obtenerTodos();
    Personaje updatePersonaje(Long id, Personaje personaje);
    Iterable<Personaje> obtenerPersonajesDesdeEdadHastaEdad(Integer desde, Integer hasta);
    Iterable<Personaje> obtenerPersonajesPorEdad(Integer edad);
    Iterable<Personaje> obtenerPersonajesPorNombre(String nombre);
}
