package com.besysoft.springbootejercitacion1.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonajeDTO {

    private Long id;
    @NotNull
    @NotEmpty
    private String nombre;
    private Integer edad;
    private Double peso;
    private String historia;
}
