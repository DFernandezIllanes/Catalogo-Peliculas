package com.besysoft.springbootejercitacion1.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaDetailsDTO {

    private Long id;
    private String titulo;
    private LocalDate fechaDeCreacion;
    private Integer calificacion;
    private List<String> listaPersonajes = new ArrayList<>();
}
