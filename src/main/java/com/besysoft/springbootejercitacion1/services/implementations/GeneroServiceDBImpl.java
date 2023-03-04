package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.excepciones.EntityExist.GeneroExistException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.GeneroNotFoundException;
import com.besysoft.springbootejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class GeneroServiceDBImpl implements GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroServiceDBImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
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
    public Genero agregarPelicula(Long id, Pelicula pelicula) {

        Optional<Genero> optionalGenero = generoRepository.findById(id);
        if(!optionalGenero.isPresent()) {
            throw new RuntimeException("No existe el genero");
        }

        Genero genero = optionalGenero.get();

        Optional<Pelicula> optionalPelicula = genero
                .getPeliculas()
                .stream()
                .filter(p -> p.getTitulo() == pelicula.getTitulo())
                .findAny();

        if(optionalPelicula.isPresent()) {
            throw new RuntimeException("La pelicula ya pertenece al genero");
        }

        genero.addPelicula(pelicula);

        return generoRepository.save(genero);
    }
}
