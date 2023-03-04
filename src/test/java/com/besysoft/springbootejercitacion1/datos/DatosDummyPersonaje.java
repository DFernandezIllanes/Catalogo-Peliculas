package com.besysoft.springbootejercitacion1.datos;

import com.besysoft.springbootejercitacion1.dominio.Personaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatosDummyPersonaje {

    public static Personaje getPersonajeUno() {
        return new Personaje(null, "Personaje Uno", 33, 80.0, "Caballero Jedi");
    }

    public static Personaje getPersonajeDos() {
        return new Personaje(null, "Personaje Dos", 40, 80.0, "Arqueologo");
    }

    public static Personaje getPersonajeTres() {
        return new Personaje(null, "Personaje Tres", 15, 100.0, "Rey de la selva");
    }

    public static List<Personaje> getPersonajes() {
        return new ArrayList<>(
                Arrays.asList(
                        new Personaje(1L, "Personaje Uno", 33, 100.0, "Empleado nuclear"),
                        new Personaje(2L, "Personaje Dos", 22, 75.0, "Velocista"),
                        new Personaje(3L, "Personaje Tres", 40, 82.0, "Ex asesino")
                )
        );
    }
}
