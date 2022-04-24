package com.springsecutityjwt.demo.service.register;

import com.springsecutityjwt.demo.dto.request.RegisterRequest;
import com.springsecutityjwt.demo.dto.response.RegisterResponse;
import com.springsecutityjwt.demo.exception.UsernameAlreadyUsedException;
import com.springsecutityjwt.demo.mapper.RoleMapper;
import com.springsecutityjwt.demo.mapper.UserMapper;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();

        // TODO: add unique constraint on username column
        if (userRepository.existsUserByUsername(username)) {
            throw new UsernameAlreadyUsedException(String.format("Usuário %s já existe no sistema", username));
        }

        User user = userRepository.save(userMapper.toUser(registerRequest));

        return new RegisterResponse(user.getId(), user.getUsername(), RoleMapper.toResponse(user.getRole()));
    }
}
