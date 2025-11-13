package br.senac.tads.dsw.exemplos;

import java.time.LocalDateTime;


public record Dados(String nome, String email, String time,
    String cidade,
    LocalDateTime dataHoraAtual) {

}

/*
public class Dados {

    private final String nome;

    private final String email;

    private final LocalDateTime dataHoraAtual;

    public Dados(String nome, String email, LocalDateTime dataHoraAtual) {
        this.nome = nome;
        this.email = email;
        this.dataHoraAtual = dataHoraAtual;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataHoraAtual() {
        return dataHoraAtual;
    }

}
*/