package com.city.service.mapper.city;

import com.city.dao.entity.City;
import com.city.dto.CitiesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CitiesToCitiesDtoMapper implements Function<Page<City>, CitiesDto> {

    private final CityToCityDtoMapper mapper;

    @Override
    public CitiesDto apply(Page<City> page) {
        return new CitiesDto(page.getContent().stream().map(mapper).toList(), page.getTotalPages());
    }
}
