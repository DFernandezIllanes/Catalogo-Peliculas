package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "app", name = "type-bean", havingValue = "database")
public class PeliculaServiceDBImpl implements PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaServiceDBImpl(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public Pelicula createPelicula(Pelicula pelicula) {

        Optional<Pelicula> optionalPelicula = this.peliculaRepository.findByTitulo(pelicula.getTitulo());
        if(optionalPelicula.isPresent()) {
            throw new RuntimeException("Ya existe una pelicula con el mismo titulo");
        }

        return this.peliculaRepository.save(pelicula);
    }

    @Override
    public Optional<Pelicula> buscarPorId(Long id) {
        return this.peliculaRepository.findById(id);
    }

    @Override
    public Optional<Pelicula> buscarPorTitulo(String titulo) {
        return this.peliculaRepository.findByTitulo(titulo);
    }

    @Override
    public Iterable<Pelicula> obtenerTodos() {
        return this.peliculaRepository.findAll();
    }

    @Override
    public Pelicula updatePelicula(Long id, Pelicula pelicula) {
        pelicula.setId(id);
        return this.peliculaRepository.save(pelicula);
    }

    @Override
    public Iterable<Pelicula> obtenerPeliculasDesdeFechaHastaFecha(LocalDate desde, LocalDate hasta) {
        return this.peliculaRepository.buscarDesdeFechaHastaFecha(desde, hasta);
    }

    @Override
    public Iterable<Pelicula> obtenerPeliculasDesdeCalificacionHastaCalificacion(Integer desde, Integer hasta) {
        return this.peliculaRepository.buscarDesdeCalificacionHastaCalificacion(desde, hasta);
    }
}
