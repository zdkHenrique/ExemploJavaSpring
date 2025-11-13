package br.senac.tads.dsw.dadospessoais.security;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;
  
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String jwt = authHeader.substring(7);
    try {
      UsuarioSistema usuario = jwtService.obterUsuarioDoToken(jwt);
      if (usuario == null) {
        // log.warn("Token JWT inválido ou expirado detectado para o usuário '{}'.", userEmail);
        // Não lançar exceção aqui, apenas não autentica
        SecurityContextHolder.clearContext();
      }
      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          usuario,
          null,
          usuario.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    } catch (Exception ex) {
      // Não lançar exceção aqui, apenas não autentica
      // log.error("Erro ao processar token JWT no filtro: {}", ex.getMessage());
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request, response);
  }

}
