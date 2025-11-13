package br.senac.tads.dsw.dadospessoais.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // <=== Habilita uso do @PreAuthorize e @PostAutorize
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    // Delegating Password Encoder – suporte a múltiplos algoritmos de hash
    @Bean
    PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        BCryptPasswordEncoder bcryptEnc = new BCryptPasswordEncoder();
        encoders.put("bcrypt", bcryptEnc); // {bcrypt}HASH
        encoders.put("noop", NoOpPasswordEncoder.getInstance()); // {noop}SENHA
        var passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(bcryptEnc);
        return passwordEncoder;
    }

    // Bean authenticationManager - Usado para iniciar o fluxo de autenticação
    @Bean
    AuthenticationManager authenticationManager(
            UsuarioSistemaService usuarioService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(usuarioService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    // filterChain - Configuração geral dos parâmetros do Spring Security
    // que serão ativados e pode-se configurar as autorizações de acesso
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(fo -> fo.sameOrigin())) // Permite mostrar H2 Console
                .formLogin(formLogin -> formLogin.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/login.html", "/me.html",
                                "/h2-console/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()
                        // .requestMatchers("/peao").hasAuthority("PEAO")
                        // .requestMatchers("/gerente").hasAuthority("GERENTE")
                        // .requestMatchers("/diretor").hasAuthority("DIRETOR")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
