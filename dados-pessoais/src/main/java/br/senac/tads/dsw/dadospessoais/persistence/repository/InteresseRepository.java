package br.senac.tads.dsw.dadospessoais.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import br.senac.tads.dsw.dadospessoais.persistence.entities.InteresseEntity;

public interface InteresseRepository
    extends JpaRepository<InteresseEntity, Integer> {

    Optional<InteresseEntity> findByNomeIgnoreCase(String nome);

    @Procedure(name = "my_stored_procedure")
    List<InteresseEntity> findUsandoProcedure();

}
