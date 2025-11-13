package br.senac.tads.dsw.dadospessoais.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtConfig {

    // Chave simétrica para assinatura do JWT
    private static final String CHAVE_ASSINATURA_JWT = "cH@v353cr37@"; // NÃO DEIXAR NO CÓDIGO

    // Bean para a SecretKey - Chave simétrica usada tanto para codificar quanto
    // decodificar com HMAC
    @Bean
    public SecretKey jwtSecretKey() {
        try {
            byte[] decodedKey = MessageDigest.getInstance("SHA-256")
                    .digest(CHAVE_ASSINATURA_JWT.getBytes(StandardCharsets.UTF_8));
            // Garante que a chave tem tamanho suficiente para HS256 (32 bytes)
            if (decodedKey.length < 32) { // 256 bits
                // Poderia lançar uma exceção aqui para impedir a inicialização
                throw new IllegalArgumentException("Deve ter pelo menos 256 bits.");
            }
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Chave secreta JWT Base64 inválida.", ex);
        }
    }

    // Bean para o JWKSource (necessário pelo NimbusJwtEncoder)
    @Bean
    public JWKSource<SecurityContext> jwkSource(SecretKey secretKey) {
        // Cria um JWK do tipo OctetSequenceKey para HMAC
        JWK jwk = new OctetSequenceKey.Builder(secretKey).build();
        JWKSet jwkSet = new JWKSet(jwk);
        return new ImmutableJWKSet<>(jwkSet);
    }

    // Bean para o JwtEncoder (usa JWKSource)
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    // Bean para o JwtDecoder (usa a SecretKey diretamente para HMAC)
    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {
        // Configura o decoder para usar a chave secreta e o algoritmo HMAC SHA-256
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256) // Especifica o algoritmo esperado
                .build();
    }
}
