package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.excepciones.EntityExist.PersonajeExistException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.PersonajeNotFoundException;
import com.besysoft.springbootejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PersonajeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class PersonajeServiceDBImpl implements PersonajeService {

    private final PersonajeRepository personajeRepository;

    public PersonajeServiceDBImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Personaje createPersonaje(Personaje personaje) {

        Optional<Personaje> optionalPersonaje = this.personajeRepository.findByNombre(personaje.getNombre());

        if(optionalPersonaje.isPresent()) {
            throw new PersonajeExistException(String.format("El personaje %s ya existe", personaje.getNombre()),
                    new RuntimeException("Causa Original")
            );
        }

        return this.personajeRepository.save(personaje);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personaje> buscarPorId(Long id) {
        return this.personajeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personaje> buscarPorNombre(String nombre) {
        return this.personajeRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Personaje> obtenerTodos() {
        return this.personajeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Personaje updatePersonaje(Long id, Personaje personaje) {

        Optional<Personaje> optionalPersonaje = this.personajeRepository.findById(id);

        if(!optionalPersonaje.isPresent()) {
            throw new PersonajeNotFoundException(String.format("No existe un personaje con id %d", id),
                    new RuntimeException("Causa Original")
            );
        }

        personaje.setId(id);
        return this.personajeRepository.save(personaje);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Personaje> obtenerPersonajesDesdeEdadHastaEdad(Integer desde, Integer hasta) {
        return this.personajeRepository.buscarDesdeEdadHastaEdad(desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Personaje> obtenerPersonajesPorFiltro(String filtro) {
        if(filtro.matches("[0-9]+")) {
            return this.personajeRepository.findByEdad(Integer.valueOf(filtro));
        } else {
            return this.personajeRepository.findAllByNombre(filtro);
        }
    }
}
