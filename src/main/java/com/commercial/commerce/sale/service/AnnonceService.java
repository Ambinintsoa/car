package com.commercial.commerce.sale.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.commercial.commerce.chat.model.JsonResponse;
import com.commercial.commerce.chat.service.FileHelper;
import com.commercial.commerce.sale.entity.AnnonceEntity;
import com.commercial.commerce.sale.entity.MakeEntity;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.repository.AnnonceRepository;
import com.commercial.commerce.sale.utils.Parameter;

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

        List<String> pictures = new ArrayList<String>();
        for (String fileBase64 : annonce.getPictures()) {
            JsonResponse json = file.uploadOnline(fileBase64);
            pictures.add(json.getData().getUrl());
        }
        annonce.setPictures(pictures);
        return annonceRepository.save(annonce);
    }

    public AnnonceEntity addFavoris(String id, String idannonce) {

        AnnonceEntity annonce = annonceRepository.findById(idannonce).orElse(null);
        if (annonce != null) {
            annonce.getFavoris().add(refreshTokenService.getId(id));
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
        List<AnnonceEntity> annonce = annonceRepository.findByFavoris(refreshTokenService.getId(user));
        return annonce;
    }

    public List<AnnonceEntity> getAnnoncesByVendeur(String user) {
        List<AnnonceEntity> annonce = annonceRepository
                .findByVendeur(refreshTokenService.getId(user));
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

    public List<AnnonceEntity> getBetween(int inf, int sup) {
        return annonceRepository.findByPrixLessThanEqualAndPrixGreaterThanEqual(sup, inf);
    }

    public List<AnnonceEntity> getInf(int inf) {
        return annonceRepository.findByPrixLessThanEqual(inf);
    }

    public List<AnnonceEntity> getSup(int sup) {
        return annonceRepository.findByPrixGreaterThanEqual(sup);
    }

    public List<AnnonceEntity> getBrand(List<MakeEntity> brand) {
        String[] idbrand = new String[brand.size()];
        for (int i = 0; i < brand.size(); i++) {
            idbrand[i] = brand.get(i).getId();
        }
        return annonceRepository.findByBrandIn(idbrand);
    }

    public List<AnnonceEntity> getModel(List<ModelEntity> brand) {
        String[] idbrand = new String[brand.size()];
        for (int i = 0; i < brand.size(); i++) {
            idbrand[i] = brand.get(i).getId();
        }
        return annonceRepository.findByModeleIn(idbrand);
    }

    public List<AnnonceEntity> getBetweenDate(LocalDateTime inf, LocalDateTime sup) {
        return annonceRepository.findByDateBetween(inf, sup);
    }

    public List<AnnonceEntity> getInfDate(LocalDateTime inf) {
        return annonceRepository.findByDateLessThanEqual(inf);
    }

    public List<AnnonceEntity> getSupDate(LocalDateTime sup) {
        return annonceRepository.findByDateGreaterThanEqual(sup);
    }

    public List<AnnonceEntity> research(Parameter parametre) {
        List<AnnonceEntity> entity1 = null;
        if (parametre.getDateInf() != null && parametre.getDateSup() != null) {
            entity1 = this.getBetweenDate(parametre.getDateInf(), parametre.getDateSup());
        }
        List<AnnonceEntity> entity2 = null;
        if (parametre.getInfMontant() != null && parametre.getSupMontant() != null) {
            entity2 = this.getBetween(parametre.getInfMontant(), parametre.getSupMontant());
        }
        List<AnnonceEntity> entity3 = null;
        if (parametre.getBrands() != null && parametre.getBrands().size() > 0) {
            entity3 = this.getBrand(parametre.getBrands());
        }
        List<AnnonceEntity> entity4 = null;
        if (parametre.getModeles() != null && parametre.getModeles().size() > 0) {
            entity4 = this.getModel(parametre.getModeles());
        }
        List<AnnonceEntity> list = this.intersect(entity1, entity2);
        list = this.intersect(entity3, list);
        list = this.intersect(list, entity4);
        return list;
    }

    public List<AnnonceEntity> intersect(List<AnnonceEntity> list1, List<AnnonceEntity> list2) {
        List<AnnonceEntity> list = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            for (int u = 0; i < list2.size(); u++) {
                if (list1.get(i).getId().compareToIgnoreCase(list2.get(u).getId()) == 0) {
                    list.add(list1.get(i));
                    break;
                }
            }
        }
        return list;
    }
}
