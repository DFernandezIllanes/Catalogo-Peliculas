package com.besysoft.springbootejercitacion1.negocio.dto.mapper;

import com.besysoft.springbootejercitacion1.dominio.Genero;
import com.besysoft.springbootejercitacion1.negocio.dto.GeneroDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GeneroMapper {

    public static Genero mapToEntity(GeneroDTO dto) {
        Genero entity = new Genero();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        return entity;
    }

    public static Genero mapToUpdatedEntity(Genero entity, GeneroDTO dto) {
        entity.setNombre(dto.getNombre());
        return entity;
    }

    public static GeneroDTO mapToDto(Genero entity) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public static List<GeneroDTO> mapListToDto(List<Genero> entities) {
        return entities
                .stream()
                .map(GeneroMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
