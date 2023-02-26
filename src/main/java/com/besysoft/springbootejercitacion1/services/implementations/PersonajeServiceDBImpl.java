package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PersonajeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class PersonajeServiceDBImpl implements PersonajeService {

    private final PersonajeRepository personajeRepository;

    public PersonajeServiceDBImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public Personaje createPersonaje(Personaje personaje) {
        return this.personajeRepository.save(personaje);
    }

    @Override
    public Optional<Personaje> buscarPorId(Long id) {
        return this.personajeRepository.findById(id);
    }

    @Override
    public Optional<Personaje> buscarPorNombre(String nombre) {
        return this.personajeRepository.findByNombre(nombre);
    }

    @Override
    public Iterable<Personaje> obtenerTodos() {
        return this.personajeRepository.findAll();
    }

    @Override
    public Personaje updatePersonaje(Long id, Personaje personaje) {
        personaje.setId(id);
        return this.personajeRepository.save(personaje);
    }

    @Override
    public Iterable<Personaje> obtenerPersonajesDesdeEdadHastaEdad(Integer desde, Integer hasta) {
        return this.personajeRepository.buscarDesdeEdadHastaEdad(desde, hasta);
    }

    @Override
    public Iterable<Personaje> obtenerPersonajesPorFiltro(String filtro) {
        return null;
    }
}
