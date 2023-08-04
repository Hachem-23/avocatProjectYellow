package com.avocat.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  private static final String SECRET_KEY = "4707b51e631c98b0c42e4d710c6b1946cd113b44a0e1feb5262cb4eff3cda573";


  public String extractUsername(String token) {

    return extractClaim(token, Claims::getSubject) ;
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims =extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  public String generateToken(UserDetails userDetails){
    return generateToken(new HashMap<>(), userDetails);

  }


  public String generateToken(
          Map<String, Object> extraClaims,
          UserDetails userDetails
  ){
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();

  }
  public boolean isTokenValide(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenexpired(token);

  }

  private boolean isTokenexpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Claims extractAllClaims(String token){
    return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();

  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
