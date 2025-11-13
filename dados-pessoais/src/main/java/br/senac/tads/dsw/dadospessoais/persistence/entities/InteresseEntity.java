package br.senac.tads.dsw.dadospessoais.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "interesse")
public class InteresseEntity {

    @Id
    private Integer id;

    @Column(unique = true, nullable = false, length = 100)
    private String nome;

    // @ManyToMany(mappedBy = "interesses")
    // private Set<PessoaEntity> pessoas;

    public InteresseEntity() {
    }

    public InteresseEntity(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    
}
