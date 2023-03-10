package com.besysoft.springbootejercitacion1.dominio;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "fecha_de_creacion")
    private LocalDate fechaDeCreacion;
    @Column(name = "calificacion")
    private Integer calificacion;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "apariciones",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id")
    )
    @JsonIgnoreProperties("peliculas") // Anotacion usada para evitar la recursion infinita
    private List<Personaje> personajes = new ArrayList<>();

    public Pelicula() {
    }

    public Pelicula(Long id, String titulo, LocalDate fechaDeCreacion, Integer calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaDeCreacion = fechaDeCreacion;
        this.calificacion = calificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(LocalDate fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public void setFechaDeCreacion(String fechaDeCreacion) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        this.fechaDeCreacion = LocalDate.parse(fechaDeCreacion, formatter);
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        /*if(calificacion > 0 && calificacion <=5) {
            this.calificacion = calificacion;
        } else {
            System.out.println("La calificacion debe estar entre 1 y 5");
            if(calificacion <= 0) {
                this.calificacion = 0;
            } else {
                this.calificacion = 5;
            }
        }*/
        this.calificacion = calificacion;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public void addPersonaje(Personaje personaje) {
        if(this.personajes.contains(personaje)) {
            System.out.println("El personaje ya esta agregada a la lista de personajes de la pelicula");
        } else {
            this.personajes.add(personaje);
        }
    }

    @Override
    public String toString() {

        String listaPersonajes = "";
        for(Personaje personaje : personajes) {
            listaPersonajes = listaPersonajes + "[" + personaje.getNombre() + "]";
        }

        return "Id: " + this.id +
                ", \nTitulo: " + this.titulo +
                ", \nFecha de Creacion: " + this.fechaDeCreacion +
                ", \nCalificacion: " + this.calificacion +
                ", \nPersonajes: " + listaPersonajes;
    }

    @Override
    public boolean equals(Object obj) {
        Pelicula pelicula = (Pelicula) obj;
        return this.titulo.equalsIgnoreCase(pelicula.titulo);
    }
}
