package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.negocio.dto.GeneroDTO;
import com.besysoft.springbootejercitacion1.negocio.dto.PeliculaDTO;
import com.besysoft.springbootejercitacion1.negocio.dto.mapper.GeneroMapper;
import com.besysoft.springbootejercitacion1.negocio.dto.mapper.PeliculaMapper;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/generos")
@Slf4j
@Api(value = "Genero Controller", tags = "Acciones permitidas para la entidad Genero")
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
    @ApiOperation(value = "Consulta todos los Generos disponibles en la BD")
    public ResponseEntity<?> getGeneros() {

        List<Genero> generoList = (List<Genero>) this.generoService.obtenerTodos();
        List<GeneroDTO> generoDTOList = GeneroMapper.mapListToDto(generoList);
        return ResponseEntity.ok(generoDTOList);
    }

    //------------------------------------------ METODOS POST ----------------------------------------

    /**
     * Agrega un genero a la coleccion de generos
     * @param generoDTO
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "Permite la creacion de un Genero")
    public ResponseEntity<?> createGenero(@Valid @RequestBody GeneroDTO generoDTO) {

        Genero genero = GeneroMapper.mapToEntity(generoDTO);
        genero = this.generoService.createGenero(genero);
        return ResponseEntity.ok(GeneroMapper.mapToDto(genero));
    }

    //------------------------------------------ METODOS PUT ----------------------------------------

    /**
     * Actualiza los datos del genero que coincida con el ID indicado
     * @param id
     * @param generoDTO
     * @return
     */
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Permite actualizar datos de un Genero existente en la BD")
    public ResponseEntity<?> updateGenero(@PathVariable(name = "id", required = true) Long id,
                                          @Valid @RequestBody GeneroDTO generoDTO) {

        Genero genero = GeneroMapper.mapToEntity(generoDTO);
        genero = this.generoService.updateGenero(id, genero);
        return ResponseEntity.ok(GeneroMapper.mapToDto(genero));
    }
}
