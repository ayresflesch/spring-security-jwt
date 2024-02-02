package com.springsecutityjwt.demo.controller;

import com.springsecutityjwt.demo.dto.request.UserWithRoleRequest;
import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('admin', 'member')")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserWithRoleRequest userWithRoleRequest, UriComponentsBuilder uriBuilder) {
        UserResponse userResponse = userService.createUser(userWithRoleRequest);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(userResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponse);
    }
}
