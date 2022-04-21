package com.springsecutityjwt.demo.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String field;
    private final String message;
}
