package com.besysoft.springbootejercitacion1.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestPelicula {

    public static void main(String[] args) {

        //-------------------------- PERSONAJES ---------------------------------------------------
        Personaje personaje1 = new Personaje(1L, "Luke Skywalker", 22, 70.5, "Joven habitante de Tatooine");

        Personaje personaje2 = new Personaje(2L, "Han Solo", 29, 80d,
                "Mercenario, ladron, poseedor del Halcon Milenario, la nave mas rapida de la galaxia");

        Personaje personaje3 = new Personaje(3L, "Leia Organa", 22, 60d,
                "Joven princesa y senadora del planeta Alderaan");

        //-------------------------- PELICULAS ---------------------------------------------------
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");


        Pelicula pelicula1 = new Pelicula(1L, "Star Wars: Episode IV - A New Hope",
                LocalDate.parse("25121977", formatter), 5);

        Pelicula pelicula2 = new Pelicula(2L, "Star Wars: Episode V - The Empire Strikes Back",
                LocalDate.parse("31071980", formatter), 5);

        Pelicula pelicula3 = new Pelicula( 3L, "Star Wars: Episode VI - Return of the Jedi",
                LocalDate.parse("08121983", formatter), 5);

        pelicula1.addPersonaje(personaje1);
        pelicula1.addPersonaje(personaje1);
        pelicula1.addPersonaje(personaje2);
        pelicula1.addPersonaje(personaje3);

        pelicula2.addPersonaje(personaje1);
        pelicula2.addPersonaje(personaje2);
        pelicula2.addPersonaje(personaje3);

        pelicula3.addPersonaje(personaje1);
        pelicula3.addPersonaje(personaje2);
        pelicula3.addPersonaje(personaje3);

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(pelicula1);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(pelicula2);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(pelicula3);
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
