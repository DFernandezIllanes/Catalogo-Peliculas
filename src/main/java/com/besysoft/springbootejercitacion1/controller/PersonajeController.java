package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.utilities.Catalogo;
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

    //------------------------------------ METODOS GET --------------------------------------------------

    /**
     * Devuelve todos los personajes
     * @return
     */
    @GetMapping()
    public List<Personaje> getPersonajes() {
        return Catalogo.getPersonajes();
    }

    /**
     * Devuelve una lista de personajes que coincidan con el argumento pasado como filtro. Si el argumento pasado es un numero,
     * devuelve todos los personajes cuya edad coincidan con dicho numero. Si el argumento no es un numero, devuelve todos los
     * personajes cuyo nombre contengan el valor del argumento. Si no hay coincidencias, devuelve una lista vacia
     * @param filtro
     * @return
     */
    @GetMapping(path = "/{filtro}")
    public List<Personaje> getPersonajesPorFiltro(@PathVariable String filtro) {

        return Catalogo.buscarPersonajesPorFiltro(filtro);
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

        Map<String, Object> mensajeBody = new HashMap<>();

        Optional<Personaje> oPersonaje = Catalogo.getPersonajes().stream().
                filter(p -> p.getEdad() >= desde && p.getEdad() <= hasta).
                findAny();

        if(!oPersonaje.isPresent()) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", String.
                    format("No existe un personaje cuya edad sea mayor que %s", desde, " y menor que %s", hasta));
        }

        List<Personaje> personajes = Catalogo.
                getPersonajes().stream().
                filter(p -> p.getEdad() >= desde && p.getEdad() <= hasta).
                collect(Collectors.toList());

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", personajes);

        return ResponseEntity.ok(mensajeBody);
    }

    //------------------------------------ METODOS POST --------------------------------------------------

    /**
     * Agrega un personaje a la coleccion de personajes
     * @param personaje
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createPersonaje(@RequestBody Personaje personaje) {

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        personaje.setId(Catalogo.getPersonajes().size()+1l);
        Catalogo.addPersonaje(personaje);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje",
                String.format("Id: %d", personaje.getId()));

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.CREATED);
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

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        Optional<Personaje> oPersonaje = Catalogo.getPersonajes().stream().filter(p -> p.getId() == id).findFirst();

        if(!oPersonaje.isPresent()) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje",
                    String.format("No existe un personaje con el id %d", personaje.getId()));

            return ResponseEntity.badRequest().body(mensajeBody);
        }

        personaje.setId(id);
        Catalogo.getPersonajes().set(id.intValue()-1, personaje);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", personaje);

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.ACCEPTED);
    }

}
