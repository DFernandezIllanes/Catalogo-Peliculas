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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController{

    private final PeliculaService service;
    private final GeneroService generoService;

    public PeliculaController(PeliculaService service, GeneroService generoService) {
        this.service = service;
        this.generoService = generoService;
    }

    //----------------------------------- METODOS GET ----------------------------------------------------

    /**
     * Devuelve todas las peliculas
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> obtenerPeliculas() {
        return Respuesta.generar(Boolean.TRUE, this.service.obtenerTodos());
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
        Optional<Pelicula> oPelicula = this.service.buscarPorTitulo(filtro);

        if(oPelicula.isPresent()) {
            return Respuesta.generar(Boolean.TRUE, Arrays.asList(oPelicula.get()));
        }

        Optional<Genero> oGenero = this.generoService.buscarPorNombre(filtro);

        if(oGenero.isPresent()) {
            return Respuesta.generar(Boolean.TRUE, oGenero.get().getPeliculas());
        }

        return Respuesta.generar(Boolean.FALSE, "No existe ningun genero o pelicula que coincida con el filtro indicado");
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

        Map<String, Object> mensajeBody = new HashMap<>();

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", this.service.obtenerPeliculasDesdeFechaHastaFecha(desdeFecha, hastaFecha));

        return ResponseEntity.ok(mensajeBody);
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

        return Respuesta.generar(Boolean.TRUE,
                this.service.obtenerPeliculasDesdeCalificacionHastaCalificacion(desde, hasta));
    }

    //----------------------------------- METODOS POST ----------------------------------------------------

    /**
     * Agrega una pelicula a la coleccion de peliculas
     * @param pelicula
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> crearPelicula(@RequestBody Pelicula pelicula) {

        Pelicula nuevaPelicula = this.service.createPelicula(pelicula);
        if(nuevaPelicula == null) {
            return  Respuesta.generar(Boolean.FALSE, "Ya existe una pelicula con ese nombre");
        }

        return Respuesta.generar(HttpStatus.CREATED, Boolean.TRUE,
                String.format("Id: %s", nuevaPelicula.getId()));
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

        Pelicula peliculaActualizada = this.service.updatePelicula(id, pelicula);
        if(peliculaActualizada == null) {
            return Respuesta.generar(Boolean.FALSE, "No se pudo actualizar los datos de la pelicula");
        }

        return Respuesta.generar(HttpStatus.ACCEPTED, Boolean.TRUE, peliculaActualizada);
    }
}
