package br.senac.tads.dsw.dadospessoais.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.dadospessoais.persistence.entities.PessoaEntity;

public interface PessoaRepository
    extends JpaRepository<PessoaEntity, Integer> {

    Optional<PessoaEntity> findByUsername(String username);

}
