package com.besysoft.springbootejercitacion1.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonajeDetailsDTO {

    private Long id;
    private String nombre;
    private Double peso;
    private String historia;
    private List<String> listaPeliculas = new ArrayList<>();
}
