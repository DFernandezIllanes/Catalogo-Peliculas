package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.excepciones.EntityAdded.PersonajeAddedException;
import com.besysoft.springbootejercitacion1.excepciones.EntityExist.PeliculaExistException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.PeliculaNotFoundException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.PersonajeNotFoundException;
import com.besysoft.springbootejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.springbootejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class PeliculaServiceDBImpl implements PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final PersonajeRepository personajeRepository;

    public PeliculaServiceDBImpl(PeliculaRepository peliculaRepository,
                                 PersonajeRepository personajeRepository) {
        this.peliculaRepository = peliculaRepository;
        this.personajeRepository = personajeRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Pelicula createPelicula(Pelicula pelicula) {

        Optional<Pelicula> optionalPelicula = this.peliculaRepository.findByTitulo(pelicula.getTitulo());
        if(optionalPelicula.isPresent()) {
            throw new PeliculaExistException(String.format("La pelicula %s ya existe", pelicula.getTitulo()),
                    new RuntimeException("Causa Original")
            );
        }

        return this.peliculaRepository.save(pelicula);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pelicula> buscarPorId(Long id) {
        return this.peliculaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pelicula> buscarPorTitulo(String titulo) {
        return this.peliculaRepository.findByTitulo(titulo);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Pelicula> obtenerTodos() {
        return this.peliculaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Pelicula updatePelicula(Long id, Pelicula pelicula) {

        Optional<Pelicula> optionalPelicula = this.peliculaRepository.findById(id);

        if(!optionalPelicula.isPresent()) {
            throw new PeliculaNotFoundException(String.format("No existe una pelicula con id %d", id),
                    new RuntimeException("Causa Original")
            );
        }

        pelicula.setId(id);
        return this.peliculaRepository.save(pelicula);
    }

    @Override
    @Transactional(readOnly = false)
    public Pelicula agregarPersonaje(Long idPelicula, Long idPersonaje) {

        Optional<Pelicula> optionalPelicula = this.peliculaRepository.findById(idPelicula);
        if(!optionalPelicula.isPresent()) {
            throw new PeliculaNotFoundException(String.format("No existe una pelicula con id %d", idPelicula),
                    new RuntimeException("Causa Original")
            );
        }

        Optional<Personaje> optionalPersonaje = this.personajeRepository.findById(idPersonaje);
        if(!optionalPersonaje.isPresent()) {
            throw new PersonajeNotFoundException(String.format("No existe un personaje con el id %d", idPersonaje),
                    new RuntimeException("Causa Original")
            );
        }

        Pelicula pelicula = optionalPelicula.get();
        Personaje personaje = optionalPersonaje.get();

        Optional<Personaje> optionalPersonajeListado = pelicula.getPersonajes()
                .stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(personaje.getNombre()))
                .findAny();

        if(optionalPersonajeListado.isPresent()) {
            throw new PersonajeAddedException(String.format("El personaje %s ya pertenece al elenco", personaje.getNombre()),
                    new RuntimeException("Causa Original")
            );
        }

        pelicula.addPersonaje(personaje);
        return this.peliculaRepository.save(pelicula);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Pelicula> obtenerPeliculasDesdeFechaHastaFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculaRepository.buscarDesdeFechaHastaFecha(desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Pelicula> obtenerPeliculasDesdeCalificacionHastaCalificacion(Integer desde, Integer hasta) {
        return this.peliculaRepository.buscarDesdeCalificacionHastaCalificacion(desde, hasta);
    }
}
