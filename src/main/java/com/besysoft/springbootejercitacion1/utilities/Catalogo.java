package com.besysoft.springbootejercitacion1.utilities;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.dominio.Personaje;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Catalogo {

    private static List<Genero> generos = new ArrayList<>();
    private static List<Pelicula> peliculas = new ArrayList<>();
    private static List<Personaje> personajes = new ArrayList<>();

    static {
        //---------------------------- Creacion de Generos ---------------------------------------------
        Genero cienciaFiccion = new Genero(1L, "Ciencia ficcion");

        Genero animacion = new Genero(2L, "Animacion");

        //---------------------------- Creacion de Personajes --------------------------------------------------
        Personaje lukeSkywalker = new Personaje(1L, "Luke Skywalker", 22, 70.5, "Joven habitante de Tatooine");

        Personaje leiaOrgana = new Personaje(2L, "Leia Organa", 22, 60d,
                "Joven princesa y senadora del planeta Alderaan");

        Personaje mufasa = new Personaje(3L, "Mufasa", 8, 190d,
                "Rey de la Selva, líder de una manada de leones, padre de Simba");

        Personaje saber = new Personaje(4L, "Saber", 24, 42d,
                "Servant de la clase Saber, invocado para luchar en la quinta guerra por el Santo Grial. Su nombre real es Artoria Pendragon");

        Personaje broly = new Personaje(5L, "Broly", 30, 84.5,
                "Pertenece a la raza saiyajin. Hijo de Paragus. Por lo abrumador de su poder, se cree que es el legendario Super Saiyajin");

        //---------------------------- Creacion de Peliculas --------------------------------------------------------
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        Pelicula starWars4 = new Pelicula(1L, "Star Wars: Episode IV - A New Hope",
                LocalDate.parse("25121977", formatter), 5);

        Pelicula starWars5 = new Pelicula(2L, "Star Wars: Episode V - The Empire Strikes Back",
                LocalDate.parse("31071980", formatter), 5);

        Pelicula starWars6 = new Pelicula(3L, "Star Wars: Episode VI - Return of the Jedi",
                LocalDate.parse("08121983", formatter), 5);

        Pelicula elReyLeon = new Pelicula(4L, "El Rey Leon",
                LocalDate.parse("07071994", formatter), 4);

        Pelicula unlimitedBladeWorks = new Pelicula(5L, "Fate Stay Night: Unlimited Blade Works",
                LocalDate.parse("04102014", formatter), 3);

        Pelicula elPoderInvencible = new Pelicula(6L, "Dragon Ball Z: El poder invencible",
                LocalDate.parse("06031993", formatter), 3);


        lukeSkywalker.addPelicula(starWars4);
        lukeSkywalker.addPelicula(starWars5);
        lukeSkywalker.addPelicula(starWars6);
        starWars4.addPersonaje(lukeSkywalker);
        starWars5.addPersonaje(lukeSkywalker);
        starWars6.addPersonaje(lukeSkywalker);

        leiaOrgana.addPelicula(starWars4);
        leiaOrgana.addPelicula(starWars5);
        leiaOrgana.addPelicula(starWars6);
        starWars4.addPersonaje(leiaOrgana);
        starWars5.addPersonaje(leiaOrgana);
        starWars6.addPersonaje(leiaOrgana);

        mufasa.addPelicula(elReyLeon);
        elReyLeon.addPersonaje(mufasa);

        saber.addPelicula(unlimitedBladeWorks);
        unlimitedBladeWorks.addPersonaje(saber);

        broly.addPelicula(elPoderInvencible);
        elPoderInvencible.addPersonaje(broly);

        animacion.addPelicula(elReyLeon);
        animacion.addPelicula(unlimitedBladeWorks);
        animacion.addPelicula(elPoderInvencible);

        cienciaFiccion.addPelicula(starWars4);
        cienciaFiccion.addPelicula(starWars5);
        cienciaFiccion.addPelicula(starWars6);

        generos.add(cienciaFiccion);
        generos.add(animacion);

        peliculas.add(elReyLeon);
        peliculas.add(unlimitedBladeWorks);
        peliculas.add(elPoderInvencible);
        peliculas.add(starWars4);
        peliculas.add(starWars5);
        peliculas.add(starWars6);

        personajes.add(saber);
        personajes.add(mufasa);
        personajes.add(broly);
        personajes.add(lukeSkywalker);
        personajes.add(leiaOrgana);
    }

    public static List<Genero> getGeneros() {
        return generos;
    }

    public static void setGeneros(List<Genero> generos) {
        generos = generos;
    }

    public static void addGenero(Genero genero) {

        if(generos.contains(genero)) {
            System.out.println("El genero ya esta agregado a la lista de generos del catalogo");
        } else {
            generos.add(genero);
        }
    }

    public static List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public static void setPeliculas(List<Pelicula> peliculas) {
        Catalogo.peliculas = peliculas;
    }

    public static void addPelicula(Pelicula pelicula) {
        peliculas.add(pelicula);
    }

    public static List<Personaje> getPersonajes() {
        return personajes;
    }

    public static void setPersonajes(List<Personaje> personajes) {
        Catalogo.personajes = personajes;
    }

    public static void addPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }


    public static List<Personaje> buscarPersonajesPorFiltro(String filtro) {
        List<Personaje> listaDePersonajesFiltrados = new ArrayList<>();

        for(Personaje personaje : personajes) {
            /*
            Si el valor del filtro es un numero, comparo por edad.
            Si el filtro no coincide con un valor numerico, comparo por nombre
             */
            if(filtro.matches("[0-9]+") && personaje.getEdad().toString().equals(filtro) ||
            !filtro.matches("[0-9]+") && personaje.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                //personaje.getNombre().equalsIgnoreCase(filtro)

                listaDePersonajesFiltrados.add(personaje);
            }
        }

        return listaDePersonajesFiltrados;
    }

    public static List<Pelicula> buscarPeliculasPorFiltro(String filtro) {
        List<Pelicula> listaDePeliculasFiltradas = new ArrayList<>();
        int index = -1;

        // Compruebo si filtro coincide con alguno de los nombres de la lista de generos
        for(int i=0; i < generos.size() && index<0; i++) {
            if(generos.get(i).getNombre().equalsIgnoreCase(filtro)) {
                index = i;
            }
        }

        // el filtro coincide con el nombre de alguno de los generos
        if(index >= 0) {
            for(Pelicula pelicula : generos.get(index).getPeliculas()) {
                listaDePeliculasFiltradas.add(pelicula);
            }
        } else {
            // Compruebo si filtro coincide con alguno de los titulos de la lista de peliculas
            for(int i = 0; i < peliculas.size() && index < 0; i++) {
                /* Condicion anterior del if de abajo
                this.peliculas.get(i).getTitulo().equalsIgnoreCase(filtro)
                 */
                if(peliculas.get(i).getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
                    index = i;
                    listaDePeliculasFiltradas.add(peliculas.get(i));
                }
            }
        }

        return listaDePeliculasFiltradas;
    }
}
