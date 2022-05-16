package fun.tianlefirstweb.www.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.tianlefirstweb.www.security.LoginRequestDTO;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Called before reaching endpoint /login with POST method
 * Sign JWT token to user with valid username & password
 */
public class JwtUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtUsernamePasswordAuthFilter(AuthenticationManager authenticationManager,
                                         JwtConfig jwtConfig,
                                         SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestDTO loginRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestDTO.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException e){
            throw new IllegalArgumentException("invalid form of username and password");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        signToken(authResult.getName(),authResult.getAuthorities(),response);

        chain.doFilter(request, response);
    }

    public void signToken(String username, Collection<? extends GrantedAuthority> authorities, HttpServletResponse response){
        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
    }
}
