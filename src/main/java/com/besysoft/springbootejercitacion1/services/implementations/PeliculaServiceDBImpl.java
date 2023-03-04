package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.excepciones.EntityExist.PeliculaExistException;
import com.besysoft.springbootejercitacion1.excepciones.EntityNotFound.PeliculaNotFoundException;
import com.besysoft.springbootejercitacion1.repositories.database.PeliculaRepository;
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

    public PeliculaServiceDBImpl(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
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
