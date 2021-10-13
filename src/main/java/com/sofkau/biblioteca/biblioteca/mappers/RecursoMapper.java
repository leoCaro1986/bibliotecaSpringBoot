package com.sofkau.biblioteca.biblioteca.mappers;

import com.sofkau.biblioteca.biblioteca.dtos.RecursoDTO;
import com.sofkau.biblioteca.biblioteca.models.Recurso;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RecursoMapper {
    private ModelMapper mapper;

    public RecursoMapper(ModelMapper mapper){
        this.mapper = mapper;
    }

    public RecursoDTO convertToDTO(Recurso recurso){
        RecursoDTO recursoDTO = mapper.map(recurso, RecursoDTO.class);
        return recursoDTO;
    }

    public Recurso convertToDocument(RecursoDTO recursoDTO){
        Recurso recurso= mapper.map(recursoDTO, Recurso.class);
        return recurso;
    }

}
