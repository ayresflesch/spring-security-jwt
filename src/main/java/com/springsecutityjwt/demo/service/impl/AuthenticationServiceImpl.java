package com.springsecutityjwt.demo.service.impl;

import com.springsecutityjwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class AuthenticationServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(format("User %s not found", username)));
    }
}
