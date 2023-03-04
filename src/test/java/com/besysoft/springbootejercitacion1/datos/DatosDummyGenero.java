package com.besysoft.springbootejercitacion1.datos;

import com.besysoft.springbootejercitacion1.dominio.Genero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatosDummyGenero {

    public static Genero getGeneroUno() { return new Genero(null, "Genero Uno"); }

    public static Genero getGeneroDos() { return new Genero(null, "Genero Dos"); }

    public static Genero getGeneroTres() { return new Genero(null, "Genero Tres"); }

    public static List<Genero> getGeneros() {
        return new ArrayList<>(
                Arrays.asList(
                        new Genero(1L, "Genero Uno"),
                        new Genero(2L, "Genero Dos"),
                        new Genero(3L, "Genero Uno")
                )
        );
    }
}
