package com.springsecutityjwt.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRoleRequest extends UserRequest {
    @NotNull
    private Long roleId;

    public UserWithRoleRequest(@NotNull String username, @NotNull String password) {
        super(username, password);
    }

    public UserWithRoleRequest(@NotNull String username, @NotNull String password, @NotNull Long roleId) {
        super(username, password);
        this.roleId = roleId;
    }
}
