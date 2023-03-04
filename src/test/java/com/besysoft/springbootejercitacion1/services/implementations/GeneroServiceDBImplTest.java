package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.datos.DatosDummyGenero;
import com.besysoft.springbootejercitacion1.datos.DatosDummyPelicula;
import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.repositories.database.GeneroRepository;
import com.besysoft.springbootejercitacion1.repositories.database.PeliculaRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GeneroServiceDBImplTest {

    @MockBean
    private GeneroRepository repository;
    @MockBean
    private PeliculaRepository peliculaRepository;
    @Autowired
    private GeneroService service;
    @Autowired
    private PeliculaService peliculaService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("[Genero Service] - Creacion de Genero")
    void createGenero() {
        //GIVEN
        Genero genero = DatosDummyGenero.getGeneroUno();

        //WHEN
        service.createGenero(genero);

        //THEN
        ArgumentCaptor<Genero> generoArgumentCaptor = ArgumentCaptor.forClass(Genero.class);

        verify(repository).save(generoArgumentCaptor.capture());
        Genero generoCaptor = generoArgumentCaptor.getValue();

        assertThat(generoCaptor)
                .isEqualTo(genero);
    }

    @Test
    @DisplayName("[Genero Service] - Buscar Genero por ID")
    void buscarPorId() {
        //GIVEN
        Genero genero = DatosDummyGenero.getGeneros().get(2);
        when(repository.findById(3L))
                .thenReturn(Optional.of(genero));

        //WHEN
        Optional<Genero> optionalGenero = service.buscarPorId(3L);

        //THEN
        assertThat(optionalGenero.isPresent())
                .isTrue();

        assertThat(optionalGenero.get()).isEqualTo(genero);
    }

    @Test
    @DisplayName("[Genero Service] - Buscar Genero por Nombre")
    void buscarPorNombre() {
        //GIVEN
        Genero genero = DatosDummyGenero.getGeneros().get(0);
        when(repository.findByNombre(genero.getNombre()))
                .thenReturn(Optional.of(genero));

        //WHEN
        Optional<Genero> optionalGenero = service.buscarPorNombre(genero.getNombre());

        //THEN
        ArgumentCaptor<String> nombreArgumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(repository).findByNombre(nombreArgumentCaptor.capture());
        String nombreCaptor = nombreArgumentCaptor.getValue();

        assertThat(nombreCaptor).isEqualTo(genero.getNombre());

        assertThat(optionalGenero.isPresent()).isTrue();

        assertThat(optionalGenero.get()).isEqualTo(genero);
    }

    @Test
    @DisplayName("[Genero Service] - Buscar todos los Generos")
    void obtenerTodos() {
        //GIVEN
        when(repository.findAll())
                .thenReturn(DatosDummyGenero.getGeneros());
        int cantidadEsperada = 3;

        //WHEN
        List<Genero> generos = (List<Genero>) service.obtenerTodos();

        //THEN
        assertThat(generos.size())
                .isEqualTo(cantidadEsperada);

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("[Genero Service] - Actualizar Genero por ID")
    void updateGenero() {
        //GIVEN
        Genero generoIngresado = DatosDummyGenero.getGeneroUno();
        Genero generoEsperado = DatosDummyGenero.getGeneros().get(0);

        when(repository.findById(1L))
                .thenReturn(Optional.of(DatosDummyGenero.getGeneros().get(0)));

        //WHEN
        service.updateGenero(1L, generoIngresado);

        //THEN
        ArgumentCaptor<Genero> generoArgumentCaptor = ArgumentCaptor.forClass(Genero.class);

        verify(repository).save(generoArgumentCaptor.capture());
        Genero generoCaptor = generoArgumentCaptor.getValue();

        assertThat(generoCaptor)
                .isEqualTo(generoEsperado);

        assertThat(generoCaptor.getId())
                .isEqualTo(generoEsperado.getId());

        assertThat(generoCaptor.getId())
                .isNotEqualTo(DatosDummyGenero.getGeneroUno().getId());
    }

    @Test
    @DisplayName("[Genero Service] - Dentro de un Genero, agregar Pelicula")
    void agregarPelicula() {
        //GIVEN
        Genero generoDePrueba = DatosDummyGenero.getGeneros().get(0);
        Pelicula pelicula = DatosDummyPelicula.getPeliculas().get(0);

        when(repository.findById(1L))
                .thenReturn(Optional.of(DatosDummyGenero.getGeneros().get(0)));
        when(peliculaRepository.findById(1L))
                .thenReturn(Optional.of(DatosDummyPelicula.getPeliculas().get(0)));

        //WHEN
        service.agregarPelicula(1L, pelicula.getId());

        //THEN
        ArgumentCaptor<Genero> generoArgumentCaptor = ArgumentCaptor.forClass(Genero.class);

        verify(repository).save(generoArgumentCaptor.capture());
        Genero generoCaptor = generoArgumentCaptor.getValue();

        assertThat(generoCaptor.getPeliculas())
                .isNotEqualTo(generoDePrueba.getPeliculas());

        assertThat(generoCaptor.getPeliculas().size())
                .isGreaterThan(0);

    }
}