package com.besysoft.springbootejercitacion1.utilities;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.dominio.Personaje;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Catalogo {

    private List<Genero> generos;
    private List<Pelicula> peliculas;
    private List<Personaje> personajes;

    public Catalogo() {
        this.generos = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.personajes = new ArrayList<>();
        this.cargarDatos();
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public void addGenero(Genero genero) {

        if(this.generos.contains(genero)) {
            System.out.println("El genero ya esta agregado a la lista de generos del catalogo");
        } else {
            this.generos.add(genero);
        }
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void addPelicula(Pelicula pelicula) {
        this.peliculas.add(pelicula);
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public void addPersonaje(Personaje personaje) {
        this.personajes.add(personaje);
    }

    public void cargarDatos() {

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

        this.generos.add(cienciaFiccion);
        this.generos.add(animacion);

        this.peliculas.add(elReyLeon);
        this.peliculas.add(unlimitedBladeWorks);
        this.peliculas.add(elPoderInvencible);
        this.peliculas.add(starWars4);
        this.peliculas.add(starWars5);
        this.peliculas.add(starWars6);

        this.personajes.add(saber);
        this.personajes.add(mufasa);
        this.personajes.add(broly);
        this.personajes.add(lukeSkywalker);
        this.personajes.add(leiaOrgana);
    }

    public List<Personaje> buscarPersonajesPorFiltro(String filtro) {
        List<Personaje> listaDePersonajesFiltrados = new ArrayList<>();

        for(Personaje personaje : this.personajes) {
            // Si el valor del filtro es un numero, comparo por edad. Si el filtro no coincide con un valor numerico, comparo por nombre
            if(filtro.matches("[0-9]+") && personaje.getEdad().toString().equals(filtro) ||
            !filtro.matches("[0-9]+") && personaje.getNombre().toLowerCase().contains(filtro.toLowerCase())) {
                //personaje.getNombre().equalsIgnoreCase(filtro)

                listaDePersonajesFiltrados.add(personaje);
            }
        }

        /* Reemplazado por el codigo de arriba

        if(filtro.matches("[0-9]+")) {
            for(Personaje personaje : this.personajes) {
                if(personaje.getEdad().toString().equals(filtro)) {
                    listaDePersonajesFiltrados.add(personaje);
                }
            }
        } else {
            for(Personaje personaje : this.personajes) {
                if(personaje.getNombre().equalsIgnoreCase(filtro)) {
                    listaDePersonajesFiltrados.add(personaje);
                }
            }
        }
         */

        return listaDePersonajesFiltrados;
    }

    public List<Pelicula> buscarPeliculasPorFiltro(String filtro) {
        List<Pelicula> listaDePeliculasFiltradas = new ArrayList<>();
        int index = -1;

        // Compruebo si filtro coincide con alguno de los nombres de la lista de generos
        for(int i=0; i<this.generos.size() && index<0; i++) {
            if(this.generos.get(i).getNombre().equalsIgnoreCase(filtro)) {
                index = i;
            }
        }

        // filtro coincide con el nombre de alguno de los generos
        if(index >= 0) {
            for(Pelicula pelicula : this.generos.get(index).getPeliculas()) {
                listaDePeliculasFiltradas.add(pelicula);
            }
        } else {
            // Compruebo si filtro coincide con alguno de los titulos de la lista de peliculas
            for(int i = 0; i < this.peliculas.size() && index < 0; i++) {
                /* Condicion anterior del if de abajo
                this.peliculas.get(i).getTitulo().equalsIgnoreCase(filtro)
                 */
                if(this.peliculas.get(i).getTitulo().toLowerCase().contains(filtro.toLowerCase())) {
                    index = i;
                    listaDePeliculasFiltradas.add(this.peliculas.get(i));
                }
            }
        }

        return listaDePeliculasFiltradas;
    }
}
