package com.springsecutityjwt.demo.service.authentication;

import com.springsecutityjwt.demo.config.security.ApplicationUser;
import com.springsecutityjwt.demo.model.User;
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
        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(format("User %s not found", username)));

        return new ApplicationUser(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getGrantedAuthorities()
        );
    }
}
