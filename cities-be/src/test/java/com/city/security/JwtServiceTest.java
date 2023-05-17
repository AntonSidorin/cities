package com.city.security;

import static com.city.security.JwtService.BEARER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.city.dao.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void generateTokenTest(){

        //given
        String username = "username";

        User user = new User();
        user.setUsername(username);

        //when
        String token = BEARER + jwtService.generateToken(new HashMap<>(), user);

        //then
        assertTrue(jwtService.isTokenValid(token, user));
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    void incorrectUserForTokenTest(){

        //given
        String username = "username";

        User user = new User();
        user.setUsername(username);

        //when
        String token = BEARER + jwtService.generateToken(new HashMap<>(), user);

        //then
        assertFalse(jwtService.isTokenValid(token, new User()));
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    void invalidTokenTest(){

        //given
        String username = "username";

        User user = new User();
        user.setUsername(username);

        //when
        String token = jwtService.generateToken(new HashMap<>(), user);

        //then
        assertFalse(jwtService.isTokenValid(token, user));

    }

}
