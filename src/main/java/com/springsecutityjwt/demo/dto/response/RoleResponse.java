package com.springsecutityjwt.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String description;
}
