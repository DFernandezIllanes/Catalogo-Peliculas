package com.besysoft.springbootejercitacion1.repositories;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.dominio.Personaje;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PeliculaRepositoryMemory implements PeliculaRepository{

    private List<Pelicula> listaPeliculas;

    public PeliculaRepositoryMemory() {
        this.listaPeliculas = new ArrayList<>(
                Arrays.asList(
                        new Pelicula(1L, "Star Wars: Episode IV - A New Hope",
                                LocalDate.parse("25121977", DateTimeFormatter.ofPattern("ddMMyyyy")), 5),
                        new Pelicula(2L, "Star Wars: Episode V - The Empire Strikes Back",
                                LocalDate.parse("31071980", DateTimeFormatter.ofPattern("ddMMyyyy")), 5),
                        new Pelicula(3L, "Star Wars: Episode VI - Return of the Jedi",
                                LocalDate.parse("08121983", DateTimeFormatter.ofPattern("ddMMyyyy")), 5),
                        new Pelicula(4L, "El Rey Leon",
                                LocalDate.parse("07071994", DateTimeFormatter.ofPattern("ddMMyyyy")), 4),
                        new Pelicula(5L, "Fate Stay Night: Unlimited Blade Works",
                                LocalDate.parse("04102014", DateTimeFormatter.ofPattern("ddMMyyyy")), 3),
                        new Pelicula(6L, "Dragon Ball Z: El poder invencible",
                                LocalDate.parse("06031993", DateTimeFormatter.ofPattern("ddMMyyyy")), 3)
                )
        );
    }

    @Override
    public Pelicula createPelicula(Pelicula pelicula) {
        pelicula.setId((long)(this.listaPeliculas.size()+1));
        this.listaPeliculas.add(pelicula);
        return pelicula;
    }

    @Override
    public Optional<Pelicula> buscarPorTitulo(String titulo) {
        return this.listaPeliculas
                .stream()
                .filter(pelicula -> pelicula.getTitulo().equalsIgnoreCase(titulo.trim()))
                .findAny();
    }

    @Override
    public Optional<Pelicula> buscarPorId(Long id) {
        return this.listaPeliculas
                .stream()
                .filter(pelicula -> pelicula.getId() == id)
                .findAny();
    }

    @Override
    public Iterable<Pelicula> obtenerTodos() {
        return this.listaPeliculas;
    }

    @Override
    public Pelicula agregarPersonaje(Pelicula pelicula, Personaje personaje) {
        pelicula.addPersonaje(personaje);
        return pelicula;
    }

    @Override
    public Pelicula updatePelicula(Long id, Pelicula pelicula) {
        pelicula.setId(id);
        this.listaPeliculas.set((int)(id-1), pelicula);
        return pelicula;
    }

    @Override
    public Iterable<Pelicula> obtenerPeliculasDesdeFechaHastaFecha(LocalDate desde, LocalDate hasta) {
        return this.listaPeliculas
                .stream()
                .filter(pelicula -> pelicula.getFechaDeCreacion().isAfter(desde) && pelicula.getFechaDeCreacion().isBefore(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Pelicula> obtenerPeliculasDesdeCalificacionHastaCalificacion(Integer desde, Integer hasta) {
        return this.listaPeliculas
                .stream()
                .filter(pelicula -> pelicula.getCalificacion() >= desde && pelicula.getCalificacion() <= hasta)
                .collect(Collectors.toList());
    }
}
