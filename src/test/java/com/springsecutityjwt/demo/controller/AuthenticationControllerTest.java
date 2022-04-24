package com.springsecutityjwt.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecutityjwt.demo.dto.request.AuthenticationRequest;
import com.springsecutityjwt.demo.dto.response.JwtResponse;
import com.springsecutityjwt.demo.service.authentication.AuthenticationServiceImpl;
import com.springsecutityjwt.demo.service.jwt.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthenticationControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @Test
    public void authenticate_ExistingUser_ReturnsOk() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("user", "password");
        UsernamePasswordAuthenticationToken authUser = authRequest.convert();
        JwtResponse jwtResponse = new JwtResponse("token", "type");

        when(authenticationManager.authenticate(authUser)).thenReturn(authUser);
        when(jwtService.generateToken(any(Authentication.class))).thenReturn(jwtResponse);

        mockMvc.perform(post("/auth")
            .content(mapper.writeValueAsString(authRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(jwtResponse.getToken()))
            .andExpect(jsonPath("$.type").value(jwtResponse.getType()));
    }

    @Test
    public void authenticate_NonExistingUser_ReturnsBadRequest() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("user", "password");
        UsernamePasswordAuthenticationToken authUser = authRequest.convert();
        JwtResponse jwtResponse = new JwtResponse("token", "type");

        when(authenticationManager.authenticate(authUser)).thenThrow(new BadCredentialsException("error"));

        mockMvc.perform(post("/auth")
            .content(mapper.writeValueAsString(authRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}
