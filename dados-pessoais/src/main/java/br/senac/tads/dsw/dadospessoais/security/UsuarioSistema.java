package br.senac.tads.dsw.dadospessoais.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioSistema implements UserDetails {

    private String username;

    private String nomeCompleto;

    private String hashSenha;

    private List<Permissao> permissoes;

    public UsuarioSistema(String username, String nomeCompleto, String hashSenha, List<Permissao> permissoes) {
        this.username = username;
        this.nomeCompleto = nomeCompleto;
        this.hashSenha = hashSenha;
        this.permissoes = permissoes;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public void setHashSenha(String hashSenha) {
        this.hashSenha = hashSenha;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public String getPassword() {
        return hashSenha;
    }

    @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    public List<Permissao> getAuthorities() {
        return permissoes;
    }

}
