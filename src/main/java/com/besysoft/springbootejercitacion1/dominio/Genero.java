package com.besysoft.springbootejercitacion1.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "generos")
public class Genero {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id", referencedColumnName = "id")
    private List<Pelicula> peliculas = new ArrayList<>();

    public Genero() {
    }

    public Genero(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void addPelicula(Pelicula pelicula) {
        if(this.peliculas.contains(pelicula)) {
            System.out.println("La pelicula ya esta agregada a la lista de peliculas del genero");
        } else {
            this.peliculas.add(pelicula);
        }
    }

    @Override
    public String toString() {

        String listaPeliculas = "";
        for(Pelicula pelicula : peliculas) {
            listaPeliculas = listaPeliculas + "[" + pelicula.getTitulo() + "]";
        }
        return "Id: " + this.id +
                ", \nGenero: " + this.nombre +
                ", \nPeliculas: " + listaPeliculas;
    }

    @Override
    public boolean equals(Object obj) {
        Genero genero = (Genero) obj;
        return this.nombre.equalsIgnoreCase(genero.nombre);
    }
}
