package com.commercial.commerce.sale.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.repository.AnnonceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public List<AnnonceEntity> getAllEntity() {
        return annonceRepository.findAll();
    }

    public AnnonceEntity insert(AnnonceEntity annonce) {
        return annonceRepository.save(annonce);
    }

    public AnnonceEntity addFavoris(String id, String idannonce) {

        AnnonceEntity annonce = annonceRepository.findById(idannonce).orElse(null);
        if (annonce != null) {
            List<String> pictures = annonce.getFavoris();
            pictures.add(refreshTokenService.getId(id));
            annonceRepository.save(annonce);
            List<String> favoris = new ArrayList<>();
            annonce.setFavoris(favoris);
        }
        return annonce;
    }

    public AnnonceEntity removeFavoris(String user, String annonceId) {
        AnnonceEntity annonce = annonceRepository.findById(annonceId).orElse(null);

        if (annonce != null) {
            List<String> favoris = annonce.getFavoris();
            favoris.remove(refreshTokenService.getId(user));
            annonceRepository.save(annonce);
            annonce.setFavoris(favoris);
            favoris = new ArrayList<>();
            annonce.setFavoris(favoris);

        }
        return annonce;
    }

    public List<AnnonceEntity> getAnnoncesByFavoris(String user) {
        List<AnnonceEntity> annonce = annonceRepository.findByFavoris(refreshTokenService.getId(user));
        List<String> favoris = new ArrayList<>();
        for (int i = 0; i < annonce.size(); i++) {
            annonce.get(i).setFavoris(favoris);
        }

        return annonce;
    }

    public List<AnnonceEntity> getRecentAnnonces(int limit) {
        Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(0, limit, sortByDateDesc);
        return annonceRepository.findAll(pageable).getContent();
    }
}
