package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.MakeEntity;
import com.commercial.commerce.sale.repository.MakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MakeService {
    @Autowired
    private MakeRepository makeRepository;

    public List<MakeEntity> getAllMakes() {
        return makeRepository.findAllActive();
    }

    public Optional<MakeEntity> getMakeById(String id) {
        return makeRepository.findByIdActive(id);
    }

    public MakeEntity createMake(MakeEntity make) {
        return makeRepository.save(make);
    }

    public Optional<MakeEntity> updateMake(String id, MakeEntity updatedMake) {
        Optional<MakeEntity> existingMake = makeRepository.findById(id);

        if (existingMake.isPresent()) {
            // Effectuez la mise à jour du make existant avec les nouvelles données
            updatedMake.setId(id);
            if (updatedMake.getNom() == null) {
                updatedMake.setNom(existingMake.get().getNom());
            }
            if (updatedMake.getState() == null) {
                updatedMake.setState(existingMake.get().getState());
            }

            return Optional.of(makeRepository.save(updatedMake));
        } else {
            return Optional.empty();
        }
    }

    public Optional<MakeEntity> deleteMake(String id) {
        MakeEntity existingCategory = makeRepository.findById(id).orElse(null);

        if (existingCategory != null) {
            existingCategory.setState(0);
            return Optional.of(makeRepository.save(existingCategory));
        } else {
            return Optional.empty();
        }
    }

    public MakeEntity insertCustom(MakeEntity make) {
        String id = makeRepository.insertCustom(make.getNom());
        make.setState(1);
        make.setId(id);
        return make;

    }

    public List<MakeEntity> selectWithPagination(int offset, int limit) {
        return makeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public long pagination(int limit) {
        long number = makeRepository.count();
        return (number + limit - 1) / limit;
    }
}
