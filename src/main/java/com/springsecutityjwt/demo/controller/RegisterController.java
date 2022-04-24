package com.springsecutityjwt.demo.controller;

import com.springsecutityjwt.demo.dto.request.UserRequest;
import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest, UriComponentsBuilder uriBuilder) {
        UserResponse userResponse = userService.createUser(userRequest);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(userResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponse);
    }
}
