package com.city.service.auth;

import static com.city.dao.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.city.controller.auth.AuthenticationRequest;
import com.city.dao.entity.User;
import com.city.dao.repository.UserRepository;
import com.city.security.JwtService;
import com.city.service.mapper.user.UserToUserDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private JwtService jwtService;

    @Spy
    private AuthenticationManager authenticationManager;

    @Spy
    private UserToUserDtoMapper userToUserDtoMapper = new UserToUserDtoMapper();

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void authenticate() {
        //given
        String username = "username";
        String password = "password";
        String firstname = "firstname";
        String lastname = "lastname";

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setRoles(Set.of(USER));

        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        String token = "token";
        when(jwtService.generateToken(Mockito.any(UserDetails.class))).thenReturn(token);

        //when
        AuthenticationResponse response = authenticationService
                .authenticate(new AuthenticationRequest(username, password));

        //then
        assertEquals(token, response.getToken());
        Assertions.assertEquals(firstname, response.getUser().firstname());
        Assertions.assertEquals(lastname, response.getUser().lastname());
    }

}