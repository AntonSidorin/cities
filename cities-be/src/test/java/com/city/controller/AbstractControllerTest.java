package com.city.controller;

import static com.city.security.JwtService.BEARER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.city.controller.auth.AuthenticationRequest;
import com.city.service.auth.AuthenticationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    protected String getToken(String username, String password) throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        AuthenticationResponse authenticationResponse = mapper.readValue(
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                                .content(new ObjectMapper().writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), AuthenticationResponse.class);

        return BEARER + " " + authenticationResponse.getToken();
    }

}
