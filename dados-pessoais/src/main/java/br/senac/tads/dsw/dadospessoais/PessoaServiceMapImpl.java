package br.senac.tads.dsw.dadospessoais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PessoaServiceMapImpl implements PessoaService {

    private Map<String, Pessoa> mapPessoas;

    public PessoaServiceMapImpl() {
        mapPessoas = new HashMap<>();
        mapPessoas.put("fulano", new Pessoa("fulano", "Fulano da Silva",
                LocalDate.parse("2000-10-20"),
                "fulano@email.com", "11 91234-1234", "senhaAbcd1234",
                List.of("Java", "Spring Boot")));
        mapPessoas.put("ciclano", new Pessoa("ciclano", "Ciclano de Souza",
                LocalDate.parse("1999-05-14"),
                "ciclano@email.com", "11 98765-8765", "senhaAbcd1234",
                List.of("HTML", "CSS", "Javascript")));
        mapPessoas.put("beltrana", new Pessoa("beltrana", "Beltrana dos Santos",
                LocalDate.parse("2021-02-18"),
                "beltrana@email.com", "11 91122-1234", "senhaAbcd1234",
                List.of("Javascript", "Angular", "React")));
    }

    @Override
    public List<Pessoa> findAll() {
        List<Pessoa> pessoas = new ArrayList<>(mapPessoas.values());
        return pessoas;
    }

    @Override
    public Pessoa findByUsername(String username) {
        return mapPessoas.get(username);
    }

    @Override
    public Pessoa addNew(Pessoa pessoa) {
        mapPessoas.put(pessoa.getUsername(), pessoa);
        return pessoa;
    }

    @Override
    public Pessoa update(String username, Pessoa pessoa) {
        if (!mapPessoas.containsKey(username)) {
            // erro
        }
        mapPessoas.put(username, pessoa);
        return pessoa;
    }

    @Override
    public void delete(String username) {
        mapPessoas.remove(username);
    }

}
