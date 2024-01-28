package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.CountryEntity;
import com.commercial.commerce.sale.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<CountryEntity> getAllCountries() {
        return countryRepository.findAllActive();
    }

    public Optional<CountryEntity> getCountryById(String id) {
        return countryRepository.findByIdActive(id);
    }

    public CountryEntity createCountry(CountryEntity country) {
        return countryRepository.save(country);
    }

    public Optional<CountryEntity> updateCountry(String id, CountryEntity updatedCountry) {
        Optional<CountryEntity> existingCountry = countryRepository.findById(id);

        if (existingCountry.isPresent()) {
            updatedCountry.setId(id);
            if (updatedCountry.getNom() == null) {
                updatedCountry.setNom(existingCountry.get().getNom());
            }
            if (updatedCountry.getState() == null) {
                updatedCountry.setState(existingCountry.get().getState());
            }
            return Optional.of(countryRepository.save(updatedCountry));
        } else {
            return Optional.empty();
        }
    }

    public Optional<CountryEntity> deleteCountry(String id) {
        CountryEntity existingCountry = countryRepository.findById(id).orElse(null);

        if (existingCountry != null) {
            existingCountry.setState(0);
            return Optional.of(countryRepository.save(existingCountry));
        } else {
            return Optional.empty();
        }
    }

    public CountryEntity insertCustom(CountryEntity country) {
        String id = countryRepository.insertCustom(country.getNom());
        country.setState(1);
        country.setId(id);
        return country;
    }

    public List<CountryEntity> selectWithPagination(int offset, int limit) {
        return countryRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public long pagination(int limit) {
        long number = countryRepository.count();
        return (number + limit - 1) / limit;
    }
}
