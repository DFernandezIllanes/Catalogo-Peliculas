package com.besysoft.springbootejercitacion1.negocio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaDTO {

    private Long id;
    @NotNull
    @NotEmpty
    private String titulo;
    private LocalDate fechaDeCreacion;
    private Integer calificacion;
}
