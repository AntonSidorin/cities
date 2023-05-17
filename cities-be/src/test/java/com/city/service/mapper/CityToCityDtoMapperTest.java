package com.city.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.city.dao.entity.City;
import com.city.dto.CityDto;
import com.city.service.mapper.city.CityToCityDtoMapper;
import org.junit.jupiter.api.Test;

class CityToCityDtoMapperTest {

    @Test
    void walletMappingTest() {

        //given
        City city = new City();
        city.setId(1L);
        city.setName("Test city name");
        city.setPhoto("Test city url");

        //when
        CityDto dto = new CityToCityDtoMapper().apply(city);

        //then
        assertEquals(dto.id(), city.getId());
        assertEquals(dto.name(), city.getName());
        assertEquals(dto.photo(), city.getPhoto());

    }

}
