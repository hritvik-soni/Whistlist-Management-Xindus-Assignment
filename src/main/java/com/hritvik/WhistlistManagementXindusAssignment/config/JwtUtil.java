package com.hritvik.WhistlistManagementXindusAssignment.config;

import com.hritvik.WhistlistManagementXindusAssignment.dto.ApiResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String extractUsername(String token) {
        System.out.println("extracting username");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("extracting claim");
        final Claims claims = (Claims) extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Object extractAllClaims(String token) {
        System.out.println("extracting all claims");

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        System.out.println("inside validate token");
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


//
//      return Jwts
//              .parserBuilder()
//              .setSigningKey(getSignKey())
//            .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//} catch (MalformedJwtException ex) {
//        System.out.println("Invalid JWT token");
//            throw new RuntimeException("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//        System.out.println("Expired JWT token");
//            throw new RuntimeException("Expired JWT token");
//
//        } catch (UnsupportedJwtException ex) {
//        System.out.println("Unsupported JWT token");
//            throw new RuntimeException("Unsupported JWT token");
//
//        } catch (IllegalArgumentException ex) {
//        System.out.println("JWT claims string is empty");
//        } catch (SignatureException ex) {
//        System.out.println("Invalid JWT signature");
//            throw new RuntimeException("Invalid JWT signature");
//        }
//                return claims;
}
