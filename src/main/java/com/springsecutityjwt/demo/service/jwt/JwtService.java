package com.springsecutityjwt.demo.service.jwt;

import com.springsecutityjwt.demo.dto.response.JwtResponse;
import org.springframework.security.core.Authentication;

public interface JwtService {
    JwtResponse generateToken(Authentication authUser);
}
