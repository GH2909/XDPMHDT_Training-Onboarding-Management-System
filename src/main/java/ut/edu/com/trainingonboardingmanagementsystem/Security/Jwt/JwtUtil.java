package ut.edu.com.trainingonboardingmanagementsystem.Security.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import ut.edu.com.trainingonboardingmanagementsystem.Security.User.UserPrincipal;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "TOMS_SECRET_KEY_123456_ABCDEF_0987654321";
    private final long EXPIRATION = 1000 * 60 * 60;

    public String generateToken(UserPrincipal user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", user.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
