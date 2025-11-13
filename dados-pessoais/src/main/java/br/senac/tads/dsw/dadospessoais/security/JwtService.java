package br.senac.tads.dsw.dadospessoais.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    private int intervaloExpiracao = 10; // 10 minutos

    // Gera um token JWT para o usuário usando JwtEncoder
    public String gerarJwt(UsuarioSistema usuario) {
        Instant now = Instant.now();
        Instant expiry = now.plus(intervaloExpiracao,
                ChronoUnit.MINUTES);
        // Extrai as roles do UserDetails
        List<String> permissoes = new ArrayList<>();
        for (Permissao p : usuario.getAuthorities()) {
            permissoes.add(p.getAuthority());
        }
        // Define as claims do token
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("pessoas-crud") // Emissor
                .issuedAt(now) // Data de emissão
                .expiresAt(expiry) // Data de expiração
                .subject(usuario.getUsername()) // Subject (username no nosso caso)
                .claim("name", usuario.getNomeCompleto())
                .claim("roles", permissoes) // Claim customizada com as roles
                .build();
        // Define o cabeçalho JWS - Usamos MacAlgorithm.HS256 que corresponde ao
        // HmacSHA256
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        // Codifica o token usando o JwtEncoder
        try {
            Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
            return jwt.getTokenValue();
        } catch (JwtEncodingException ex) {
            // log.error("Erro ao codificar token JWT: {}", e.getMessage(), ex);
            throw new RuntimeException("Erro ao gerar token JWT", ex);
        }
    }

    public UsuarioSistema obterUsuarioDoToken(String token) {
        try {
            Jwt jwt = decodificarToken(token);
            final String username = jwt.getSubject();
            List<String> permissoesToken = jwt.getClaimAsStringList("roles");
            List<Permissao> permissoes = new ArrayList<>();
            for (String p : permissoesToken) {
                permissoes.add(new Permissao(p));
            }
            // CRIAR OBJETO COM DADOS DO USUARIO LOGADO
            UsuarioSistema usuario = new UsuarioSistema(username, jwt.getClaimAsString("name"), null, permissoes);
            return usuario;
        } catch (JwtException ex) {
            // log.warn("Validação do JWT falhou: {}", ex.getMessage());
        }
        return null;
    }

    private Jwt decodificarToken(String token) throws JwtException {
        try {
            // O decoder já valida assinatura, expiração (com alguma tolerância talvez), e
            // claims padrão
            return this.jwtDecoder.decode(token);
        } catch (BadJwtException ex) {
            // log.warn("Token JWT mal formado ou inválido: {}", e.getMessage());
            throw ex; // Relança a exceção específica
        } catch (JwtException ex) {
            // log.error("Erro inesperado ao decodificar JWT: {}", e.getMessage(), e);
            throw ex; // Relança outras exceções JWT
        }
    }

}
