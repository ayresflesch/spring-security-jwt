package com.springsecutityjwt.demo.service.user;

import com.springsecutityjwt.demo.dto.response.UserResponse;
import com.springsecutityjwt.demo.mapper.UserMapper;
import com.springsecutityjwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository
            .findAll()
            .stream()
            .map(userMapper::toUserResponse)
            .collect(Collectors.toList());
    }
}