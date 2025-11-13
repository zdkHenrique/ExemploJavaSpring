package br.senac.tads.dsw.exemplos;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class GeradorSaidaJson implements GeradorSaida {
  
  @Override
  public String gerarSaida(Dados dados) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    return """
      {
        "nome": "%s",
        "email": "%s",
        "dataHoraAtual": "%s"
      }
      """.formatted(dados.nome(), dados.email(), formatter.format(dados.dataHoraAtual()));
  }
  
}