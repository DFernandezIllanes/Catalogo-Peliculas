package com.besysoft.springbootejercitacion1.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestPersonaje {
    public static void main(String[] args) {

        //--------------------------- CREACION DE PERSONAJES --------------------------------------------------
        Personaje personaje1 = new Personaje(1L, "Luke Skywalker", 22, 70.5, "Joven habitante de Tatooine");

        Personaje personaje2 = new Personaje(2L, "Han Solo", 29, 80d,
                "Mercenario, ladron, poseedor del Halcon Milenario, la nave mas rapida de la galaxia");

        Personaje personaje3 = new Personaje(3L, "Leia Organa", 22, 60d,
                "Joven princesa y senadora del planeta Alderaan");

        //--------------------------- CREACION DE PELICULAS --------------------------------------------------
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        Pelicula pelicula1 = new Pelicula(1L, "Star Wars: Episode IV - A New Hope", LocalDate.parse("25121977", formatter), 5);

        Pelicula pelicula2 = new Pelicula(2L, "Star Wars: Episode V - The Empire Strikes Back", LocalDate.parse("31071980", formatter), 5);

        Pelicula pelicula3 = new Pelicula(3L, "Star Wars: Episode VI - Return of the Jedi", LocalDate.parse("08121983", formatter), 5);

        personaje1.addPelicula(pelicula1);
        personaje1.addPelicula(pelicula1);
        personaje1.addPelicula(pelicula2);
        personaje1.addPelicula(pelicula3);

        personaje2.addPelicula(pelicula1);
        personaje2.addPelicula(pelicula2);
        personaje2.addPelicula(pelicula3);

        personaje3.addPelicula(pelicula1);
        personaje3.addPelicula(pelicula2);
        personaje3.addPelicula(pelicula3);

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(personaje1);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(personaje2);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(personaje3);
        System.out.println("----------------------------------------------------------------------------------------");

    }
}
