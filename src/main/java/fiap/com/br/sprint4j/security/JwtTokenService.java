package fiap.com.br.sprint4j.security;

import java.sql.Date;
import java.time.Instant;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fiap.com.br.sprint4j.domain.models.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.issuer}")
    private String issuer;

    @Value("${app.jwt.expiration-minutes}")
    private long expirationMinutes;

    private SecretKey key() { return Keys.hmacShaKeyFor(secret.getBytes()); }

    public String generateToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parser().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValid(String token) {
        try {
            parser().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private JwtParser parser() {
        return Jwts.parserBuilder().setSigningKey(key()).build();
    }
}
