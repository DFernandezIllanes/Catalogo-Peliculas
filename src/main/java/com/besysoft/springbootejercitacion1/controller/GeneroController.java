package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.utilities.Catalogo;
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

    //------------------------------------------ METODOS GET ----------------------------------------

    /**
     * Devuelve todos los generos
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> getGeneros() {

        return ResponseEntity.ok(Catalogo.getGeneros());
    }

    //------------------------------------------ METODOS POST ----------------------------------------

    /**
     * Agrega un genero a la coleccion de generos
     * @param genero
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createGenero(@RequestBody Genero genero) {

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        genero.setId(Catalogo.getGeneros().size()+1l);

        Catalogo.addGenero(genero);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje",
                String.format("Id: %d", genero.getId()));

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.CREATED);
    }

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

        Map<String, Object> mensajeBody = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "nombre@dominio.com");

        Optional<Genero> oGenero = Catalogo.getGeneros().stream().filter(g -> g.getId() == id).findFirst();

        if(!oGenero.isPresent()) {
            mensajeBody.put("success", Boolean.FALSE);
            mensajeBody.put("mensaje", String.
                    format("No existe un genero con el id %d", id));

            return ResponseEntity.
                    badRequest().
                    headers(headers).
                    body(mensajeBody);
        }

        genero.setId(id);
        Catalogo.getGeneros().set(id.intValue()-1, genero);

        mensajeBody.put("success", Boolean.TRUE);
        mensajeBody.put("mensaje", genero);

        return new ResponseEntity<Map<String, Object>>(mensajeBody, headers, HttpStatus.ACCEPTED);
    }
}
