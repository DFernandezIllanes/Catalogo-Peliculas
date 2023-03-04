package com.besysoft.springbootejercitacion1.negocio.dto.mapper;

import com.besysoft.springbootejercitacion1.dominio.Personaje;
import com.besysoft.springbootejercitacion1.negocio.dto.PersonajeDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PersonajeMapper {

    public static Personaje mapToEntity(PersonajeDTO dto) {
        Personaje entity = new Personaje();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setPeso(dto.getPeso());
        entity.setHistoria(dto.getHistoria());
        entity.setEdad(dto.getEdad());
        return entity;
    }

    public static PersonajeDTO mapToDto(Personaje entity) {
        PersonajeDTO dto = new PersonajeDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEdad(entity.getEdad());
        dto.setPeso(entity.getPeso());
        dto.setHistoria(entity.getHistoria());
        return dto;
    }

    public static List<PersonajeDTO> mapListToDto(List<Personaje> entities) {
        return entities
                .stream()
                .map(PersonajeMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
