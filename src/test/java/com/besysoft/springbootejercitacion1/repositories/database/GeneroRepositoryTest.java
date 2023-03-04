package com.besysoft.springbootejercitacion1.repositories.database;

import com.besysoft.springbootejercitacion1.datos.DatosDummyGenero;
import com.besysoft.springbootejercitacion1.dominio.Genero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GeneroRepositoryTest {

    @Autowired
    private GeneroRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(DatosDummyGenero.getGeneroUno());
        repository.save(DatosDummyGenero.getGeneroDos());
        repository.save(DatosDummyGenero.getGeneroTres());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void findByNombre() {
        //GIVEN
        String test = "Genero Uno";

        //WHEN
        Optional<Genero> optionalGenero = repository.findByNombre(DatosDummyGenero.getGeneroUno().getNombre());

        //THEN
        assertThat(optionalGenero.isPresent())
                .isTrue();

        assertThat(optionalGenero.get().getNombre())
                .isEqualTo(test);
    }
}