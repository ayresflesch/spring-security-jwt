package com.springsecutityjwt.demo.controller;

import com.springsecutityjwt.demo.dto.request.AuthenticationRequest;
import com.springsecutityjwt.demo.dto.response.JwtResponse;
import com.springsecutityjwt.demo.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        try {
            UsernamePasswordAuthenticationToken authUser = authenticationRequest.convert();
            Authentication authentication = authenticationManager.authenticate(authUser);
            JwtResponse jwtResponse = jwtService.generateToken(authentication);

            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
