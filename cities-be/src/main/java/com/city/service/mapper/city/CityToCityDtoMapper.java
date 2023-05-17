package com.city.service.mapper.city;

import com.city.dao.entity.City;
import com.city.dto.CityDto;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CityToCityDtoMapper implements Function<City, CityDto> {
    @Override
    public CityDto apply(City city) {
        return new CityDto(
                city.getId(),
                city.getName(),
                city.getPhoto()
        );
    }
}
