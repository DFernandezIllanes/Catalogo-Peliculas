package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.repositories.GeneroRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeneroServiceImpl implements GeneroService {

    private final GeneroRepository repository;

    public GeneroServiceImpl(GeneroRepository repository) {
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
    public Genero agregarPelicula(Long id, Pelicula pelicula) {
        return this.repository.agregarPelicula(id, pelicula);
    }
}
