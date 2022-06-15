package fun.tianlefirstweb.www.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@Data
public class JwtConfig {

    private String secretKey;
    private String authorizationHeader;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public void signToken(String username, Collection<? extends GrantedAuthority> authorities, HttpServletResponse response){
        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(tokenExpirationAfterDays)))
                .signWith(secretKey())
                .compact();
        response.addHeader(authorizationHeader, tokenPrefix + token);
    }

    public Authentication verifyToken(String token) {
        token = token.replace(tokenPrefix, "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey())
                .build()
                .parseClaimsJws(token);

        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
        //TODO: check token excess time
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities
                .stream()
                .map(a -> new SimpleGrantedAuthority(a.get("authority")))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                simpleGrantedAuthorities
        );
    }
}
