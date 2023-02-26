package com.besysoft.springbootejercitacion1.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "personajes")
public class Personaje {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "edad")
    private Integer edad;
    @Column(name = "peso")
    private Double peso;
    @Column(name = "historia")
    private String historia;

    @ManyToMany(mappedBy = "personajes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("personajes") // Anotacion usada para evitar la recursion infinita
    private List<Pelicula> peliculas = new ArrayList<>();

    public Personaje() {
    }

    public Personaje(Long id, String nombre, Integer edad, Double peso, String historia) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void addPelicula(Pelicula pelicula) {
        if(this.peliculas.contains(pelicula)) {
            System.out.println("La pelicula ya esta agregada a la lista de apariciones del personaje");
        } else {
            this.peliculas.add(pelicula);
        }
    }

    @Override
    public String toString() {
        String nombresPeliculas = "";
        for(Pelicula pelicula : peliculas) {
            nombresPeliculas = nombresPeliculas + "[" + pelicula.getTitulo() + "]";
        }
        return "Id: " + this.id +
                ", \nNombre: " + this.nombre +
                ", \nEdad: " + this.edad +
                ", \nPeso: " + this.peso +
                ", \nHistoria: " + this.historia +
                ", \nPeliculas: " + nombresPeliculas;
    }

    @Override
    public boolean equals(Object obj) {
        Personaje personaje = (Personaje) obj;
        return this.nombre.equalsIgnoreCase(personaje.nombre);
    }
}
