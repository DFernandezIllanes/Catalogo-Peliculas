package com.besysoft.springbootejercitacion1.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneroDetailsDTO {

    private Long id;
    private String nombre;
    private List<String> listaPeliculas = new ArrayList<>();
}
