package com.besysoft.springbootejercitacion1.negocio.dto.mapper;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.negocio.dto.PeliculaDTO;
import com.besysoft.springbootejercitacion1.negocio.dto.PeliculaDetailsDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PeliculaMapper {

    public static Pelicula mapToEntity(PeliculaDTO dto) {
        Pelicula entity = new Pelicula();
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setCalificacion(dto.getCalificacion());
        entity.setFechaDeCreacion(dto.getFechaDeCreacion());
        return entity;
    }

    public static PeliculaDTO mapToDto(Pelicula entity) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setCalificacion(entity.getCalificacion());
        dto.setFechaDeCreacion(entity.getFechaDeCreacion());
        return dto;
    }

    public static List<PeliculaDTO> mapListToDto(List<Pelicula> entities) {
        return entities
                .stream()
                .map(PeliculaMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public static PeliculaDetailsDTO mapToDetailsDto(Pelicula entity) {
        PeliculaDetailsDTO dto = new PeliculaDetailsDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setFechaDeCreacion(entity.getFechaDeCreacion());
        dto.setCalificacion(entity.getCalificacion());
        dto.setListaPersonajes(entity
                .getPersonajes()
                .stream()
                .map(Personaje::getNombre)
                .collect(Collectors.toList())
        );
        return dto;
    }
}
