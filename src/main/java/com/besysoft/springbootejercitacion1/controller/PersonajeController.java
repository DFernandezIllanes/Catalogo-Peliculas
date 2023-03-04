package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.negocio.dto.PersonajeDTO;
import com.besysoft.springbootejercitacion1.negocio.dto.mapper.PersonajeMapper;
import com.besysoft.springbootejercitacion1.services.interfaces.PersonajeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personajes")
@Slf4j
@Api(value = "Personaje Controller", tags = "Acciones permitidas para la entidad Personaje")
public class PersonajeController{

    private final PersonajeService personajeService;

    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    //------------------------------------ METODOS GET --------------------------------------------------

    @GetMapping()
    @ApiOperation(value = "Consulta todos los Personajes disponibles en la BD")
    public ResponseEntity<?> getPersonajes() {

        List<Personaje> personajes = (List<Personaje>) this.personajeService.obtenerTodos();
        List<PersonajeDTO> personajeDTOList = PersonajeMapper.mapListToDto(personajes);
        return ResponseEntity.ok(personajeDTOList);
    }

    @GetMapping("/{id}/peliculas")
    @ApiOperation(value = "Consulta el Personaje por ID, mostrando las Peliculas en las que aparece el mismo")
    public ResponseEntity<?> getPersonajeById(@PathVariable(name = "id", required = true) Long id) {

        Optional<Personaje> optionalPersonaje = this.personajeService.buscarPorId(id);
        if(!optionalPersonaje.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("No existe un Personaje con id %d", id));
        }

        Personaje personaje = optionalPersonaje.get();
        return ResponseEntity.ok(PersonajeMapper.mapToDetailsDto(personaje));
    }

    @GetMapping(path = "/{filtro}")
    @ApiOperation(value = "Consulta los Personajes disponibles en la BD que coincidan con el filtro ingresado. " +
            "Si el filtro es un valor numerico, se realizada una busqueda por edad. Sino, devuelve todos los personajes " +
            "cuyo nombre coincidan con el valor del filtro")
    public ResponseEntity<?> getPersonajesPorFiltro(@PathVariable String filtro) {

        List<Personaje> personajes = (List<Personaje>) this.personajeService.obtenerPersonajesPorFiltro(filtro);
        List<PersonajeDTO> personajeDTOList = PersonajeMapper.mapListToDto(personajes);
        return ResponseEntity.ok(personajeDTOList);
    }

    @GetMapping("/edad")
    @ApiOperation(value = "Consulta todos los Personajes disponibles en la BD que pertenezcan al intervalo de edad ingresado")
    public ResponseEntity<?> getPersonajesEntreEdades(@RequestParam(name = "desde", required = true) Integer desde,
                                                      @RequestParam(name = "hasta", required = true) Integer hasta) {
        List<Personaje> personajes = (List<Personaje>) this.personajeService.obtenerPersonajesDesdeEdadHastaEdad(desde, hasta);
        List<PersonajeDTO> personajeDTOList = PersonajeMapper.mapListToDto(personajes);
        return ResponseEntity.ok(personajeDTOList);
    }

    //------------------------------------ METODOS POST --------------------------------------------------

    @PostMapping()
    @ApiOperation(value = "Permite la creacion de un Personaje")
    public ResponseEntity<?> createPersonaje(@Valid @RequestBody PersonajeDTO personajeDTO) {

        Personaje personaje = PersonajeMapper.mapToEntity(personajeDTO);
        personaje = this.personajeService.createPersonaje(personaje);
        return ResponseEntity.ok(PersonajeMapper.mapToDto(personaje));
    }

    //------------------------------------ METODOS PUT --------------------------------------------------

    /**
     * Actualiza los datos del personaje que coincida con el ID pasado
     * @param id
     * @param personajeDTO
     * @return
     */
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Permite actualizar datos de un Personaje existente en la BD")
    public ResponseEntity<?> updatePersonaje(@PathVariable(name = "id", required = true) Long id,
                                             @Valid @RequestBody PersonajeDTO personajeDTO) {

        Personaje personaje = PersonajeMapper.mapToEntity(personajeDTO);
        personaje = this.personajeService.updatePersonaje(id, personaje);
        return ResponseEntity.ok(PersonajeMapper.mapToDto(personaje));
    }
}
