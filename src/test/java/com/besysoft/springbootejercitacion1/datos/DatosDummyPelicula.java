package com.besysoft.springbootejercitacion1.datos;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatosDummyPelicula {

    public static Pelicula getPeliculaUno() {
        return new Pelicula(null, "El rey leon", LocalDate.of(1975, 12, 30), 1);
    }

    public static Pelicula getPeliculaDos() {
        return new Pelicula(null, "Star wars", LocalDate.of(2000, 12, 30), 3);
    }

    public static Pelicula getPeliculaTres() {
        return new Pelicula(null, "Lego", LocalDate.of(2020, 12, 30), 5);
    }

    public static List<Pelicula> getPeliculas() {
        return new ArrayList<>(
                Arrays.asList(
                        new Pelicula(1L, "El rey leon", LocalDate.of(1975, 12, 30), 1),
                        new Pelicula(2L, "Star wars", LocalDate.of(2000, 12, 30), 3),
                        new Pelicula(3L, "Lego", LocalDate.of(2020, 12, 30), 5)
                )
        );
    }
}
