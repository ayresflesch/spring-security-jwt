package com.springsecutityjwt.demo.service.jwt;

import com.springsecutityjwt.demo.config.security.JwtConfiguration;
import com.springsecutityjwt.demo.dto.response.JwtResponse;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.service.jwt.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Override
    public JwtResponse generateToken(Authentication authUser) {
        User loggedUser = (User) authUser.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + jwtConfiguration.getExpiration());

        String token = Jwts.builder()
            .setIssuer("Spring Security Jwt")
            .setSubject(loggedUser.getId().toString())
            .setIssuedAt(today)
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes()))
            .compact();

        return new JwtResponse(token, jwtConfiguration.getTokenPrefix());
    }
}
