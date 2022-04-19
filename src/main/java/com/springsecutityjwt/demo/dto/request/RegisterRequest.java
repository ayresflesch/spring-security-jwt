package com.springsecutityjwt.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegisterRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
