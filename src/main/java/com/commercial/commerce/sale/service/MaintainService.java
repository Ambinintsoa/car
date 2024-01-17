package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.MaintainEntity;
import com.commercial.commerce.sale.repository.MaintainRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintainService {
    @Autowired
    private MaintainRepository maintainRepository;

    public List<MaintainEntity> getAllMaintains() {
        return maintainRepository.findAllActive();
    }

    public Optional<MaintainEntity> getMaintainById(String id) {
        return maintainRepository.findByIdActive(id);
    }

    public MaintainEntity createMaintain(MaintainEntity maintain) {
        return maintainRepository.save(maintain);
    }

    public Optional<MaintainEntity> updateMaintain(String id, MaintainEntity updatedMaintain) {
        Optional<MaintainEntity> existingMaintain = maintainRepository.findById(id);

        if (existingMaintain.isPresent()) {
            // Perform the update of the existing maintain with the new data
            updatedMaintain.setId(id);
            if (updatedMaintain.getNom() == null) {
                updatedMaintain.setNom(existingMaintain.get().getNom());
            }
            return Optional.of(maintainRepository.save(updatedMaintain));
        } else {
            return Optional.empty();
        }
    }

    public Optional<MaintainEntity> deleteMaintain(String id) {
        MaintainEntity existingMaintain = maintainRepository.findById(id).orElse(null);

        if (existingMaintain != null) {
            existingMaintain.setState(0);
            return Optional.of(maintainRepository.save(existingMaintain));
        } else {
            return Optional.empty();
        }
    }

    public MaintainEntity insertCustom(MaintainEntity maintain) {
        String id = maintainRepository.insertCustom(maintain.getNom());
        maintain.setState(1);
        maintain.setId(id);
        return maintain;
    }
}
