package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import com.besysoft.springbootejercitacion1.utilities.Respuesta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/generos")
public class GeneroController{

    private final GeneroService service;
    private final PeliculaService peliculaService;

    public GeneroController(GeneroService service, PeliculaService peliculaService) {
        this.service = service;
        this.peliculaService = peliculaService;
    }

    //------------------------------------------ METODOS GET ----------------------------------------

    /**
     * Devuelve todos los generos
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> getGeneros() {

        return Respuesta.generar(Boolean.TRUE, this.service.obtenerTodos());
        //return ResponseEntity.ok(this.service.obtenerTodos());
    }

    //------------------------------------------ METODOS POST ----------------------------------------

    /**
     * Agrega un genero a la coleccion de generos
     * @param genero
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createGenero(@RequestBody Genero genero) {

        Genero nuevoGenero = this.service.createGenero(genero);
        if(nuevoGenero == null) {
            return Respuesta.generar(Boolean.FALSE, "Ya existe un genero con ese nombre");
        }

        return Respuesta.generar(Boolean.TRUE, nuevoGenero);
    }

    /*@PostMapping(path = "/{id}")
    public ResponseEntity<?> agregarPelicula(@PathVariable(name = "id") Long id,
                                             @RequestBody Pelicula pelicula) {

        //TODO comprobar validaciones y ubicacion de las mismas
        if(pelicula.getId() != null ) {
            Optional<Pelicula> optionalPelicula = this.peliculaService.buscarPorId(pelicula.getId());
            if(!optionalPelicula.isPresent()) {
                return ResponseEntity.badRequest().body("No existe la pelicula");
            } else {
                Pelicula pelicula1 = optionalPelicula.get();

                return ResponseEntity.ok(this.service.agregarPelicula(id, pelicula1));
            }
        } else {
            return ResponseEntity.badRequest().body("La pelicula debe tener un ID");
        }
    }*/

    //------------------------------------------ METODOS PUT ----------------------------------------

    /**
     * Actualiza los datos del genero que coincida con el ID indicado
     * @param id
     * @param genero
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateGenero(@PathVariable(name = "id", required = true) Long id,
                                          @RequestBody Genero genero) {

        Optional<Genero> oGenero = this.service.buscarPorId(id);

        if(!oGenero.isPresent()) {
            return Respuesta.generar(Boolean.FALSE, String.format("No existe un genero con el id %d", id));
        }

        return Respuesta.generar(Boolean.TRUE, this.service.updateGenero(id, genero));
    }
}
