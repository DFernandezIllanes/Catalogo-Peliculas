package com.besysoft.springbootejercitacion1.negocio.dto.mapper;

import com.besysoft.springbootejercitacion1.dominio.Pelicula;
import com.besysoft.springbootejercitacion1.negocio.dto.PeliculaDTO;

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
}
