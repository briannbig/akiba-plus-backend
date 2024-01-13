package com.github.briannbig.akiba.util;

import com.github.briannbig.akiba.config.JWTConfigProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * A utility class for handling jwt tokens
 *
 * @author Brian Barasa
 */

@Component
public class JWTUtil {

    Logger log = LoggerFactory.getLogger(getClass());
    private final String jwtSecret;

    private final int jwtExpirationMs;
    private final int jwtRefreshExpirationMs;

    @Autowired
    public JWTUtil(JWTConfigProperties configProperties) {

        int defaultJwtExpirationMs = 86400000; // 1 day
        int defaultJwtRefreshExpirationMs = 864000000; // 10 days

        jwtSecret = configProperties.jwtSecret();
        try {
            defaultJwtExpirationMs = Integer.parseInt(configProperties.jwtExpirationMs());
        } catch (NumberFormatException e) {
            log.warn("invalid jwt expiration: {}", e.getMessage());
        }
        try {
            defaultJwtRefreshExpirationMs = Integer.parseInt(configProperties.jwtRefreshExpirationMs());
        } catch (NumberFormatException e) {
            log.warn("invalid jwt refresh expiration: {}", e.getMessage());
        }

        jwtRefreshExpirationMs = defaultJwtRefreshExpirationMs;
        jwtExpirationMs = defaultJwtExpirationMs;

    }


    /**
     * creates a jwt for the given subject
     *
     * @param subject the subject to create an access token for. Usually the username
     * @param userId  claim to be put in id payload
     * @param roles   roles to be included in the payload
     * @return refresh jwt token which expires after the configured expiration time
     */
    public String generateJWT(String subject, String userId, Set<String> roles) {
        var claims = Jwts.claims().setSubject(subject);
        claims.put("ID", userId);
        claims.put("ROLES", roles);


        return Jwts.builder()
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    /**
     * creates a jwt for the given subject with a refresh expiration time
     *
     * @param username the subject to create a refresh token for
     * @return refresh jwt token which expires after the configured refresh expiration time
     */
    public String generateRefreshJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtRefreshExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * @param token jwt string
     * @return username from the token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature:--> {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token:--> {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired:--> {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported:--> {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty:--> {}", e.getMessage());
        }

        return false;
    }


}
