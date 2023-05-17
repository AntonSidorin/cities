package com.city.controller.exception;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.city.controller.AbstractControllerTest;
import com.city.dto.CityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerExceptionHandlerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void updateNotExistingCityByAllowEditUser() throws Exception {

        Long cityId = -1L;
        String token = getToken("admin", "admin");

        CityDto city = new CityDto(cityId, "Test city name", "Test city photo");

        //update city
        mockMvc.perform(patch("/api/v1/cities/{cityId}", cityId)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(city))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]", equalTo("City has not been found.")));

    }

    @Test
    void updateCityWithInvalidNameInputByAllowEditUser() throws Exception {

        Long cityId = -1L;
        String token = getToken("admin", "admin");

        CityDto city = new CityDto(cityId, RandomStringUtils.random(500), "Test city photo");

        //update city
        mockMvc.perform(patch("/api/v1/cities/{cityId}", cityId)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(city))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]", equalTo("size must be between 0 and 200")));

    }

    @Test
    void updateCityWithInvalidPhotoInputByAllowEditUser() throws Exception {

        Long cityId = -1L;
        String token = getToken("admin", "admin");

        CityDto city = new CityDto(cityId, "Test city name", RandomStringUtils.random(1500));

        //update city
        mockMvc.perform(patch("/api/v1/cities/{cityId}", cityId)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(city))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]", equalTo("size must be between 0 and 500")));

    }
}
