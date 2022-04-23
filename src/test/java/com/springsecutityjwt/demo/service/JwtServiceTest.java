package com.springsecutityjwt.demo.service;

import com.springsecutityjwt.demo.config.security.JwtConfiguration;
import com.springsecutityjwt.demo.dto.response.JwtResponse;
import com.springsecutityjwt.demo.model.Role;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.service.jwt.JwtServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private JwtConfiguration jwtConfiguration;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateToken_WithAuthentication_ReturnsJwtResponse() {
        Authentication authentication = mock(Authentication.class);
        User user = new User(1L, "teste", "teste", new Role(1L, "test"));

        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtConfiguration.getSecretKey()).thenReturn("secretsecretsecretsecretsecretsecretsecretsecret");
        when(jwtConfiguration.getTokenPrefix()).thenReturn("prefix");

        JwtResponse jwtResponse = jwtService.generateToken(authentication);

        assertEquals(jwtResponse.getType(), jwtConfiguration.getTokenPrefix());
    }
}
