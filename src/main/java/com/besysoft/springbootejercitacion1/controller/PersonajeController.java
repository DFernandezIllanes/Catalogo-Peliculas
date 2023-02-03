package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.services.interfaces.PersonajeService;
import com.besysoft.springbootejercitacion1.utilities.Catalogo;
import com.besysoft.springbootejercitacion1.utilities.Respuesta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personajes")
public class PersonajeController{

    private final PersonajeService service;

    public PersonajeController(PersonajeService service) {
        this.service = service;
    }

    //------------------------------------ METODOS GET --------------------------------------------------

    /**
     * Devuelve todos los personajes
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> getPersonajes() {
        return Respuesta.generar(Boolean.TRUE, this.service.obtenerTodos());
    }

    /**
     * Devuelve una lista de personajes que coincidan con el argumento pasado como filtro. Si el argumento pasado es un numero,
     * devuelve todos los personajes cuya edad coincidan con dicho numero. Si el argumento no es un numero, devuelve todos los
     * personajes cuyo nombre contengan el valor del argumento. Si no hay coincidencias, devuelve una lista vacia
     * @param filtro
     * @return
     */
    @GetMapping(path = "/{filtro}")
    public ResponseEntity<?> getPersonajesPorFiltro(@PathVariable String filtro) {
        return Respuesta.generar(Boolean.TRUE, this.service.obtenerPersonajesPorFiltro(filtro));
    }

    /**
     * Devuelve todos los personajes cuya edad se encuentren dentro del intervalo indicado por los parametros
     * @param desde
     * @param hasta
     * @return
     */
    @GetMapping("/edad")
    public ResponseEntity<?> getPersonajesEntreEdades(@RequestParam(name = "desde", required = true) Integer desde,
                                                      @RequestParam(name = "hasta", required = true) Integer hasta) {

        return Respuesta.generar(Boolean.TRUE, this.service.obtenerPersonajesDesdeEdadHastaEdad(desde, hasta));
    }

    //------------------------------------ METODOS POST --------------------------------------------------

    /**
     * Agrega un personaje a la coleccion de personajes
     * @param personaje
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createPersonaje(@RequestBody Personaje personaje) {

        /*Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje",
                String.format("Id: %d", this.service.createPersonaje(personaje).getId()));

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.CREATED);*/
        Personaje personaje1 = this.service.createPersonaje(personaje);

        if(personaje1 == null) {
            return Respuesta.generar(Boolean.FALSE, "No se pudo crear el personaje");
        }

        return Respuesta.generar(HttpStatus.CREATED, Boolean.TRUE, String.format("Id: %d", personaje1.getId()));
    }

    //------------------------------------ METODOS PUT --------------------------------------------------

    /**
     * Actualiza los datos del personaje que coincida con el ID pasado
     * @param id
     * @param personaje
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updatePersonaje(@PathVariable(name = "id", required = true) Long id,
                                             @RequestBody Personaje personaje) {

        Personaje personaje1 = this.service.updatePersonaje(id, personaje);

        if(personaje1 == null) {
            return Respuesta.generar(Boolean.FALSE, "No se pudo actualizar los datos del personaje");
        }

        return Respuesta.generar(HttpStatus.ACCEPTED, Boolean.TRUE, personaje1);
    }

}
