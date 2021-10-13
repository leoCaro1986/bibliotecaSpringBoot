package com.sofkau.biblioteca.biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecursoDTO {
    private String id;
    private String nombreRecurso;
    private String tematicaRecurso;
    private String tipoRacurso;
    private LocalDate fechaPrestamo;
    private Integer cantidadDisponible;
    private Integer cantidadPrestada;
}
