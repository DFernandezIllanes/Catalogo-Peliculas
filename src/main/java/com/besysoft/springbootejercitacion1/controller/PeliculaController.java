package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import com.besysoft.springbootejercitacion1.utilities.Respuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController{

    private final PeliculaService peliculaService;
    private final GeneroService generoService;

    public PeliculaController(PeliculaService peliculaService, GeneroService generoService) {
        this.peliculaService = peliculaService;
        this.generoService = generoService;
    }

    //----------------------------------- METODOS GET ----------------------------------------------------

    /**
     * Devuelve todas las peliculas
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> obtenerPeliculas() {
        return ResponseEntity.ok(this.peliculaService.obtenerTodos());
    }

    /**
     * Devuelve todas las peliculas que coincidan con el filtro ingresado. Si el argumento coincide con algun genero, devuelve
     * todas las peliculas que pertenecen al mismo. Si el argumento no corresponde a genero alguno, devuelve la pelicula cuyo titulo
     * coincida con el valor del argumento. Si no hay coincidencias, devuelve una lista vacia
     * @param filtro
     * @return
     */
    @GetMapping(path = "/{filtro}")
    public ResponseEntity<?> getPeliculasPorFiltro(@PathVariable String filtro) {
        Optional<Pelicula> oPelicula = this.peliculaService.buscarPorTitulo(filtro);

        if(oPelicula.isPresent()) {
            return ResponseEntity.ok(oPelicula.get());
        }

        Optional<Genero> oGenero = this.generoService.buscarPorNombre(filtro);

        if(oGenero.isPresent()) {
            return ResponseEntity.ok(oGenero.get().getPeliculas());
        }
        return ResponseEntity.badRequest().body("Ninguna pelicula o genero coinciden con el valor del filtro");
    }

    /**
     * Devuelve todas las peliculas que se encuentran entre dos fechas dadas
     * @param desde
     * @param hasta
     * @return
     */
    @GetMapping(path = "/fecha")
    public ResponseEntity<?> getPeliculasEntreFechas(@RequestParam(name = "desde", required = true) String desde,
                                                     @RequestParam(name = "hasta", required = true) String hasta) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        LocalDate desdeFecha = LocalDate.parse(desde, formatter);
        LocalDate hastaFecha = LocalDate.parse(hasta, formatter);

        return ResponseEntity.ok(this.peliculaService.obtenerPeliculasDesdeFechaHastaFecha(desdeFecha, hastaFecha));
    }

    /**
     * Devuelve todas las peliculas cuya calificacion pertenezcan al intervalo dado
     * @param desde
     * @param hasta
     * @return
     */
    @GetMapping(path = "/calificacion")
    public ResponseEntity<?> getPeliculasEntreCalificaciones(@RequestParam(name = "desde", required = true) Integer desde,
                                                             @RequestParam(name = "hasta", required = true) Integer hasta) {
        return ResponseEntity.ok(this.peliculaService.obtenerPeliculasDesdeCalificacionHastaCalificacion(desde, hasta));
    }

    //----------------------------------- METODOS POST ----------------------------------------------------

    /**
     * Agrega una pelicula a la coleccion de peliculas
     * @param pelicula
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> crearPelicula(@RequestBody Pelicula pelicula) {

        if(pelicula.getTitulo() == null) {
            return ResponseEntity.badRequest().body("La pelicula debe tener titulo");
        }

        try {
            return new ResponseEntity<>(this.peliculaService.createPelicula(pelicula), HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //----------------------------------- METODOS PUT ----------------------------------------------------

    /**
     * Actualiza los datos de la pelicula que coincide con el ID dado
     * @param id
     * @param pelicula
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updatePelicula(@PathVariable(name = "id", required = true) Long id,
                                            @RequestBody Pelicula pelicula) {

        if(pelicula.getTitulo() == null || pelicula.getTitulo().equals("")) {
            return ResponseEntity.badRequest().body("La pelicula debe tener titulo");
        }

        Optional<Pelicula> optionalPelicula = this.peliculaService.buscarPorId(id);
        if(!optionalPelicula.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("No existe una pelicula con el id %d", id));
        }

        return ResponseEntity.ok(this.peliculaService.updatePelicula(id, pelicula));
    }
}
