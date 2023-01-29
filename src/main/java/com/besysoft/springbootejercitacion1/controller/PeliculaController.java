package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.utilities.Catalogo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController{

    //----------------------------------- METODOS GET ----------------------------------------------------

    /**
     * Devuelve todas las peliculas
     * @return
     */
    @GetMapping()
    public List<Pelicula> getPeliculas() {
        return Catalogo.getPeliculas();
    }

    /**
     * Devuelve todas las peliculas que coincidan con el filtro ingresado. Si el argumento coincide con algun genero, devuelve
     * todas las peliculas que pertenecen al mismo. Si el argumento no corresponde a genero alguno, devuelve la pelicula cuyo titulo
     * coincida con el valor del argumento. Si no hay coincidencias, devuelve una lista vacia
     * @param filtro
     * @return
     */
    @GetMapping(path = "/{filtro}")
    public List<Pelicula> getPeliculasPorFiltro(@PathVariable String filtro) {
        return Catalogo.buscarPeliculasPorFiltro(filtro);
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

        Optional<Pelicula> oPelicula = Catalogo.getPeliculas().stream().filter(p -> p.getFechaDeCreacion().isAfter(desdeFecha) && p.getFechaDeCreacion().isBefore(hastaFecha)).findAny();

        if(!oPelicula.isPresent()) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", String.
                    format("Desde %d", desdeFecha, " hasta %d", hastaFecha, " no se estreno ninguna pelicula"));

            return ResponseEntity.
                    badRequest().
                    body(mensajeBody);
        }

        List<Pelicula> peliculas = Catalogo.getPeliculas().
                stream().
                filter(p -> p.getFechaDeCreacion().isAfter(desdeFecha) && p.getFechaDeCreacion().isBefore(hastaFecha)).
                collect(Collectors.toList());

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", peliculas);

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

        Map<String, Object> mensajeBody = new HashMap<>();

        Optional<Pelicula> oPelicula = Catalogo.getPeliculas().stream().filter(p -> p.getCalificacion()>= desde && p.getCalificacion()<= hasta).findAny();

        if(!oPelicula.isPresent()) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", String.
                    format("No hay peliculas con calificacion superior entre %s", desde, " y %s", hasta));

            return ResponseEntity.
                    badRequest().
                    body(mensajeBody);
        }

        List<Pelicula> peliculas = Catalogo.getPeliculas().stream().
                filter(p -> p.getCalificacion()>= desde && p.getCalificacion()<= hasta).
                collect(Collectors.toList());

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", peliculas);

        return ResponseEntity.ok(mensajeBody);
    }

    //----------------------------------- METODOS POST ----------------------------------------------------

    /**
     * Agrega una pelicula a la coleccion de peliculas
     * @param pelicula
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> crearPelicula(@RequestBody Pelicula pelicula) {

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        pelicula.setId((long)(Catalogo.getPeliculas().size()+1));
        Catalogo.addPelicula(pelicula);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", String.
                format("Id: %s", pelicula.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(mensajeBody);
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

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        Optional<Pelicula> oPelicula = Catalogo.getPeliculas().stream().filter(p -> p.getId() == id).findFirst();

        if(!oPelicula.isPresent()) {

            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", String.
                    format("No existe una pelicula con el id %d", id));

            return ResponseEntity.
                    badRequest().
                    headers(headers).
                    body(mensajeBody);
        }

        pelicula.setId(id);
        Catalogo.getPeliculas().set(id.intValue()-1, pelicula);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", pelicula);

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.ACCEPTED);
    }
}
