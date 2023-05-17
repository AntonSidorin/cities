package com.city.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;

@Tag(name = "City")
public record CityDto(Long id, @Size(max=200) String name, @Size(max=500) String photo) {}
