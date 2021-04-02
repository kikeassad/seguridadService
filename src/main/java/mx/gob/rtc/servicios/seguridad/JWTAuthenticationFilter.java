package mx.gob.rtc.servicios.seguridad;

import mx.gob.rtc.servicios.seguridad.SecurityConstants;
import mx.gob.rtc.servicios.seguridad.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
						   user.getNombre(),
						   user.getContrasena()
					));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User)authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(auth->{
            roles.add(auth.getAuthority());
        });
        String jwt = JWT.create()
                .withIssuer(request.getRequestURI())
                .withSubject(user.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstants.JWT_EXPIRATION))
                .sign(Algorithm.HMAC256(SecurityConstants.JWT_SECRET));
        response.addHeader(SecurityConstants.JWT_HEADER_NAME,SecurityConstants.JWT_HEADER_PREFIX+jwt);
    }

}
