package com.springsecutityjwt.demo.mapper;

import com.springsecutityjwt.demo.dto.request.UserRequest;
import com.springsecutityjwt.demo.dto.request.UserWithRoleRequest;
import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.model.Role;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void toUser_ValidUserRequest_ReturnsUser() {
        Role defaultRole = new Role(2L, "default");

        when(roleRepository.findById(defaultRole.getId())).thenReturn(Optional.of(defaultRole));

        UserRequest userRequest = new UserRequest("username", "password");
        User user = userMapper.toUser(userRequest);

        assertEquals(user.getUsername(), userRequest.getUsername());
        assertEquals(user.getRole().getId(), defaultRole.getId());
        assertEquals(user.getRole().getDescription(), defaultRole.getDescription());
        assertNotEquals(user.getPassword(), userRequest.getPassword());
    }

    @Test
    public void toUser_ValidUserWithRoleRequest_ReturnsUser() {
        Role role = new Role(3L, "default");

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        UserWithRoleRequest userRequest = new UserWithRoleRequest("username", "password", role.getId());
        User user = userMapper.toUser(userRequest);

        assertEquals(user.getUsername(), userRequest.getUsername());
        assertEquals(user.getRole().getId(), role.getId());
        assertEquals(user.getRole().getDescription(), role.getDescription());
        assertNotEquals(user.getPassword(), userRequest.getPassword());
    }

    @Test
    public void toUserResponse_ValidUser_ReturnsUserResponse() {
        Role role = new Role(1L, "test");
        User user = new User("test", "test", role);

        UserResponse userResponse = userMapper.toUserResponse(user);

        assertEquals(userResponse.getId(), user.getId());
        assertEquals(userResponse.getUsername(), user.getUsername());
        assertEquals(userResponse.getRole().getId(), user.getRole().getId());
        assertEquals(userResponse.getRole().getDescription(), user.getRole().getDescription());
    }

}
