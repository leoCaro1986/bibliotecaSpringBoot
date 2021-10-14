package com.sofkau.biblioteca.biblioteca.services;

import com.sofkau.biblioteca.biblioteca.dtos.RecursoDTO;
import com.sofkau.biblioteca.biblioteca.mappers.RecursoMapper;
import com.sofkau.biblioteca.biblioteca.models.Recurso;
import com.sofkau.biblioteca.biblioteca.repositories.RepositorioRecurso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServicioRecursoTest {

    @MockBean
    private RepositorioRecurso repository;
    @Autowired
    private ServicioRecurso servicioRecurso;
    @Autowired
    private RecursoMapper recursoMapper;


    @Test
    @DisplayName("traer todos los recursos")
    void getTodos() {
        var primerRecurso = new Recurso();

        Mockito.when(repository.findAll()).thenReturn(recursos());
        var result = servicioRecurso.obtenerTodos();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("l20", result.get(0).getId());
        Assertions.assertEquals("50 sombras", result.get(0).getNombreRecurso());
        Assertions.assertEquals(2, result.get(0).getCantidadDisponible());
        Assertions.assertEquals(null, result.get(0).getFechaPrestamo());
        Assertions.assertEquals(0, result.get(0).getCantidadPrestada());
        Assertions.assertEquals("Libro", result.get(0).getTipoRecurso());
        Assertions.assertEquals("terror",result.get(0).getTematicaRecurso());
        Assertions.assertEquals("t15", result.get(1).getId());
        Assertions.assertEquals("cromos", result.get(1).getNombreRecurso());
        Assertions.assertEquals(5, result.get(1).getCantidadDisponible());
        Assertions.assertEquals(LocalDate.now(),result.get(1).getFechaPrestamo());
        Assertions.assertEquals(1, result.get(1).getCantidadPrestada());
        Assertions.assertEquals("revista", result.get(1).getTipoRecurso());
        Assertions.assertEquals("farandula",result.get(1).getTematicaRecurso());
    }

    private List<Recurso> recursos() {

        var primerRecurso = new Recurso();
        primerRecurso.setId("l20");
        primerRecurso.setNombreRecurso("50 sombras");
        primerRecurso.setCantidadDisponible(2);
        primerRecurso.setFechaPrestamo(null);
        primerRecurso.setCantidadPrestada(0);
        primerRecurso.setTipoRecurso("Libro");
        primerRecurso.setTematicaRecurso("terror");
        var segundoRecurso = new Recurso();
        segundoRecurso.setId("t15");
        segundoRecurso.setNombreRecurso("cromos");
        segundoRecurso.setCantidadDisponible(5);
        segundoRecurso.setFechaPrestamo(LocalDate.now());
        segundoRecurso.setCantidadPrestada(1);
        segundoRecurso.setTipoRecurso("revista");
        segundoRecurso.setTematicaRecurso("farandula");
        var recursos = new ArrayList<Recurso>();
        recursos.add(primerRecurso);
        recursos.add(segundoRecurso);
        return recursos;
    }


    @Test
    @DisplayName("Test para crear recurso")
    void guardar(){
        var primerRecurso=new RecursoDTO();
        primerRecurso.setNombreRecurso("50 sombras");
        primerRecurso.setCantidadDisponible(2);
        primerRecurso.setFechaPrestamo(null);
        primerRecurso.setCantidadPrestada(0);
        primerRecurso.setTipoRecurso("Libro");
        primerRecurso.setTematicaRecurso("terror");

        Mockito.when(repository.save(Mockito.any())).thenReturn(recursos().get(0));

        var result = servicioRecurso.guardar(primerRecurso);

        Assertions.assertNotNull(result, "no puede ser nulo");
        Assertions.assertEquals("50 sombras", result.getNombreRecurso(), "Debe ser igual el nombre");
        Assertions.assertEquals(2, result.getCantidadDisponible(), "La cantidad disponible debe ser igual");
        Assertions.assertEquals(null, result.getFechaPrestamo(), "La fecha debe ser nula");
        Assertions.assertEquals(0, result.getCantidadPrestada(), "La cantidad prestada no corresponde");
        Assertions.assertEquals("Libro", result.getTipoRecurso(), "El tipo de recurso debe ser igual");
        Assertions.assertEquals("terror", result.getTematicaRecurso(), "La tematica debe ser igual");
    }


    @Test
    @DisplayName("buscar recurso por id")
    void obtenerPorId(){
        Mockito.when(repository.findById(Mockito.any())).thenReturn(recursos().stream().findAny().stream().findFirst());

        var result = servicioRecurso.obtenerPorId(recursos().get(0).getId());
        Assertions.assertEquals(recursos().get(0).getId(),result.getId(), "El id no corresponde");
        Assertions.assertEquals("50 sombras", result.getNombreRecurso(), "Debe ser igual el nombre");
        Assertions.assertEquals(2, result.getCantidadDisponible(), "La cantidad disponible debe ser igual");
        Assertions.assertEquals(null, result.getFechaPrestamo(), "La fecha debe ser nula");
        Assertions.assertEquals(0, result.getCantidadPrestada(), "La cantidad prestada no corresponde");
        Assertions.assertEquals("Libro", result.getTipoRecurso(), "El tipo de recurso debe ser igual");
        Assertions.assertEquals("terror", result.getTematicaRecurso(), "La tematica debe ser igual");
    }



}