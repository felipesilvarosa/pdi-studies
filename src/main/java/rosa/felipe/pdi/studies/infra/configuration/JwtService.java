package rosa.felipe.pdi.studies.infra.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rosa.felipe.pdi.studies.infra.exception.UnauthorizedException;

import java.security.Provider;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

@Service
public class JwtService {

    private String secret;
    private Algorithm algorithm;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @PostConstruct
    public void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(UserDetails userDetails) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMinutes(30);
        return JWT.create()
                .withIssuedAt(Instant.from(now))
                .withExpiresAt(Instant.from(expiration))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String extractUserName(String jwt) {
        return extractClaim(jwt, "sub", String.class);
    }

    public <T> T extractClaim(String jwt, String claimName, Class<T> type) {
        Map<String, Claim> claims = extractClaims(jwt);
        Claim claim = claims.get(claimName);
        return claim.as(type);
    }

    public DecodedJWT getToken(String jwt) {
        DecodedJWT decodedJWT = getDecodedJWT(jwt);
        if (this.isTokenExpired(jwt)) {
            throw new UnauthorizedException("Token is expired");
        }
        return decodedJWT;
    }

    private DecodedJWT getDecodedJWT(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(jwt);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedException();
        }
    }

    public boolean isTokenExpired(String jwt) {
        return extractClaim(jwt, "exp", LocalDateTime.class).isBefore(LocalDateTime.now());
    }

    private Map<String, Claim> extractClaims(String jwt) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(jwt);
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return decodedJWT.getClaims();
    }

}
