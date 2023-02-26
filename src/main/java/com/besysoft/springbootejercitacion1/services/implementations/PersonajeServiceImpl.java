package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.repositories.memory.PersonajeRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PersonajeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "memory")
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository repository;

    public PersonajeServiceImpl(PersonajeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Personaje createPersonaje(Personaje personaje) {

        Optional<Personaje> oPersonaje = this.repository.buscarPorNombre(personaje.getNombre());

        if(oPersonaje.isPresent()) {
            return null;
        }

        return this.repository.createPersonaje(personaje);
    }

    @Override
    public Optional<Personaje> buscarPorId(Long id) {
        return this.repository.buscarPorId(id);
    }

    @Override
    public Optional<Personaje> buscarPorNombre(String nombre) {
        return this.repository.buscarPorNombre(nombre);
    }

    @Override
    public Iterable<Personaje> obtenerTodos() {
        return this.repository.obtenerTodos();
    }

    @Override
    public Personaje updatePersonaje(Long id, Personaje personaje) {

        Optional<Personaje> oPersonaje = this.repository.buscarPorId(id);

        if(!oPersonaje.isPresent()) {
            return null;
        }

        return this.repository.updatePersonaje(id, personaje);
    }

    @Override
    public Iterable<Personaje> obtenerPersonajesDesdeEdadHastaEdad(Integer desde, Integer hasta) {
        return this.repository.obtenerPersonajesDesdeEdadHastaEdad(desde, hasta);
    }

    @Override
    public Iterable<Personaje> obtenerPersonajesPorFiltro(String filtro) {

        if(filtro.matches("[0-9]+")) {
            return this.repository.obtenerPersonajesPorEdad(Integer.valueOf(filtro));
        }

        return this.repository.obtenerPersonajesPorNombre(filtro);
    }
}
