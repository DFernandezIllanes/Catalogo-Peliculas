package com.besysoft.springbootejercitacion1.repositories.database;

import com.besysoft.springbootejercitacion1.datos.DatosDummyPelicula;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PeliculaRepositoryTest {

    @Autowired
    private PeliculaRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(DatosDummyPelicula.getPeliculaUno());
        repository.save(DatosDummyPelicula.getPeliculaDos());
        repository.save(DatosDummyPelicula.getPeliculaTres());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("[Pelicula Repository] - Buscar Pelicula por Titulo")
    void findByTitulo() {
        //GIVEN
        String test = DatosDummyPelicula.getPeliculaUno().getTitulo();

        //WHEN
        Optional<Pelicula> optionalPelicula =  repository.findByTitulo(DatosDummyPelicula.getPeliculaUno().getTitulo());

        //THEN
        assertThat(optionalPelicula.isPresent())
                .isTrue();

        assertThat(optionalPelicula.get().getTitulo())
                .isEqualTo(test);
    }

    @Test
    @DisplayName("[Pelicula Repository] - Buscar todas las Peliculas dentro de Intervalo de Fecha")
    void buscarDesdeFechaHastaFecha() {
        //GIVEN
        LocalDate desde = LocalDate.of(1900,1, 1);
        LocalDate hasta = LocalDate.of(2000,1, 1);
        String test = DatosDummyPelicula.getPeliculaUno().getTitulo();

        //WHEN
        List<Pelicula> peliculas = (List<Pelicula>) repository.buscarDesdeFechaHastaFecha(desde, hasta);

        //THEN
        assertThat(peliculas.size())
                .isGreaterThan(0);

        assertThat(peliculas.get(0).getTitulo()).isEqualTo(test);
    }

    @Test
    @DisplayName("[Pelicula Repository] - Buscar todas las Peliculas dentro de Intervalo de Calificacion")
    void buscarDesdeCalificacionHastaCalificacion() {
        //GIVEN
        Integer desde = 1;
        Integer hasta = 4;
        List<String> titulosEsperados = new ArrayList<>(
                Arrays.asList(
                        DatosDummyPelicula.getPeliculaUno().getTitulo(),
                        DatosDummyPelicula.getPeliculaDos().getTitulo()
                )
        );

        //WHEN
        List<Pelicula> peliculas = (List<Pelicula>) repository.buscarDesdeCalificacionHastaCalificacion(desde, hasta);

        //THEN
        assertThat(peliculas.size()).isEqualTo(2);

        assertThat(peliculas
                .stream()
                .map(Pelicula::getTitulo)
                .collect(Collectors.toList())
        ).isEqualTo(titulosEsperados);
    }
}