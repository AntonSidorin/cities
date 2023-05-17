package com.city.dto;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Cities")
public record CitiesDto(List<CityDto> cities, int totalPages) {}