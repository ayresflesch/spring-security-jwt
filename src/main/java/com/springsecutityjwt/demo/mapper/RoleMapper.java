package com.springsecutityjwt.demo.mapper;

import com.springsecutityjwt.demo.dto.response.RoleResponse;
import com.springsecutityjwt.demo.model.Role;

public class RoleMapper {
    public static RoleResponse toResponse(Role role) {
        return new RoleResponse(role.getId(), role.getDescription());
    }
}
