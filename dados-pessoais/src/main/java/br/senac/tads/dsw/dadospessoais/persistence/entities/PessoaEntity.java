package br.senac.tads.dsw.dadospessoais.persistence.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "pessoa")
public class PessoaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 100)
    private String nome;

    private LocalDate dataNascimento;

    @Column(nullable = false, length = 100)
    private String email;
    
    @Column(length = 20)
    private String telefone;

    private String senha;

    @OneToMany(mappedBy = "pessoa")
    private Set<FotoPessoaEntity> fotos;

    @ManyToMany
    @JoinTable(name = "pessoa_interesse",
        joinColumns = @JoinColumn(name = "pessoa_id"),
        inverseJoinColumns = @JoinColumn(name = "interesse_id"))
    private Set<InteresseEntity> interesses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<FotoPessoaEntity> getFotos() {
        return fotos;
    }

    public void setFotos(Set<FotoPessoaEntity> fotos) {
        this.fotos = fotos;
    }

    public Set<InteresseEntity> getInteresses() {
        return interesses;
    }

    public void setInteresses(Set<InteresseEntity> interesses) {
        this.interesses = interesses;
    }

    

}
