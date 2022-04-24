package com.springsecutityjwt.demo.service;

import com.springsecutityjwt.demo.dto.request.UserRequest;
import com.springsecutityjwt.demo.dto.response.RoleResponse;
import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.exception.UsernameAlreadyUsedException;
import com.springsecutityjwt.demo.mapper.UserMapper;
import com.springsecutityjwt.demo.model.Role;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.UserRepository;
import com.springsecutityjwt.demo.service.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_ValidUser_ReturnsUserResponse() {
        UserRequest userRequest = new UserRequest("teste", "teste");

        Role role = new Role(1L, "teste");
        User user = new User(1L, userRequest.getUsername(), "password", role);

        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), new RoleResponse(role.getId(), role.getDescription()));

        when(userRepository.existsUserByUsername(userRequest.getUsername())).thenReturn(false);
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse response = userService.createUser(userRequest);

        assertEquals(response.getId(), user.getId());
        assertEquals(response.getUsername(), user.getUsername());
        assertEquals(response.getRole().getId(), user.getRole().getId());
        assertEquals(response.getRole().getDescription(), user.getRole().getDescription());
    }

    @Test(expected = UsernameAlreadyUsedException.class)
    public void createUser_ExistingUsername_ThrowsException() {
        UserRequest userRequest = new UserRequest("existingUsername", "teste");

        when(userRepository.existsUserByUsername(userRequest.getUsername())).thenThrow(new UsernameAlreadyUsedException("message"));

        userService.createUser(userRequest);
    }
}
