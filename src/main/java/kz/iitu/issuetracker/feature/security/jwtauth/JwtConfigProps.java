package kz.iitu.issuetracker.feature.security.jwtauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
@Data
public class JwtConfigProps {
    private final String secret;
    private final Long expirationMs;
}
