package com.commercial.commerce.sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.repository.AnnonceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    public List<AnnonceEntity> getAllEntity() {
        return annonceRepository.findAll();
    }

    public AnnonceEntity insert(AnnonceEntity annonce) {
        return annonceRepository.save(annonce);
    }
}
