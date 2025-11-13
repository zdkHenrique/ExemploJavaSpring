package br.senac.tads.dsw.exemplos;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ExemploMvcController {

    @GetMapping("/mvc")
    public String gerarHtml(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("time") String time,
            Model model) {

        Dados dados = new Dados(nome, email, time, "Sorocaba", LocalDateTime.now());
        model.addAttribute("dados", dados);
        return "tela-template";

    }

}
