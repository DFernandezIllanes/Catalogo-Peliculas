package com.besysoft.springbootejercitacion1.repositories;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class GeneroRepositoryMemory implements GeneroRepository{

    private List<Genero> listaGeneros;

    public GeneroRepositoryMemory() {
        this.listaGeneros = new ArrayList<>(
                Arrays.asList(
                        new Genero(1L, "Ciencia ficcion"),
                        new Genero(2L, "Animacion")
                )
        );
    }

    @Override
    public Genero createGenero(Genero genero) {
        genero.setId((long)(this.listaGeneros.size()+1));
        this.listaGeneros.add(genero);
        return genero;
    }

    @Override
    public Optional<Genero> buscarPorNombre(String nombre) {
        return this.listaGeneros
                .stream()
                .filter(genero -> genero.getNombre().equals(nombre.trim()))
                .findAny();
    }

    @Override
    public Optional<Genero> buscarPorId(Long id) {
        return this.listaGeneros
                .stream()
                .filter(gen -> gen.getId() == id)
                .findAny();
    }

    @Override
    public Iterable<Genero> obtenerTodos() {
        return this.listaGeneros;
    }

    @Override
    public Genero updateGenero(Long id, Genero genero) {
        genero.setId(id);
        this.listaGeneros.set((int)(id-1), genero);
        return genero;
    }

    @Override
    public Genero agregarPelicula(Long id, Pelicula pelicula) {
        Genero genero = this.listaGeneros.get((int)(id-1));
        genero.addPelicula(pelicula);
        return genero;
    }
}
