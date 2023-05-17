package com.city.controller.city;

import com.city.controller.exception.ErrorResult;
import com.city.dto.CitiesDto;
import com.city.dto.CityDto;
import com.city.service.city.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "City API")
@RequestMapping("/api/v1/cities")
@SecurityRequirement(name = "bearer")
public class CityController {

    private final CityService cityService;

    @Value("${cities.row.per.page}")
    private int rowPerPage;

    @Operation(summary = "Get city by id")
    @Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(type = "integer"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))}),
            @ApiResponse(responseCode = "403", description = "JWT token is not valid.", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))})
    })
    @GetMapping(value = "/{cityId}", produces = "application/json")
    public CityDto getCity(@PathVariable("cityId") Long cityId) {
        return cityService.getCity(cityId);
    }

    @Operation(summary = "Get a paginated list of cities")
    @Parameter(in = ParameterIn.QUERY, name = "name", description = "City name", schema = @Schema(type = "string"))
    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Page number", schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(in = ParameterIn.QUERY, name = "size", description = "Page size", schema = @Schema(type = "integer", defaultValue = "30"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))}),
            @ApiResponse(responseCode = "403", description = "JWT token is not valid.", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))})
    })
    @GetMapping(produces = "application/json")
    public CitiesDto getCities(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "page", defaultValue = "0") int pageIndex,
            @RequestParam(value = "size", defaultValue = "30") int size
    ) {
        return cityService.getCities(name, pageIndex > 0 ? pageIndex : 0, size > 0 ? size : rowPerPage);
    }

    @Operation(summary = "Update city")
    @Parameter(in = ParameterIn.PATH, name = "cityId", description = "City Id", required = true, schema = @Schema(type = "String"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))}),
            @ApiResponse(responseCode = "403", description = "JWT token is not valid.", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResult.class)))})
    })
    @PatchMapping(value = "/{cityId}", produces = "application/json")
    @PreAuthorize("hasAuthority('ALLOW_EDIT')")
    public CityDto updateCity(
            @PathVariable("cityId") Long cityId,
            @RequestBody @Valid CityDto city) {
        return cityService.updateCity(cityId, city);
    }

}
