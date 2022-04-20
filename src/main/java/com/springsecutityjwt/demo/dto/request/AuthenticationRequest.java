package com.springsecutityjwt.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class AuthenticationRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
