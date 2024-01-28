package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.TypeEntity;
import com.commercial.commerce.sale.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public List<TypeEntity> getAllTypes() {
        return typeRepository.findAllActive();
    }

    public Optional<TypeEntity> getTypeById(String id) {
        return typeRepository.findByIdActive(id);
    }

    public TypeEntity createType(TypeEntity type) {
        return typeRepository.save(type);
    }

    public Optional<TypeEntity> updateType(String id, TypeEntity updatedType) {
        Optional<TypeEntity> existingType = typeRepository.findById(id);

        if (existingType.isPresent()) {
            // Effectuez la mise à jour du type existant avec les nouvelles données
            updatedType.setId(id);
            if (updatedType.getNom() == null) {
                updatedType.setNom(existingType.get().getNom());
            }
            if (updatedType.getState() == null) {
                updatedType.setState(existingType.get().getState());
            }
            return Optional.of(typeRepository.save(updatedType));
        } else {
            return Optional.empty();
        }
    }

    public Optional<TypeEntity> deleteType(String id) {
        TypeEntity existingCategory = typeRepository.findById(id).orElse(null);

        if (existingCategory != null) {
            existingCategory.setState(0);
            return Optional.of(typeRepository.save(existingCategory));
        } else {
            return Optional.empty();
        }
    }

    public TypeEntity insertCustom(TypeEntity type) {
        String id = typeRepository.insertCustom(type.getNom());
        type.setState(1);
        type.setId(id);
        return type;
    }

    public List<TypeEntity> selectWithPagination(int offset, int limit) {
        return typeRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public long pagination(int limit) {
        long number = typeRepository.count();
        return (number + limit - 1) / limit;
    }
}
