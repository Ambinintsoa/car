package com.commercial.commerce.sale.controller;

import com.commercial.commerce.sale.entity.CountryEntity;
import com.commercial.commerce.sale.service.CountryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bibine")
@AllArgsConstructor
public class CountryController extends Controller {

    private final CountryService countryService;

    @GetMapping("/actu/countries")
    public ResponseEntity<ApiResponse<List<CountryEntity>>> getAllCountries() {
        try {
            List<CountryEntity> countries = countryService.getAllCountries();
            return createResponseEntity(countries, "Countries retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/pagination/countries")
    public ResponseEntity<ApiResponse<List<CountryEntity>>> getAllCountriesWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<CountryEntity> types = countryService.selectWithPagination(id, limit);
            return createResponseEntity(types, "Countries retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/countries/{id}")
    public ResponseEntity<ApiResponse<CountryEntity>> getCountryById(@PathVariable String id) {
        try {
            Optional<CountryEntity> country = countryService.getCountryById(id);
            return country.map(c -> createResponseEntity(c, "Country retrieved successfully for this id"))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.OK)
                            .body(new ApiResponse<>(null, new Status("error", "NOT FOUND"), LocalDateTime.now())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping(value = "/countries")
    public ResponseEntity<ApiResponse<CountryEntity>> createCountry(HttpServletRequest request,
            @Valid @RequestBody CountryEntity country) {
        try {
            CountryEntity createdCountry = countryService.insertCustom(country);
            return createResponseEntity(createdCountry, "Country created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<ApiResponse<CountryEntity>> updateCountry(HttpServletRequest request, @PathVariable String id,
            @Valid @RequestBody CountryEntity updatedCountry) {
        try {
            Optional<CountryEntity> existingCountry = countryService.updateCountry(id, updatedCountry);
            if (existingCountry.isPresent()) {
                return createResponseEntity(existingCountry.get(), "Country updated successfully");
            } else {
                return createResponseEntity(null, "nothing updated ");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<ApiResponse<CountryEntity>> deleteCountry(HttpServletRequest request,
            @PathVariable String id) {
        try {
            Optional<CountryEntity> existingCountry = countryService.deleteCountry(id);
            if (existingCountry.isPresent()) {
                return createResponseEntity(existingCountry.get(), "Country updated successfully");
            } else {
                return createResponseEntity(null, "nothing deleted ");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/actu/country/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(countryService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
