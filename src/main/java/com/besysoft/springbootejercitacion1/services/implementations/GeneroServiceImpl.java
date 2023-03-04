package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.repositories.memory.GeneroRepository;
import com.besysoft.springbootejercitacion1.repositories.memory.PeliculaRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "memory")
public class GeneroServiceImpl implements GeneroService {

    private final GeneroRepository repository;
    private final PeliculaRepository peliculaRepository;

    public GeneroServiceImpl(GeneroRepository repository, PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
        this.repository = repository;
    }

    @Override
    public Genero createGenero(Genero genero) {

        Optional<Genero> oGenero = this.repository.buscarPorNombre(genero.getNombre());

        if(oGenero.isPresent()) {
            return null;
        }

        return this.repository.createGenero(genero);
    }

    @Override
    public Optional<Genero> buscarPorId(Long id) {
        return this.repository.buscarPorId(id);
    }

    @Override
    public Optional<Genero> buscarPorNombre(String nombre) {
        return this.repository.buscarPorNombre(nombre);
    }

    @Override
    public Iterable<Genero> obtenerTodos() {
        return this.repository.obtenerTodos();
    }

    @Override
    public Genero updateGenero(Long id, Genero newGenero) {

        Optional<Genero> oGenero = this.repository.buscarPorId(id);

        if(!oGenero.isPresent()) {
            return null;
        }

        return this.repository.updateGenero(id, newGenero);
    }

    @Override
    public Genero agregarPelicula(Long idGenero, Long idPelicula) {

        Optional<Pelicula> optionalPelicula= this.peliculaRepository.buscarPorId(idPelicula);

        if(!optionalPelicula.isPresent()) {
            return null;
        }

        Pelicula pelicula = optionalPelicula.get();
        return this.repository.agregarPelicula(idGenero, pelicula);
    }
}
