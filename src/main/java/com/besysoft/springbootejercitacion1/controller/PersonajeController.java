package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
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
public class PersonajeController extends Controller{

    //------------------------------------ METODOS GET --------------------------------------------------

    @GetMapping()
    public List<Personaje> getPersonajes() {
        return catalogo.getPersonajes();
    }

    @GetMapping(path = "/{filtro}")
    public List<Personaje> getPersonajesPorFiltro(@PathVariable String filtro) {

        return catalogo.buscarPersonajesPorFiltro(filtro);
    }

    //GET /personajes/edad?desde=N&hasta=X
    @GetMapping("/edad")
    public ResponseEntity<?> getPersonajesEntreEdades(@RequestParam(name = "desde", required = true) Integer desde,
                                                      @RequestParam(name = "hasta", required = true) Integer hasta) {

        Map<String, Object> mensajeBody = new HashMap<>();

        Optional<Personaje> oPersonaje = catalogo.getPersonajes().stream().
                filter(p -> p.getEdad() >= desde && p.getEdad() <= hasta).
                findAny();

        if(!oPersonaje.isPresent()) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", String.
                    format("No existe un personaje cuya edad sea mayor que %s", desde, " y menor que %s", hasta));
        }

        List<Personaje> personajes = catalogo.getPersonajes().stream().
                filter(p -> p.getEdad() >= desde && p.getEdad() <= hasta).
                collect(Collectors.toList());

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", personajes);

        return ResponseEntity.ok(mensajeBody);
    }

    //------------------------------------ METODOS POST --------------------------------------------------
    @PostMapping()
    public ResponseEntity<?> createPersonaje(@RequestBody Personaje personaje) {

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        personaje.setId(catalogo.getPersonajes().size()+1l);
        catalogo.addPersonaje(personaje);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje",
                String.format("Id: %d", personaje.getId()));

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.CREATED);
    }

    //------------------------------------ METODOS PUT --------------------------------------------------
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updatePersonaje(@PathVariable(name = "id", required = true) Long id,
                                             @RequestBody Personaje personaje) {

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        Optional<Personaje> oPersonaje = catalogo.getPersonajes().stream().filter(p -> p.getId() == id).findFirst();

        if(!oPersonaje.isPresent()) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje",
                    String.format("No existe un personaje con el id %d", personaje.getId()));

            return ResponseEntity.badRequest().body(mensajeBody);
        }

        personaje.setId(id);
        catalogo.getPersonajes().set(id.intValue()-1, personaje);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", personaje);

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.ACCEPTED);
    }

}
