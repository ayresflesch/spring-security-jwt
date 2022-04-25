package com.springsecutityjwt.demo.mapper;


import com.springsecutityjwt.demo.dto.response.RoleResponse;
import com.springsecutityjwt.demo.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class RoleMapperTest {
    @Test
    public void toResponse_ValidRole_ReturnsRoleResponse() {
        Role role = new Role(1L, "test");
        RoleResponse roleResponse = RoleMapper.toResponse(role);

        assertEquals(roleResponse.getId(), role.getId());
        assertEquals(roleResponse.getDescription(), role.getDescription());
    }
}
