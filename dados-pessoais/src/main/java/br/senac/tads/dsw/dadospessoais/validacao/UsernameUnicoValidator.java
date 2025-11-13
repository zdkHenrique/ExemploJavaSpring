package br.senac.tads.dsw.dadospessoais.validacao;

import org.springframework.beans.factory.annotation.Autowired;

import br.senac.tads.dsw.dadospessoais.PessoaService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameUnicoValidator
    implements ConstraintValidator<UsernameUnico, String> {

    @Autowired
    private PessoaService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return (service.findByUsername(username) == null);
    } 

}
