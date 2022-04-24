package com.springsecutityjwt.demo.mapper;

import com.springsecutityjwt.demo.dto.request.RegisterRequest;
import com.springsecutityjwt.demo.dto.response.UserResponse;
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

    public User toUser(RegisterRequest registerRequest) {
        return User.builder()
            .username(registerRequest.getUsername())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .role(roleRepository.findById(defaultRoleId).get())
            .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .role(RoleMapper.toResponse(user.getRole()))
            .build();
    }
}
