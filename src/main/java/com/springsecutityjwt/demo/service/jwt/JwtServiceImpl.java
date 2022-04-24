package com.springsecutityjwt.demo.service.jwt;

import com.springsecutityjwt.demo.config.security.ApplicationUser;
import com.springsecutityjwt.demo.config.security.JwtConfiguration;
import com.springsecutityjwt.demo.dto.response.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Override
    public JwtResponse generateToken(Authentication authUser) {
        ApplicationUser loggedUser = (ApplicationUser) authUser.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + jwtConfiguration.getExpiration());

        String token = Jwts.builder()
            .setIssuer("Spring Security Jwt")
            .setSubject(loggedUser.getId().toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(getSigningKeyForSecretKey())
            .compact();

        return new JwtResponse(token, jwtConfiguration.getTokenPrefix());
    }

    @Override
    public boolean isJwtValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKeyForSecretKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKeyForSecretKey()).build().parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    private SecretKey getSigningKeyForSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes());
    }
}
