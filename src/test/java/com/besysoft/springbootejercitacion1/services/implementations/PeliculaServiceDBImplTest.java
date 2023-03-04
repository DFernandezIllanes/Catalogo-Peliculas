package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.datos.DatosDummyPelicula;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PeliculaServiceDBImplTest {

    @MockBean
    private PeliculaRepository repository;
    @Autowired
    private PeliculaService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("[PeliculaService] - Alta de Pelicula")
    void createPelicula() {
        //GIVEN
        Pelicula pelicula = DatosDummyPelicula.getPeliculaUno();

        //WHEN
        service.createPelicula(pelicula);

        //THEN
        ArgumentCaptor<Pelicula> peliculaArgumentCaptor = ArgumentCaptor.forClass(Pelicula.class);

        verify(repository).save(peliculaArgumentCaptor.capture());
        Pelicula peliculaCaptor = peliculaArgumentCaptor.getValue();

        assertThat(peliculaCaptor)
                .isEqualTo(pelicula);
    }

    @Test
    @DisplayName("[PeliculaService] - Buscar por ID")
    void buscarPorId() {
        //GIVEN
        Pelicula pelicula = DatosDummyPelicula.getPeliculas().get(2);
        when(repository.findById(3L))
                .thenReturn(Optional.of(pelicula));

        //WHEN
        Optional<Pelicula> optionalPelicula = service.buscarPorId(3L);

        //THEN
        assertThat(optionalPelicula.isPresent())
                .isTrue();

        assertThat(optionalPelicula.get())
                .isEqualTo(pelicula);
    }

    @Test
    void buscarPorTitulo() {
        //GIVEN
        Pelicula pelicula = DatosDummyPelicula.getPeliculas().get(0);
        when(repository.findByTitulo(pelicula.getTitulo()))
                .thenReturn(Optional.of(pelicula));

        //WHEN
        Optional<Pelicula> optionalPelicula = service.buscarPorTitulo(pelicula.getTitulo());

        //THEN
        ArgumentCaptor<String> tituloArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(repository).findByTitulo(tituloArgumentCaptor.capture());
        String tituloCaptor = tituloArgumentCaptor.getValue();

        assertThat(tituloCaptor)
                .isEqualTo(pelicula.getTitulo());

        assertThat(optionalPelicula.isPresent())
                .isTrue();

        assertThat(optionalPelicula.get())
                .isEqualTo(pelicula);
    }

    @Test
    void obtenerTodos() {
        //GIVEN
        when(repository.findAll())
                .thenReturn(DatosDummyPelicula.getPeliculas());
        int cantidadEsperada = 3;

        //WHEN
        List<Pelicula> peliculas = (List<Pelicula>) service.obtenerTodos();

        //THEN
        assertThat(peliculas.size())
                .isEqualTo(cantidadEsperada);

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void updatePelicula() {
        //GIVEN
        Pelicula peliculaIngresada = DatosDummyPelicula.getPeliculaUno();
        Pelicula peliculaEsperada = DatosDummyPelicula.getPeliculas().get(0);

        //WHEN
        service.updatePelicula(1L, peliculaIngresada);

        //THEN
        ArgumentCaptor<Pelicula> peliculaArgumentCaptor = ArgumentCaptor.forClass(Pelicula.class);

        verify(repository).save(peliculaArgumentCaptor.capture());
        Pelicula peliculaCaptor = peliculaArgumentCaptor.getValue();

        assertThat(peliculaCaptor)
                .isEqualTo(peliculaEsperada);

        assertThat(peliculaCaptor.getId())
                .isEqualTo(peliculaEsperada.getId());

        assertThat(peliculaCaptor.getId())
                .isNotEqualTo(DatosDummyPelicula.getPeliculaUno().getId());
    }

    @Test
    void obtenerPeliculasDesdeFechaHastaFecha() {
        //GIVEN
        LocalDate desde = LocalDate.of(1975,1,1), hasta = LocalDate.of(2010,1,1);
        when(repository.buscarDesdeFechaHastaFecha(desde, hasta))
                .thenReturn(
                        new ArrayList<>(
                                Arrays.asList(
                                        DatosDummyPelicula.getPeliculas().get(0),
                                DatosDummyPelicula.getPeliculas().get(1)
                                )
                        )
                );

        //WHEN
        List<Pelicula> peliculas = (List<Pelicula>) service.obtenerPeliculasDesdeFechaHastaFecha(desde, hasta);

        //THEN
        assertThat(peliculas.size())
                .isEqualTo(2);

        assertThat(peliculas)
                .isEqualTo(Arrays.asList(
                        DatosDummyPelicula.getPeliculas().get(0),
                        DatosDummyPelicula.getPeliculas().get(1))
                );
    }

    @Test
    void obtenerPeliculasDesdeCalificacionHastaCalificacion() {
        //GIVEN
        Integer desde = 2, hasta = 5;
        when(repository.buscarDesdeCalificacionHastaCalificacion(desde, hasta))
                .thenReturn(
                        Arrays.asList(
                                DatosDummyPelicula.getPeliculas().get(1),
                                DatosDummyPelicula.getPeliculas().get(2)
                        )
                );

        //WHEN
        List<Pelicula> peliculas = (List<Pelicula>) service.obtenerPeliculasDesdeCalificacionHastaCalificacion(desde, hasta);

        //THEN
        assertThat(peliculas.size())
                .isEqualTo(2);

        assertThat(peliculas)
                .isEqualTo(
                        Arrays.asList(
                                DatosDummyPelicula.getPeliculas().get(1),
                                DatosDummyPelicula.getPeliculas().get(2)
                        )
                );
    }
}