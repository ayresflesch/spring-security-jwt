package com.springsecutityjwt.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserWithRoleRequest extends UserRequest {
    @NotNull
    private Long roleId;

    public UserWithRoleRequest(@NotNull String username, @NotNull String password) {
        super(username, password);
    }
}
