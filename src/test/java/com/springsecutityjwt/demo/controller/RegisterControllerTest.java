package com.springsecutityjwt.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecutityjwt.demo.dto.request.RegisterRequest;
import com.springsecutityjwt.demo.dto.response.RegisterResponse;
import com.springsecutityjwt.demo.exception.UsernameAlreadyUsedException;
import com.springsecutityjwt.demo.service.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService registerService;


    @Test
    public void register_NewUserWithAllFieldsFilled_ReturnsCreated() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("Teste", "teste");
        RegisterResponse registerResponse = new RegisterResponse(1L, registerRequest.getUsername());

        when(registerService.registerUser(registerRequest)).thenReturn(registerResponse);

        mockMvc.perform(post("/register")
            .content(mapper.writeValueAsString(registerRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(registerResponse.getId().toString()))
            .andExpect(jsonPath("$.username").value(registerResponse.getUsername()));
    }

    @Test
    public void register_NewUserWithBothFieldsEmpty_ReturnsCreated() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(null, null);

        mockMvc.perform(post("/register")
            .content(mapper.writeValueAsString(registerRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[*].field").value(containsInAnyOrder("username", "password")));
    }

    @Test
    public void register_NewUserWithExistingUsername_ReturnsCreated() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("Existing username", "teste");
        RegisterResponse registerResponse = new RegisterResponse(1L, registerRequest.getUsername());

        UsernameAlreadyUsedException exception = new UsernameAlreadyUsedException("Exception message");
        when(registerService.registerUser(registerRequest)).thenThrow(exception);

        mockMvc.perform(post("/register")
            .content(mapper.writeValueAsString(registerRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

}
