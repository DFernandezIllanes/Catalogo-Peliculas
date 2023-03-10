package com.besysoft.springbootejercitacion1.services.interfaces;

import com.besysoft.springbootejercitacion1.dominio.Personaje;

import java.util.Optional;

public interface PersonajeService {

    Personaje createPersonaje(Personaje personaje);
    Optional<Personaje> buscarPorId(Long id);
    Optional<Personaje> buscarPorNombre(String nombre);
    Iterable<Personaje> obtenerTodos();
    Personaje updatePersonaje(Long id, Personaje personaje);
    Iterable<Personaje> obtenerPersonajesDesdeEdadHastaEdad(Integer desde, Integer hasta);
    Iterable<Personaje> obtenerPersonajesPorFiltro(String filtro);
}
