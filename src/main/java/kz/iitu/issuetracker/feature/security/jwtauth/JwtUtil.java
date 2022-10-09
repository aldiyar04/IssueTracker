package kz.iitu.issuetracker.feature.security.jwtauth;

import io.jsonwebtoken.*;
import kz.iitu.issuetracker.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
@AllArgsConstructor
public class JwtUtil {
    private final JwtConfigProps jwtProps;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(nowPlusMilliseconds(jwtProps.getExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, jwtProps.getSecret())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProps.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProps.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Date nowPlusMilliseconds(long ms) {
        long dateNowMs = new Date().getTime();
        return new Date(dateNowMs + ms);
    }
}
