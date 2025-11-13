package br.senac.tads.dsw.dadospessoais;

import java.util.List;

public interface PessoaService {

    List<Pessoa> findAll();

    Pessoa findByUsername(String username);

    Pessoa addNew(Pessoa pessoa);

    Pessoa update(String username, Pessoa pessoa);

    void delete(String username);

}