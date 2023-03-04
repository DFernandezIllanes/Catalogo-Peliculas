package com.besysoft.springbootejercitacion1.services.implementations;

import com.besysoft.springbootejercitacion1.datos.DatosDummyPersonaje;
import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.repositories.database.PersonajeRepository;
import com.besysoft.springbootejercitacion1.services.interfaces.PersonajeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonajeServiceDBImplTest {

    @MockBean
    private PersonajeRepository repository;
    @Autowired
    private PersonajeService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("[Personaje Service] - Creacion de Personaje")
    void createPersonaje() {
        //GIVEN
        Personaje personaje = DatosDummyPersonaje.getPersonajeUno();

        //WHEN
        service.createPersonaje(personaje);

        //THEN
        ArgumentCaptor<Personaje> personajeArgumentCaptor = ArgumentCaptor.forClass(Personaje.class);

        verify(repository).save(personajeArgumentCaptor.capture());
        Personaje personajeCaptor = personajeArgumentCaptor.getValue();

        assertThat(personajeCaptor)
                .isEqualTo(personaje);
    }

    @Test
    @DisplayName("[Personaje Service] - Buscar Personaje por ID")
    void buscarPorId() {
        //GIVEN
        Long id = 1L;
        int index = 0;
        when(repository.findById(id)).thenReturn(Optional.of(DatosDummyPersonaje.getPersonajes().get(index)));

        //WHEN
        Optional<Personaje> optionalPersonaje = service.buscarPorId(id);


        //THEN
        assertThat(optionalPersonaje.isPresent())
                .isTrue();

        assertThat(optionalPersonaje.get())
                .isEqualTo(DatosDummyPersonaje.getPersonajes().get(index));

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("[Personaje Service] - Buscar Personaje por Nombre")
    void buscarPorNombre() {
        //GIVEN
        int index = 1;
        Personaje personaje = DatosDummyPersonaje.getPersonajes().get(index);
        String nombre = personaje.getNombre();

        when(repository.findByNombre(personaje.getNombre())).thenReturn(Optional.of(personaje));

        //WHEN
        Optional<Personaje> optionalPersonaje = service.buscarPorNombre(personaje.getNombre());

        //THEN
        assertThat(optionalPersonaje.isPresent())
                .isTrue();

        assertThat(optionalPersonaje.get().getNombre()).isEqualTo(nombre);
    }

    @Test
    @DisplayName("[Personaje Service] - Buscar todos los Personajes")
    void obtenerTodos() {
        //GIVEN
        when(repository.findAll())
                .thenReturn(DatosDummyPersonaje.getPersonajes());
        int cantidadEsperada = 3;

        //WHEN
        List<Personaje> personajes = (List<Personaje>) service.obtenerTodos();

        //THEN
        assertThat(personajes.size())
                .isEqualTo(cantidadEsperada);

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("[Personaje Service] - Actualizar Personaje por ID")
    void updatePersonaje() {
        //GIVEN
        Personaje personajeIngresado = DatosDummyPersonaje.getPersonajeUno();
        Personaje personajeEsperado = DatosDummyPersonaje.getPersonajes().get(0);

        when(repository.findById(1L))
                .thenReturn(Optional.of(DatosDummyPersonaje.getPersonajes().get(0)));

        //WHEN
        service.updatePersonaje(1L, personajeIngresado);

        //THEN
        ArgumentCaptor<Personaje> personajeArgumentCaptor = ArgumentCaptor.forClass(Personaje.class);

        verify(repository).save(personajeArgumentCaptor.capture());
        Personaje personajeCaptor = personajeArgumentCaptor.getValue();

        assertThat(personajeCaptor)
                .isEqualTo(personajeEsperado);

        assertThat(personajeCaptor.getId())
                .isEqualTo(personajeEsperado.getId());
    }

    @Test
    @DisplayName("[Personaje Service] - Buscar todos los Personajes dentro de Intervalo de Edad")
    void obtenerPersonajesDesdeEdadHastaEdad() {
        //GIVEN
        Integer desde = 10, hasta = 40;
        when(repository.buscarDesdeEdadHastaEdad(desde, hasta))
                .thenReturn(DatosDummyPersonaje.getPersonajes());

        //WHEN
        List<Personaje> personajes = (List<Personaje>) service.obtenerPersonajesDesdeEdadHastaEdad(10, 40);

        //THEN
        assertThat(personajes.size())
                .isEqualTo(3);

        assertThat(personajes)
                .isEqualTo(DatosDummyPersonaje.getPersonajes());
    }

    @Test
    @DisplayName("[Personaje Service] - Buscar todos los Personajes por Edad o Nombre")
    void obtenerPersonajesPorFiltro() {
        //GIVEN
        Personaje personaje = DatosDummyPersonaje.getPersonajes().get(2);
        when(repository.findByEdad(40)).thenReturn(Arrays.asList(personaje));
        when(repository.findAllByNombre("Personaje")).thenReturn(DatosDummyPersonaje.getPersonajes());

        //WHEN
        List<Personaje> lista1 = (List<Personaje>) service.obtenerPersonajesPorFiltro("Personaje");
        List<Personaje> lista2 = (List<Personaje>) service.obtenerPersonajesPorFiltro("40");

        //THEN
        assertThat(lista1.size())
                .isEqualTo(3);

        assertThat(lista1)
                .isEqualTo(DatosDummyPersonaje.getPersonajes());

        assertThat(lista2.size())
                .isEqualTo(1);

        assertThat(lista2.get(0))
                .isEqualTo(DatosDummyPersonaje.getPersonajes().get(2));
    }
}