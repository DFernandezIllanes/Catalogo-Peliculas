package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class GeneroServiceDBImpl implements GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroServiceDBImpl(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    public Genero createGenero(Genero genero) {
        return this.generoRepository.save(genero);
    }

    @Override
    public Optional<Genero> buscarPorId(Long id) {
        return this.generoRepository.findById(id);
    }

    @Override
    public Optional<Genero> buscarPorNombre(String nombre) {
        return this.generoRepository.findByNombre(nombre);
    }

    @Override
    public Iterable<Genero> obtenerTodos() {
        return this.generoRepository.findAll();
    }

    @Override
    public Genero updateGenero(Long id, Genero newGenero) {
        newGenero.setId(id);
        return this.generoRepository.save(newGenero);
    }

    @Override
    public Genero agregarPelicula(Long id, Pelicula pelicula) {
        return null;
    }
}
