package com.besysoft.springbootejercitacion1.repositories;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonajeRepositoryMemory implements PersonajeRepository{

    private List<Personaje> listaPersonajes;

    public PersonajeRepositoryMemory() {
        this.listaPersonajes = new ArrayList<>(
                Arrays.asList(
                        new Personaje(1L,"Luke Skywalker",22,70.5,"Joven habitante de Tatooine"),
                        new Personaje(2L,"Leia Organa",22,60d,"Joven princesa y senadora del planeta Alderaan"),
                        new Personaje(3L,"Mufasa",8,190d,
                                "Rey de la Selva, líder de una manada de leones, padre de Simba"),
                        new Personaje(4L, "Saber", 24, 42d,
                                "Servant de la clase Saber, invocado para luchar en la quinta guerra " +
                                        "por el Santo Grial. Su nombre real es Artoria Pendragon"),
                        new Personaje(5L, "Broly", 30, 84.5,
                                "Pertenece a la raza saiyajin. Hijo de Paragus. Por lo abrumador de su poder, " +
                                        "se cree que es el legendario Super Saiyajin")
                )
        );
    }

    @Override
    public Personaje createPersonaje(Personaje personaje) {
        personaje.setId((long)(this.listaPersonajes.size()+1));
        this.listaPersonajes.add(personaje);
        return personaje;
    }

    @Override
    public Optional<Personaje> buscarPorId(Long id) {
        return this.listaPersonajes
                .stream()
                .filter(personaje -> personaje.getId() == id)
                .findAny();
    }

    @Override
    public Optional<Personaje> buscarPorNombre(String nombre) {
        return this.listaPersonajes
                .stream()
                .filter(personaje -> personaje.getNombre().equalsIgnoreCase(nombre.trim()))
                .findAny();
    }

    @Override
    public Iterable<Personaje> obtenerTodos() {
        return this.listaPersonajes;
    }

    @Override
    public Personaje updatePersonaje(Long id, Personaje personaje) {
        personaje.setId(id);
        this.listaPersonajes.set((int)(id-1), personaje);
        return personaje;
    }

    @Override
    public Iterable<Personaje> obtenerPersonajesDesdeEdadHastaEdad(Integer desde, Integer hasta) {
        return this.listaPersonajes
                .stream()
                .filter(personaje -> personaje.getEdad() >= desde && personaje.getEdad() <= hasta)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Personaje> obtenerPersonajesPorEdad(Integer edad) {
        return this.listaPersonajes
                .stream()
                .filter(personaje -> personaje.getEdad() == edad)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Personaje> obtenerPersonajesPorNombre(String nombre) {
        return this.listaPersonajes
                .stream()
                .filter(personaje -> personaje.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
}
