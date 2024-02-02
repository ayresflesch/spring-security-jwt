package com.springsecutityjwt.demo.config.security;

import com.springsecutityjwt.demo.model.User;
import com.springsecutityjwt.demo.repository.UserRepository;
import com.springsecutityjwt.demo.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final JwtConfiguration jwtConfiguration;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequestHeader(request);

        if (jwtService.isJwtValid(token)) {
            authenticateUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequestHeader(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token == null || token.isEmpty() || !token.startsWith(jwtConfiguration.getTokenPrefix())) {
            return null;
        }

        return token.replace(jwtConfiguration.getTokenPrefix(), "");
    }

    private void authenticateUser(String token) {
        Long userId = jwtService.getUserId(token);
        User user = userRepository.findById(userId).get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getGrantedAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
