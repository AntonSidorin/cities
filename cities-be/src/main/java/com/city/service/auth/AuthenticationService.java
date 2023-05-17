package com.city.service.auth;

import com.city.controller.auth.AuthenticationRequest;
import com.city.dao.repository.UserRepository;
import com.city.security.JwtService;
import com.city.service.mapper.user.UserToUserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserToUserDtoMapper userToUserDtoMapper;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        //User is authenticated
        var user = repository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));

        return AuthenticationResponse.builder()
                .user(userToUserDtoMapper.apply(user))
                .token(jwtService.generateToken(user))
                .build();
    }

}
