package com.springsecutityjwt.demo.mapper;

import com.springsecutityjwt.demo.dto.request.UserRequest;
import com.springsecutityjwt.demo.dto.request.UserWithRoleRequest;
import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.exception.ResourceNotFoundException;
import com.springsecutityjwt.demo.model.Role;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private static final Long defaultRoleId = 2L;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public User toUser(UserRequest userRequest) {
        return User.builder()
            .username(userRequest.getUsername())
            .password(passwordEncoder.encode(userRequest.getPassword()))
            .role(getUserRequestRole(userRequest))
            .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(RoleMapper.toResponse(user.getRole()))
            .build();
    }

    private Role getUserRequestRole(UserRequest userRequest) {
        if(userRequest instanceof UserWithRoleRequest) {
            Long roleId = ((UserWithRoleRequest) userRequest).getRoleId();

            return roleRepository
                .findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with id %d not found", roleId)));
        } else {
            return roleRepository.findById(defaultRoleId).get();
        }
    }
}
