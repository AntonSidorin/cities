package com.city.controller.city;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.city.controller.AbstractControllerTest;
import com.city.dto.CitiesDto;
import com.city.dto.CityDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest extends AbstractControllerTest {

    @Value("${cities.row.per.page}")
    private int rowPerPage;

    @Test
    void unauthorisedAccessToCitiesApi() throws Exception {
        mockMvc.perform(get("/api/v1/cities/{cityId}", "cityId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void getCity() throws Exception {

        //register user
        Long cityId = 1L;
        String token = getToken("user", "user");

        //get cities
        CityDto city = mapper.readValue(
                mockMvc.perform(get("/api/v1/cities/{cityId}", cityId)
                                .header(HttpHeaders.AUTHORIZATION, token)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {
                });

        assertEquals(cityId, city.id());
        assertEquals("Tokyo", city.name());
        assertNotNull(city.photo());
    }

    @Test
    void getCities() throws Exception {

        //register user
        String token = getToken("user", "user");

        //get cities
        CitiesDto cities = mapper.readValue(
                mockMvc.perform(get("/api/v1/cities")
                                .header(HttpHeaders.AUTHORIZATION, token)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {
                });

        assertEquals(rowPerPage, cities.cities().size());
    }

    @Test
    void getCityByName() throws Exception {

        String cityName = "London";
        String token = getToken("user", "user");

        //get cities
        CitiesDto cities = mapper.readValue(
                mockMvc.perform(get("/api/v1/cities")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .param("name", cityName)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {
                });

        assertEquals(1, cities.totalPages());
        assertEquals(cityName, cities.cities().get(0).name());
    }

    @Test
    void getCitiesByName() throws Exception {

        String cityName = "Mo";
        String token = getToken("user", "user");

        //get cities
        CitiesDto cities = mapper.readValue(
                mockMvc.perform(get("/api/v1/cities")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .param("name", cityName)
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {
                });

        assertFalse(cities.cities().stream().anyMatch(city -> !StringUtils.containsIgnoreCase(city.name(), cityName)));
    }

    @Test
    void updateCityByUser() throws Exception {

        Long cityId = 1L;
        String token = getToken("user", "user");

        CityDto city = new CityDto(cityId, "Test city name", "Test city photo");

        //update city
        mockMvc.perform(patch("/api/v1/cities/{cityId}", cityId)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(city))
                )
                .andExpect(status().isForbidden());

    }

    @Test
    void updateCityByAllowEditUser() throws Exception {

        Long cityId = 2L;
        String token = getToken("admin", "admin");

        CityDto city = new CityDto(cityId, "Test city name", "Test city photo");

        //update city
        CityDto result = mapper.readValue(
                mockMvc.perform(patch("/api/v1/cities/{cityId}", cityId)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsBytes(city))
                        )
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {
                });

        assertEquals(city.id(), result.id());
        assertEquals(city.name(), result.name());
        assertEquals(city.photo(), result.photo());
    }

}
