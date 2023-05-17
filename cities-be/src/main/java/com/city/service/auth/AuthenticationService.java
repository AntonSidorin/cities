package com.city.service.auth;

import static com.city.dao.entity.Role.ALLOW_EDIT;

import com.city.controller.auth.AuthenticationRequest;
import com.city.dao.repository.UserRepository;
import com.city.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

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

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("firstname", user.getFirstname());
        extraClaims.put("lastname", user.getLastname());
        if(user.getRoles() != null){
            extraClaims.put("allowEdit", user.getRoles().contains(ALLOW_EDIT));
        }

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(extraClaims, user))
                .build();
    }

}
