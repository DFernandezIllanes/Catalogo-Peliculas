package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.excepciones.EntityAdded.PeliculaAddedException;
import com.besysoft.springbootejercitacion1.excepciones.EntityExist.GeneroExistException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.GeneroNotFoundException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.PeliculaNotFoundException;
import com.besysoft.springbootejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.springbootejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class GeneroServiceDBImpl implements GeneroService {

    private final GeneroRepository generoRepository;
    private final PeliculaRepository peliculaRepository;

    public GeneroServiceDBImpl(GeneroRepository generoRepository, PeliculaRepository peliculaRepository) {
        this.generoRepository = generoRepository;
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public Genero createGenero(Genero genero) {
        Optional<Genero> optionalGenero = this.generoRepository.findByNombre(genero.getNombre());
        if(optionalGenero.isPresent()) {
            throw new GeneroExistException(String.format("El genero %s ya existe", genero.getNombre()),
                    new RuntimeException("Causa Original")
            );
        }
        return this.generoRepository.save(genero);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genero> buscarPorId(Long id) {
        return this.generoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genero> buscarPorNombre(String nombre) {
        return this.generoRepository.findByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Genero> obtenerTodos() {
        return this.generoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Genero updateGenero(Long id, Genero genero) {

        Optional<Genero> optionalGenero = this.generoRepository.findById(id);

        if(!optionalGenero.isPresent()) {
            throw new GeneroNotFoundException(String.format("No existe un genero con id %d", id),
                    new RuntimeException("Causa Original")
            );
        }

        genero.setId(id);
        return this.generoRepository.save(genero);
    }

    @Override
    @Transactional(readOnly = false)
    public Genero agregarPelicula(Long idGenero, Long idPelicula) {

        Optional<Genero> optionalGenero = generoRepository.findById(idGenero);
        if(!optionalGenero.isPresent()) {
            throw new GeneroNotFoundException(String.format("No existe un genero con id %d", idGenero),
                    new RuntimeException("Causa Original")
            );
        }

        Optional<Pelicula> optionalPelicula = peliculaRepository.findById(idPelicula);
        if(!optionalPelicula.isPresent()) {
            throw new PeliculaNotFoundException(String.format("No existe una pelicula con el id %d", idPelicula),
                    new RuntimeException("Causa Original")
            );
        }

        Genero genero = optionalGenero.get();
        Pelicula pelicula = optionalPelicula.get();

        Optional<Pelicula> optionalPeliculaListada = genero.getPeliculas()
                .stream()
                .filter(p -> p.getTitulo().equalsIgnoreCase(pelicula.getTitulo()))
                .findAny();

        if(optionalPeliculaListada.isPresent()) {
            throw new PeliculaAddedException(String.format("Ya pertenece al genero la pelicula con titulo %s", pelicula.getTitulo()),
                    new RuntimeException("Causa Original")
            );
        }

        genero.addPelicula(pelicula);
        return generoRepository.save(genero);
    }
}
