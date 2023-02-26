package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.services.interfaces.PersonajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personajes")
public class PersonajeController{

    private final PersonajeService personajeService;

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    //------------------------------------ METODOS GET --------------------------------------------------

    /**
     * Devuelve todos los personajes
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> getPersonajes() {
        return ResponseEntity.ok(this.personajeService.obtenerTodos());
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
        return ResponseEntity.ok(this.personajeService.obtenerPersonajesPorFiltro(filtro));
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
        return ResponseEntity.ok(this.personajeService.obtenerPersonajesDesdeEdadHastaEdad(desde, hasta));
    }

    //------------------------------------ METODOS POST --------------------------------------------------

    /**
     * Agrega un personaje a la coleccion de personajes
     * @param personaje
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createPersonaje(@RequestBody Personaje personaje) {

        if(personaje.getNombre() == null) {
            return ResponseEntity.badRequest().body("El personaje debe tener un nombre");
        }

        return ResponseEntity.ok(this.personajeService.createPersonaje(personaje));
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

        if(personaje.getNombre() == null) {
            return ResponseEntity.badRequest().body("El personaje debe tener nombre");
        }

        return ResponseEntity.ok(this.personajeService.updatePersonaje(id, personaje));
    }

}
