package com.springsecutityjwt.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecutityjwt.demo.dto.request.UserWithRoleRequest;
import com.springsecutityjwt.demo.dto.response.RoleResponse;
import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "user", roles = {"admin", "member"})
    public void getUsers_WithAllowedUser_ReturnsUsersList() throws Exception {
        UserResponse user1 = new UserResponse(1L, "user 1", new RoleResponse(1L, "role 1"));
        UserResponse user2 = new UserResponse(2L, "user 2", new RoleResponse(2L, "role 2"));

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].id").value(containsInAnyOrder(user1.getId().intValue(), user2.getId().intValue())))
            .andExpect(jsonPath("$[*].username").value(containsInAnyOrder(user1.getUsername(), user2.getUsername())))
            .andExpect(jsonPath("$[*].role.id").value(containsInAnyOrder(user1.getRole().getId().intValue(), user2.getRole().getId().intValue())))
            .andExpect(jsonPath("$[*].role.description").value(containsInAnyOrder(user1.getRole().getDescription(), user2.getRole().getDescription())));
    }

    @Test
    @WithMockUser(username = "user", roles = {"admin"})
    public void createUser_WithAdminUser_CreatesUser() throws Exception {
        UserWithRoleRequest userWithRoleRequest = new UserWithRoleRequest("username", "password", 1L);
        UserResponse userResponse = new UserResponse(1L, "user 1", new RoleResponse(1L, "role 1"));

        when(userService.createUser(userWithRoleRequest)).thenReturn(userResponse);

        mockMvc.perform(post("/users")
            .content(mapper.writeValueAsString(userWithRoleRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = {"member"})
    public void createUser_WithMemberUser_ReturnsBadRequest() throws Exception {
        UserWithRoleRequest userWithRoleRequest = new UserWithRoleRequest("username", "password", 1L);
        UserResponse userResponse = new UserResponse(1L, "user 1", new RoleResponse(1L, "role 1"));

        when(userService.createUser(userWithRoleRequest)).thenReturn(userResponse);

        mockMvc.perform(post("/users")
            .content(mapper.writeValueAsString(userWithRoleRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }



}
