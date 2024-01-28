package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.CouleurEntity;
import com.commercial.commerce.sale.repository.CouleurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouleurService {
    @Autowired
    private CouleurRepository couleurRepository;

    public List<CouleurEntity> getAllCouleurs() {
        return couleurRepository.findAllActive();
    }

    public Optional<CouleurEntity> getCouleurById(String id) {
        return couleurRepository.findByIdActive(id);
    }

    public CouleurEntity createCouleur(CouleurEntity couleur) {
        return couleurRepository.save(couleur);
    }

    public Optional<CouleurEntity> updateCouleur(String id, CouleurEntity updatedCouleur) {
        Optional<CouleurEntity> existingCouleur = couleurRepository.findById(id);

        if (existingCouleur.isPresent()) {
            updatedCouleur.setId(id);
            if (updatedCouleur.getNom() == null) {
                updatedCouleur.setNom(existingCouleur.get().getNom());
            }
            if (updatedCouleur.getState() == null) {
                updatedCouleur.setState(existingCouleur.get().getState());
            }

            return Optional.of(couleurRepository.save(updatedCouleur));
        } else {
            return Optional.empty();
        }
    }

    public Optional<CouleurEntity> deleteCouleur(String id) {
        CouleurEntity existingCouleur = couleurRepository.findById(id).orElse(null);

        if (existingCouleur != null) {
            existingCouleur.setState(0);
            return Optional.of(couleurRepository.save(existingCouleur));
        } else {
            return Optional.empty();
        }
    }

    public CouleurEntity insertCustom(CouleurEntity couleur) {
        String id = couleurRepository.insertCustom(couleur.getNom());
        couleur.setState(1);
        couleur.setId(id);
        return couleur;
    }

    public List<CouleurEntity> selectWithPagination(int offset, int limit) {
        return couleurRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public long pagination(int limit) {
        long number = couleurRepository.count();
        return (number + limit - 1) / limit;
    }
}
