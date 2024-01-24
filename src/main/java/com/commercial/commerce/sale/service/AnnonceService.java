package com.commercial.commerce.sale.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.chat.service.FileHelper;
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

    public AnnonceEntity insert(AnnonceEntity annonce) throws Exception {
        FileHelper file = new FileHelper();
        for (String fileBase64 : annonce.getPictures()) {
            file.uploadOnline(fileBase64);
        }
        return annonceRepository.save(annonce);
    }

    public AnnonceEntity addFavoris(String id, String idannonce) {

        AnnonceEntity annonce = annonceRepository.findById(idannonce).orElse(null);
        if (annonce != null) {
            annonce.getFavoris().add(Long.parseLong(refreshTokenService.getId(id)));
            Set<Long> uniqueFavoris = new HashSet<>(annonce.getFavoris());
            annonce.getFavoris().clear();
            annonce.getFavoris().addAll(uniqueFavoris);
            annonceRepository.save(annonce);
        }
        return annonce;
    }

    public AnnonceEntity removeFavoris(String user, String annonceId) {
        AnnonceEntity annonce = annonceRepository.findById(annonceId).orElse(null);

        if (annonce != null) {
            annonce.getFavoris().remove(refreshTokenService.getId(user));
            annonceRepository.save(annonce);

        }
        return annonce;
    }

    public List<AnnonceEntity> getAnnoncesByFavoris(String user) {
        List<AnnonceEntity> annonce = annonceRepository.findByFavoris(Long.parseLong(refreshTokenService.getId(user)));
        return annonce;
    }

    public List<AnnonceEntity> getAnnoncesByVendeur(String user) {
        List<AnnonceEntity> annonce = annonceRepository
                .findByVendeurIdvendeur(Long.parseLong(refreshTokenService.getId(user)));
        return annonce;
    }

    public List<AnnonceEntity> getRecentAnnonces(int limit) {
        Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(0, limit, sortByDateDesc);
        return annonceRepository.findAll(pageable).getContent();
    }

    public Optional<AnnonceEntity> getById(String id) {
        return annonceRepository.findById(id);
    }
}
