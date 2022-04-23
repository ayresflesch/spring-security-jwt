package com.springsecutityjwt.demo.service;

import com.springsecutityjwt.demo.dto.request.RegisterRequest;
import com.springsecutityjwt.demo.dto.response.RegisterResponse;
import com.springsecutityjwt.demo.exception.UsernameAlreadyUsedException;
import com.springsecutityjwt.demo.mapper.UserMapper;
import com.springsecutityjwt.demo.model.Role;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.UserRepository;
import com.springsecutityjwt.demo.service.register.RegisterServiceImpl;
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
public class RegisterServiceTest {

    @InjectMocks
    private RegisterServiceImpl registerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUser_ValidUser_ReturnsRegisterResponse() {
        RegisterRequest registerRequest = new RegisterRequest("teste", "teste");
        User user = new User(1L, "username", "password", new Role(1L, "teste"));

        when(userRepository.existsUserByUsername(registerRequest.getUsername())).thenReturn(false);
        when(userMapper.toUser(registerRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        RegisterResponse registerResponse = registerService.registerUser(registerRequest);

        assertEquals(registerResponse.getId(), user.getId());
        assertEquals(registerResponse.getUsername(), user.getUsername());
        assertEquals(registerResponse.getRole().getId(), user.getRole().getId());
        assertEquals(registerResponse.getRole().getDescription(), user.getRole().getDescription());
    }

    @Test(expected = UsernameAlreadyUsedException.class)
    public void registerUser_ExistingUsername_ThrowsException() {
        RegisterRequest registerRequest = new RegisterRequest("existingUsername", "teste");

        when(userRepository.existsUserByUsername(registerRequest.getUsername())).thenThrow(new UsernameAlreadyUsedException("message"));

        registerService.registerUser(registerRequest);
    }
}
