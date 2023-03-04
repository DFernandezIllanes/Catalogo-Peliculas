package com.besysoft.springbootejercitacion1.repositories.database;

import com.besysoft.springbootejercitacion1.datos.DatosDummyPersonaje;
import com.besysoft.springbootejercitacion1.dominio.Personaje;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonajeRepositoryTest {

    @Autowired
    private PersonajeRepository personajeRepository;

    @BeforeEach
    void setUp() {
        personajeRepository.save(DatosDummyPersonaje.getPersonajeUno());
        personajeRepository.save(DatosDummyPersonaje.getPersonajeDos());
        personajeRepository.save(DatosDummyPersonaje.getPersonajeTres());
    }

    @AfterEach
    void tearDown() {
        personajeRepository.deleteAll();
    }

    @Test
    void findByNombre() {
        //GIVEN
        String test = "Personaje Uno";

        //WHEN
        Optional<Personaje> optionalPersonaje = personajeRepository.findByNombre(DatosDummyPersonaje.getPersonajeUno().getNombre());

        //THEN
        assertThat(optionalPersonaje.isPresent())
                .isTrue();

        assertThat(optionalPersonaje.get().getNombre())
                .isEqualTo(test);
    }

    @Test
    void findAllByNombre() {
        //GIVEN
        String test = "Personaje Uno";

        //WHEN
        List<Personaje> personajes = (List<Personaje>) personajeRepository.findAllByNombre("Uno");

        //THEN
        assertThat(personajes.size())
                .isGreaterThan(0);

        assertThat(personajes
                .stream()
                .filter(p -> p.getNombre().contains(test))
                .findAny()
                .get()
                .getNombre())
                .isEqualTo(test);
    }

    @Test
    void buscarDesdeEdadHastaEdad() {
        //GIVEN
        Integer desde = 10, hasta = 30;
        int cantidadMinima = 1;


        //WHEN
        List<Personaje> personajes = (List<Personaje>) personajeRepository.buscarDesdeEdadHastaEdad(desde, hasta);

        //THEN
        assertThat(personajes.size())
                .isEqualTo(cantidadMinima);
    }

    @Test
    void findByEdad() {
        //GIVEN
        Integer edadBuscada = DatosDummyPersonaje.getPersonajeUno().getEdad();
        int minimo = 1;

        //WHEN
        List<Personaje> personajes = (List<Personaje>) personajeRepository.findByEdad(edadBuscada);

        //THEN
        assertThat(personajes.size())
                .isEqualTo(minimo);
    }
}