package com.sofkau.biblioteca.biblioteca.services;

import com.sofkau.biblioteca.biblioteca.dtos.RecursoDTO;
import com.sofkau.biblioteca.biblioteca.mappers.RecursoMapper;
import com.sofkau.biblioteca.biblioteca.models.Recurso;
import com.sofkau.biblioteca.biblioteca.repositories.RepositorioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServicioRecurso {
    private RepositorioRecurso repository;
    private RecursoMapper mapper;

    @Autowired
    public ServicioRecurso(RepositorioRecurso repositorioRecurso, RecursoMapper recursoMapper) {
        this.repository = repositorioRecurso;
        this.mapper = recursoMapper;
    }

    public List<RecursoDTO> obtenerTodos() {
        List<RecursoDTO> recursoDTOS = new ArrayList<>();
        repository.findAll().forEach(recurso -> recursoDTOS.add(mapper.convertToDTO(recurso)));
        return recursoDTOS;
    }

    public RecursoDTO guardar(RecursoDTO recursoDTO){
        if (recursoDTO.getNombreRecurso().isEmpty()){
            throw new IllegalArgumentException("el nombre no puede estar vacio");
        }
        Recurso recurso = mapper.convertToDocument(recursoDTO);
        return mapper.convertToDTO(repository.save(recurso));
    }

    public RecursoDTO obtenerPorId(String id){
        Optional<Recurso> recuerso = repository.findById(id);
        if (recuerso.isEmpty()){
            throw new NoSuchElementException("no existe un recurso con el id: " + id);
        }
        return mapper.convertToDTO(recuerso.get());
    }

    public void eliminar(String id){
        repository.delete(mapper.convertToDocument(obtenerPorId(id)));
    }

    public  RecursoDTO actualizar(RecursoDTO recursoDTO){
        Recurso recurso = mapper.convertToDocument(recursoDTO);
        obtenerPorId(recurso.getId());
        return mapper.convertToDTO(repository.save(recurso));
    }

    public String comprobarDisponibilidad(String id){
        RecursoDTO dto = obtenerPorId(id);
        if (disponible(dto)){
            return  "El recurso " + dto.getNombreRecurso() + "esta disponible y hay " + (dto.getCantidadDisponible()-dto.getCantidadPrestada() + " unidades disponibles");
        }
        return  "El recurso" + dto.getNombreRecurso() + "no esta disponible y se presto " + dto.getFechaPrestamo();
    }

    private  boolean disponible(RecursoDTO dto){
        return dto.getCantidadDisponible() > dto.getCantidadPrestada();
    }

    public String prestar(String id){
        RecursoDTO dto = obtenerPorId(id);
        if (disponible(dto)){
            dto.setCantidadPrestada(dto.getCantidadPrestada()+1);
            dto.setFechaPrestamo(LocalDate.now());
            actualizar(dto);
            return "El recurso " + dto.getNombreRecurso() + "se encuentra prestado";
        }
        return "En estos momentos no hay unidades disponibles del recurso " + dto.getNombreRecurso();
    }

    public List<RecursoDTO> recomendarPorTema(String tema){
        List<RecursoDTO> recursoDTOS = new ArrayList<>();
        repository.findByTematicaRecurso(tema).forEach(recurso -> recursoDTOS.add(mapper.convertToDTO(recurso)));
        return recursoDTOS;
    }

    public List<RecursoDTO> recomendarPorTipo(String tipo){
        List<RecursoDTO> recursoDTOS = new ArrayList<>();
        repository.findByTipoRecurso(tipo).forEach(recurso -> recursoDTOS.add(mapper.convertToDTO(recurso)));
        return recursoDTOS;
    }

    public List<RecursoDTO> recomendarPorTemaYTipo(String tema, String tipo){
        List<RecursoDTO> recursoDTOS = new ArrayList<>();
        List<RecursoDTO> recursoDTOS1 = new ArrayList<>();
        recursoDTOS1.addAll(recomendarPorTipo(tipo));
        recursoDTOS1.addAll(recomendarPorTema(tema));
        recursoDTOS1.stream().distinct().forEach(recursoDTO -> recursoDTOS.add(recursoDTO));
        return recursoDTOS;
    }

    public  String regresarRecurso(String id){
        RecursoDTO dto = obtenerPorId(id);
        if (dto.getCantidadPrestada() > 0){
            dto.setCantidadPrestada(dto.getCantidadPrestada() - 1);
            actualizar(dto);
            return "Se ha regresado el recurso " + dto.getNombreRecurso();
        }
        return "El recurso " + dto.getNombreRecurso() + " no se encuentra prestado";
    }



}
