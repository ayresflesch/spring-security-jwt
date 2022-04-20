package com.springsecutityjwt.demo.dto.response;

import lombok.Data;

@Data
public class JwtResponse {
    private final String token;
    private final String type;
}
