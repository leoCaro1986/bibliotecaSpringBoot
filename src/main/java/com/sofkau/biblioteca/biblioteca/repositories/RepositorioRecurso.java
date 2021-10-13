package com.sofkau.biblioteca.biblioteca.repositories;

import com.sofkau.biblioteca.biblioteca.models.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioRecurso extends MongoRepository<Recurso, String> {
    List<Recurso> findByTipoRecurso(final String tipo);
    List<Recurso> findByTematicaRecurso(final String tematica);
}
