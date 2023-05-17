package com.city.service.city;

import com.city.dao.entity.City;
import com.city.dao.repository.CityRepository;
import com.city.dto.CitiesDto;
import com.city.dto.CityDto;
import com.city.exception.CityNotFoundException;
import com.city.service.mapper.city.CitiesToCitiesDtoMapper;
import com.city.service.mapper.city.CityToCityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;

    private final CityToCityDtoMapper mapper;
    private final CitiesToCitiesDtoMapper citiesMapper;

    public CityDto getCity(Long cityId) {
        return cityRepository.findById(cityId)
                .map(mapper)
                .orElseThrow(() ->new CityNotFoundException("City has not been found."));
    }

    public CitiesDto getCities(String name, int pageNumber, int rowPerPage) {

        if(StringUtils.isBlank(name)){
            return getCities(pageNumber, rowPerPage);
        }

        return citiesMapper.apply(cityRepository.findByNameContainsIgnoringCase(
                        name,
                        PageRequest.of(pageNumber, rowPerPage, Sort.by("name"))
                ));
    }

    public CitiesDto getCities(int pageNumber, int rowPerPage) {
        return citiesMapper.apply(cityRepository.findAll(
                        PageRequest.of(pageNumber, rowPerPage, Sort.by("name"))
                ));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public CityDto updateCity(Long cityId, CityDto cityDto) {

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException("City has not been found."));

        city.setName(cityDto.name());
        city.setPhoto(cityDto.photo());

        return mapper.apply(cityRepository.save(city));
    }

}
