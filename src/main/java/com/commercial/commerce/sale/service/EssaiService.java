package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.EssaiEntity;
import com.commercial.commerce.sale.repository.EssaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EssaiService {
    @Autowired
    private EssaiRepository essaiRepository;

    public List<EssaiEntity> getAll() {
        return this.essaiRepository.findAll();
    }

    public Optional<EssaiEntity> getById(Long id) {
        return this.essaiRepository.findById(id);
    }
}
