package com.springsecutityjwt.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecutityjwt.demo.dto.request.UserRequest;
import com.springsecutityjwt.demo.dto.response.RoleResponse;
import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.exception.UsernameAlreadyUsedException;
import com.springsecutityjwt.demo.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RegisterControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void register_NewUserWithAllFieldsFilled_ReturnsCreated() throws Exception {
        UserRequest userRequest = new UserRequest("Teste", "teste");
        UserResponse response = new UserResponse(1L, userRequest.getUsername(), new RoleResponse(1L, "description"));

        when(userService.createUser(userRequest)).thenReturn(response);

        mockMvc.perform(post("/register")
            .content(mapper.writeValueAsString(userRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(response.getId().toString()))
            .andExpect(jsonPath("$.username").value(response.getUsername()));
    }

    @Test
    public void register_NewUserWithBothFieldsEmpty_ReturnsCreated() throws Exception {
        UserRequest userRequest = new UserRequest(null, null);

        mockMvc.perform(post("/register")
            .content(mapper.writeValueAsString(userRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[*].field").value(containsInAnyOrder("username", "password")));
    }

    @Test
    public void register_NewUserWithExistingUsername_ReturnsCreated() throws Exception {
        UserRequest userRequest = new UserRequest("Existing username", "teste");

        UsernameAlreadyUsedException exception = new UsernameAlreadyUsedException("Exception message");
        when(userService.createUser(userRequest)).thenThrow(exception);

        mockMvc.perform(post("/register")
            .content(mapper.writeValueAsString(userRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

}

