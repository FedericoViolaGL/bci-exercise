package org.globallogic.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.globallogic.exception.TokenNotValidException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    private final Environment environment;

    public String createJwt(String email) {
        String key = environment.getProperty("jwt-key");
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
    }

    public Jws<Claims> parseToken(String token, String email) {
        String key = environment.getProperty("jwt-key");

        Jws<Claims> jwsClaims;
        try {
            jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                    .requireSubject(email) //valido que este token se haya creado para un email especifico
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException exception) {
            throw new TokenNotValidException();
        }

        return jwsClaims;
    }
}
