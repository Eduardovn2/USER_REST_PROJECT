package Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Filtro para validação de autorização JWT.
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;;

    public JwtAuthFilter(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    /*Responsavel por inteceptar cada requisição http,e verificar se ela tem um token valido para autorizar o user.
    * Esta é a parte crucial.
    * Se o token for válido, o filtro autentica o usuário e define o SecurityContextHolder.
    * A partir deste ponto, o Spring Security considera o usuário logado e aplica as regras de autorização
    * (ex: se ele pode acessar /admin ou não).*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
    if(authHeader == null || !authHeader.startsWith("Bearer")){
        filterChain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7);
    String username = JwtUtil.extractUsername(token);

    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(JwtUtil.validateToken(token)){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }filterChain.doFilter(request, response);
    }
    }
}
