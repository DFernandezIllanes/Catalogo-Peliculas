package com.besysoft.springbootejercitacion1.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestGenero {

    public static void main(String[] args) {

        //------------------------ CREACION DE GENEROS ------------------------------------------
        Genero genero1 = new Genero(1L, "Ciencia ficcion");

        Genero genero2 = new Genero(2L, "Terror");

        //------------------------ CREACION DE PELICULAS ----------------------------------------
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        Pelicula pelicula1 = new Pelicula(1L, "Star Wars: Episode IV - A New Hope",
                LocalDate.parse("25121977", formatter), 5);

        Pelicula pelicula2 = new Pelicula(2L, "Star Wars: Episode V - The Empire Strikes Back",
                LocalDate.parse("31071980", formatter), 3);

        Pelicula pelicula3 = new Pelicula(3L, "Star Wars: Episode VI - Return of the Jedi",
                LocalDate.parse("08121983", formatter), 1);

        Pelicula pelicula7 = new Pelicula(7L, "Star Wars: Episode IV - A New Hope",
                LocalDate.parse("25121977", formatter), 0);

        Pelicula pelicula4 = new Pelicula(4L, "El exorcista", LocalDate.parse("15081974", formatter), 6);

        Pelicula pelicula5 = new Pelicula(5L, "El juego del miedo", LocalDate.parse("15012005", formatter), 4);

        Pelicula pelicula6 = new Pelicula(6L, "El conjuro", LocalDate.parse("08082013", formatter), 2);

        genero1.addPelicula(pelicula1);
        genero1.addPelicula(pelicula7);
        genero1.addPelicula(pelicula2);
        genero1.addPelicula(pelicula3);

        genero2.addPelicula(pelicula4);
        genero2.addPelicula(pelicula5);
        genero2.addPelicula(pelicula6);

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(genero1);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(genero2);
        System.out.println("----------------------------------------------------------------------------------------");

    }
}
