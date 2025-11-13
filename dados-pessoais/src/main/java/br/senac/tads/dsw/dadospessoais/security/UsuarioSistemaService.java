package br.senac.tads.dsw.dadospessoais.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UsuarioSistemaService implements UserDetailsService {

    private Map<String, UsuarioSistema> mapUsuarios;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioSistemaService() {
    }

    @PostConstruct
    public void init() {
        mapUsuarios = new HashMap<>();
        mapUsuarios.put("fulano", new UsuarioSistema("fulano", 
            "Fulano da Silva", passwordEncoder.encode("Abcd%1234"),
            List.of(new Permissao("PEAO"))));
        mapUsuarios.put("ciclano", new UsuarioSistema("ciclano",
            "Ciclano de Souza", passwordEncoder.encode("Abcd%1234"),
            List.of(new Permissao("PEAO"), new Permissao("GERENTE"))));
        mapUsuarios.put("beltrana", new UsuarioSistema("beltrana",
            "Beltrana dos Santos", passwordEncoder.encode("Abcd%1234"),
            List.of(new Permissao("GERENTE"), new Permissao("DIRETOR"))));
    }

    @Override
    public UsuarioSistema loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UsuarioSistema usuario = mapUsuarios.get(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário %s não encontrado".formatted(username));
        }
        return usuario;
    }

}
