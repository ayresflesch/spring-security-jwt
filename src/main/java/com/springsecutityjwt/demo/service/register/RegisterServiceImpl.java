package com.springsecutityjwt.demo.service.register;

import com.springsecutityjwt.demo.dto.request.RegisterRequest;
import com.springsecutityjwt.demo.dto.response.RegisterResponse;
import com.springsecutityjwt.demo.exception.UsernameAlreadyUsedException;
import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();

        if (userRepository.existsUserByUsername(username)) {
            throw new UsernameAlreadyUsedException(String.format("Usuário %s já existe no sistema", username));
        }

        User user = userRepository.save(new User(
            username,
            passwordEncoder.encode(registerRequest.getPassword())
        ));

        return new RegisterResponse(user.getId(), user.getUsername());
    }
}
