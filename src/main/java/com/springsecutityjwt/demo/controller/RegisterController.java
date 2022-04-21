package com.springsecutityjwt.demo.controller;

import com.springsecutityjwt.demo.dto.request.RegisterRequest;
import com.springsecutityjwt.demo.dto.response.RegisterResponse;
import com.springsecutityjwt.demo.service.register.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest registerRequest, UriComponentsBuilder uriBuilder) {
        RegisterResponse registerResponse = registerService.registerUser(registerRequest);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(registerResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(registerResponse);
    }
}
