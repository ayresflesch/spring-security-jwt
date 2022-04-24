package com.springsecutityjwt.demo.service;

import com.springsecutityjwt.demo.model.Role;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.UserRepository;
import com.springsecutityjwt.demo.service.authentication.AuthenticationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsername_ExistingUser_ReturnsUser() {
        String username = "username";
        User user = new User(1L, username, "password", new Role(1L, "teste"));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = authenticationService.loadUserByUsername(username);

        assertEquals(userDetails.getUsername(), user.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_NonExistingUser_ThrowsException() {
        String username = "username";

        when(userRepository.findByUsername(username)).thenThrow(new UsernameNotFoundException("message"));

        authenticationService.loadUserByUsername(username);
    }
}
