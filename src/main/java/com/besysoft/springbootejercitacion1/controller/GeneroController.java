package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/generos")
public class GeneroController{

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    //------------------------------------------ METODOS GET ----------------------------------------

    /**
     * Devuelve todos los generos
     * @return
     */
    @GetMapping()
    public ResponseEntity<?> getGeneros() {
        return ResponseEntity.ok(this.generoService.obtenerTodos());
    }

    //------------------------------------------ METODOS POST ----------------------------------------

    /**
     * Agrega un genero a la coleccion de generos
     * @param genero
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> createGenero(@RequestBody Genero genero) {

        if(genero.getNombre() == null) {
            return ResponseEntity.badRequest().body("El genero debe tener nombre");
        }

        Optional<Genero> optionalGenero = this.generoService.buscarPorNombre(genero.getNombre());
        if(optionalGenero.isPresent()) {
            return new ResponseEntity<>("Ya existe un genero con el mismo nombre", HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(this.generoService.createGenero(genero));
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

        Optional<Genero> oGenero = this.generoService.buscarPorId(id);

        if(!oGenero.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("No existe un genero con el id %d", id));
        }
        return ResponseEntity.ok(this.generoService.updateGenero(id, genero));
    }
}
