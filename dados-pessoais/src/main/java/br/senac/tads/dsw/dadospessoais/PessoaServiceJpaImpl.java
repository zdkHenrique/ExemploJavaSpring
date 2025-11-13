package br.senac.tads.dsw.dadospessoais;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads.dsw.dadospessoais.persistence.entities.InteresseEntity;
import br.senac.tads.dsw.dadospessoais.persistence.entities.PessoaEntity;
import br.senac.tads.dsw.dadospessoais.persistence.repository.InteresseRepository;
import br.senac.tads.dsw.dadospessoais.persistence.repository.PessoaRepository;

@Service
public class PessoaServiceJpaImpl implements PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private InteresseRepository interesseRepository;

    private Pessoa toDto(PessoaEntity entity) {
        Pessoa dto = new Pessoa();
        dto.setUsername(entity.getUsername());
        dto.setNome(entity.getNome());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());

        List<String> interesses = new ArrayList<>();
        if (entity.getInteresses() != null) {
            for (InteresseEntity interesseEntity : entity.getInteresses()) {
                interesses.add(interesseEntity.getNome());
            }
        }
        dto.setInteresses(interesses);
        return dto;
    }

    @Override
    public List<Pessoa> findAll() {
        List<PessoaEntity> entities = pessoaRepository.findAll();
        List<Pessoa> resultado = new ArrayList<>();
        for (PessoaEntity entity : entities) {
            resultado.add(toDto(entity));
        }
        return resultado;
    }

    @Override
    public Pessoa findByUsername(String username) {
        Optional<PessoaEntity> optPessoa = pessoaRepository.findByUsername(username);
        if (optPessoa.isEmpty()) {
            // Tratar erro
            return null;
        }
        PessoaEntity entity = optPessoa.get();
        return toDto(entity);
    }

    @Override
    public Pessoa addNew(Pessoa dto) {

        PessoaEntity entity = new PessoaEntity();
        entity.setUsername(dto.getUsername());
        entity.setNome(dto.getNome());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setSenha(dto.getSenha());

        Set<InteresseEntity> interesses = new HashSet<>();
        for (String interesseNome : dto.getInteresses()) {
            Optional<InteresseEntity> optInteresse = interesseRepository.findByNomeIgnoreCase(interesseNome);
            if (optInteresse.isPresent()) {
                interesses.add(optInteresse.get());
            }
        }
        entity.setInteresses(interesses);
        
        pessoaRepository.save(entity);
        return dto;
    }

    @Override
    public Pessoa update(String username, Pessoa pessoa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
