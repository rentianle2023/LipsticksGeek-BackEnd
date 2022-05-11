package fun.tianlefirstweb.www.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

public class JwtUtil {

    public static String sign(String subject, Collection<? extends GrantedAuthority> authentications, Integer tokenExpirationAfterDays, SecretKey secretKey){
        return Jwts.builder()
                .setSubject(subject)
                .claim("authorities", authentications)
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(tokenExpirationAfterDays)))
                .signWith(secretKey)
                .compact();
    }

    public static Jws<Claims> parse(String token, SecretKey secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
