package br.senac.tads.dsw.dadospessoais.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearer-jwt")
public class DadosController {

    @Autowired
    private UsuarioSistemaService usuarioService;
   
    @GetMapping("/me")
    public UsuarioSistemaDto obterDados(Authentication authn) {

        // JwtAuthenticationFilter já converteu o token JWT em um objeto UsuarioSistema
        // e o colocou no contexto de segurança
        UsuarioSistema usuario = (UsuarioSistema) authn.getPrincipal();

        // Carregando o UsuarioSistema do service somente para ilustrar o formato do HashSenha na tela
        // Em situações reais não é necessário fazer isso
        UsuarioSistemaDto dto = new UsuarioSistemaDto(usuarioService.loadUserByUsername(usuario.getUsername()));
        return dto;
    }

    @GetMapping("/peao")
    @PreAuthorize("hasAuthority('PEAO')")
    public MensagemPermissao obterDadosPeao(@AuthenticationPrincipal UsuarioSistema usuario) {
        return new MensagemPermissao(usuario.getNomeCompleto(), "Usuário com Permissão \"PEAO\"");
    }
    
    @GetMapping("/gerente")
    @PreAuthorize("hasAuthority('GERENTE')")
    public MensagemPermissao obterDadosGerente(@AuthenticationPrincipal UsuarioSistema usuario) {
        return new MensagemPermissao(usuario.getNomeCompleto(), "Usuário com Permissão \"GERENTE\"");
    }
    
    @GetMapping("/diretor")
    @PreAuthorize("hasAuthority('DIRETOR')")
    public MensagemPermissao obterDadosDiretor(@AuthenticationPrincipal UsuarioSistema usuario) {
        return new MensagemPermissao(usuario.getNomeCompleto(), "Usuário com Permissão \"DIRETOR\"");
    }

    public record UsuarioSistemaDto(String nomeCompleto, String username, String hashSenha) {

        public UsuarioSistemaDto(UsuarioSistema usuario) {
            this(usuario.getNomeCompleto(), usuario.getUsername(), usuario.getHashSenha());
        }
        
    }
 
    public record MensagemPermissao(String nome, String mensagem) {
        
    }
}