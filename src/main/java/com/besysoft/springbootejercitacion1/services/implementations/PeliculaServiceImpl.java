package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.repositories.memory.GeneroRepository;
import com.besysoft.springbootejercitacion1.repositories.memory.PeliculaRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "memory")
public class PeliculaServiceImpl implements PeliculaService {

    private final PeliculaRepository repository;
    private final GeneroRepository generoRepository;

    public PeliculaServiceImpl(PeliculaRepository repository, GeneroRepository generoRepository) {
        this.repository = repository;
        this.generoRepository = generoRepository;
    }

    @Override
    public Pelicula createPelicula(Pelicula pelicula) {

        Optional<Pelicula> oPelicula = this.repository.buscarPorTitulo(pelicula.getTitulo());

        if(oPelicula.isPresent()) {
            return null;
        }

        return this.repository.createPelicula(pelicula);
    }

    @Override
    public Optional<Pelicula> buscarPorId(Long id) {
        return this.repository.buscarPorId(id);
    }

    @Override
    public Optional<Pelicula> buscarPorTitulo(String titulo) {
        return this.repository.buscarPorTitulo(titulo);
    }

    @Override
    public Iterable<Pelicula> obtenerTodos() {
        return this.repository.obtenerTodos();
    }

    @Override
    public Pelicula updatePelicula(Long id, Pelicula pelicula) {

        Optional<Pelicula> oPelicula = this.repository.buscarPorId(id);

        if(!oPelicula.isPresent()) {
            return null;
        }

        return this.repository.updatePelicula(id, pelicula);
    }

    @Override
    public Pelicula agregarPersonaje(Long idPelicula, Long idPersonaje) {
        return null;
    }

    @Override
    public Iterable<Pelicula> obtenerPeliculasDesdeFechaHastaFecha(LocalDate desde, LocalDate hasta) {
        return this.repository.obtenerPeliculasDesdeFechaHastaFecha(desde, hasta);
    }

    @Override
    public Iterable<Pelicula> obtenerPeliculasDesdeCalificacionHastaCalificacion(Integer desde, Integer hasta) {
        return this.repository.obtenerPeliculasDesdeCalificacionHastaCalificacion(desde, hasta);
    }
}
