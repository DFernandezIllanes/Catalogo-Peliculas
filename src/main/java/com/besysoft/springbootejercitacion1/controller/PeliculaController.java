package com.besysoft.springbootejercitacion1.controller;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.negocio.dto.PeliculaDTO;
import com.besysoft.springbootejercitacion1.negocio.dto.mapper.PeliculaMapper;
import com.besysoft.springbootejercitacion1.services.interfaces.GeneroService;
import com.besysoft.springbootejercitacion1.services.interfaces.PeliculaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/peliculas")
@Slf4j
@Api(value = "Pelicula Controller", tags = "Acciones permitidas para la entidad Pelicula")
public class PeliculaController{

    private final PeliculaService peliculaService;
    private final GeneroService generoService;

    public PeliculaController(PeliculaService peliculaService, GeneroService generoService) {
        this.peliculaService = peliculaService;
        this.generoService = generoService;
    }

    //----------------------------------- METODOS GET ----------------------------------------------------

    /**
     * Devuelve todas las peliculas
     * @return
     */
    @GetMapping()
    @ApiOperation(value = "Consulta todas las Peliculas disponibles en la BD")
    public ResponseEntity<?> obtenerPeliculas() {
        List<Pelicula> peliculas = (List<Pelicula>) this.peliculaService.obtenerTodos();
        List<PeliculaDTO> peliculaDTOList = PeliculaMapper.mapListToDto(peliculas);
        return ResponseEntity.ok(peliculaDTOList);
    }

    /**
     * Devuelve todas las peliculas que coincidan con el filtro ingresado. Si el argumento coincide con algun genero, devuelve
     * todas las peliculas que pertenecen al mismo. Si el argumento no corresponde a genero alguno, devuelve la pelicula cuyo titulo
     * coincida con el valor del argumento. Si no hay coincidencias, devuelve una lista vacia
     * @param filtro
     * @return
     */
    @GetMapping(path = "/{filtro}")
    @ApiOperation(value = "Consulta las Peliculas disponibles en la BD segun el filtro ingresado. " +
            "Si el filtro coincide con algun Genero disponible en la BD, devuelve todas las Peliculas pertenecientes a dicho Genero." +
            "Si no hay coincidencia, devuelve la Pelicula cuyo titulo coincida con el valor del filtro")
    public ResponseEntity<?> getPeliculasPorFiltro(@PathVariable String filtro) {
        Optional<Pelicula> oPelicula = this.peliculaService.buscarPorTitulo(filtro);

        if(oPelicula.isPresent()) {
            Pelicula pelicula = oPelicula.get();
            return ResponseEntity.ok(PeliculaMapper.mapToDto(pelicula));
        }

        Optional<Genero> oGenero = this.generoService.buscarPorNombre(filtro);

        if(oGenero.isPresent()) {
            List<Pelicula> peliculas = oGenero.get().getPeliculas();
            List<PeliculaDTO> peliculaDTOList = PeliculaMapper.mapListToDto(peliculas);
            return ResponseEntity.ok(peliculaDTOList);
        }
        return ResponseEntity.badRequest().body("Ninguna pelicula o genero coinciden con el valor del filtro");
    }

    /**
     * Devuelve todas las peliculas que se encuentran entre dos fechas dadas
     * @param desde
     * @param hasta
     * @return
     */
    @GetMapping(path = "/fecha")
    @ApiOperation(value = "Consulta las Peliculas disponibles en la BD que se encuentren dentro del intervalo de Fechas ingresado")
    public ResponseEntity<?> getPeliculasEntreFechas(@RequestParam(name = "desde", required = true) String desde,
                                                     @RequestParam(name = "hasta", required = true) String hasta) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        LocalDate desdeFecha = LocalDate.parse(desde, formatter);
        LocalDate hastaFecha = LocalDate.parse(hasta, formatter);

        List<Pelicula> peliculas = (List<Pelicula>) this.peliculaService.obtenerPeliculasDesdeFechaHastaFecha(desdeFecha, hastaFecha);
        List<PeliculaDTO> peliculaDTOList = PeliculaMapper.mapListToDto(peliculas);
        return ResponseEntity.ok(peliculaDTOList);
    }

    /**
     * Devuelve todas las peliculas cuya calificacion pertenezcan al intervalo dado
     * @param desde
     * @param hasta
     * @return
     */
    @GetMapping(path = "/calificacion")
    @ApiOperation(value = "Consulta las Peliculas disponibles en la BD que se encuentren dentro del intervalo de Calificaciones ingresado")
    public ResponseEntity<?> getPeliculasEntreCalificaciones(@RequestParam(name = "desde", required = true) Integer desde,
                                                             @RequestParam(name = "hasta", required = true) Integer hasta) {
        List<Pelicula> peliculas = (List<Pelicula>) this.peliculaService.obtenerPeliculasDesdeCalificacionHastaCalificacion(desde, hasta);
        List<PeliculaDTO> peliculaDTOList = PeliculaMapper.mapListToDto(peliculas);
        return ResponseEntity.ok(peliculaDTOList);
    }

    //----------------------------------- METODOS POST ----------------------------------------------------

    /**
     * Agrega una pelicula a la coleccion de peliculas
     * @param peliculaDTO
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "Permite la creacion de una Pelicula")
    public ResponseEntity<?> crearPelicula(@Valid @RequestBody PeliculaDTO peliculaDTO) {

        Pelicula pelicula = PeliculaMapper.mapToEntity(peliculaDTO);
        pelicula = this.peliculaService.createPelicula(pelicula);
        return new ResponseEntity<>(PeliculaMapper.mapToDto(pelicula), HttpStatus.OK);
    }

    //----------------------------------- METODOS PUT ----------------------------------------------------

    /**
     * Actualiza los datos de la pelicula que coincide con el ID dado
     * @param id
     * @param peliculaDTO
     * @return
     */
    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Permite actualizar datos de una Pelicula existente en la BD")
    public ResponseEntity<?> updatePelicula(@PathVariable(name = "id", required = true) Long id,
                                            @Valid @RequestBody PeliculaDTO peliculaDTO) {

        Pelicula pelicula = PeliculaMapper.mapToEntity(peliculaDTO);
        pelicula = this.peliculaService.updatePelicula(id, pelicula);
        return ResponseEntity.ok(PeliculaMapper.mapToDto(pelicula));
    }
}
