package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.MakeEntity;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.repository.MakeRepository;
import com.commercial.commerce.sale.repository.ModelRepository;
import com.commercial.commerce.sale.repository.TypeRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelService {
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private MakeRepository makeRepository;
    @Autowired
    private TypeRepository typeRepository;

    public ModelEntity insertCustom(ModelEntity modelEntity) {

        modelEntity
                .setId(modelRepository.insertCustom(modelEntity.getNom(), modelEntity.getDate(), modelEntity.getDoors(),
                        modelEntity.getPlaces(), modelEntity.getType().getId(), modelEntity.getMake().getId()));
        modelEntity.setState(1);
        modelEntity.setMake(makeRepository.findByIdActive(modelEntity.getMake().getId()).get());
        modelEntity.setType(typeRepository.findByIdActive(modelEntity.getType().getId()).get());
        return modelEntity;
    }

    public List<ModelEntity> getModelsByMake(String id) {
        return modelRepository.findByMake(id);
    }

    public Optional<ModelEntity> getModelById(String id) {
        return modelRepository.findByIdActive(id);
    }

    public Optional<ModelEntity> delete(String id) {
        ModelEntity existingCategory = modelRepository.findById(id).orElse(null);

        if (existingCategory != null) {
            existingCategory.setState(0);
            return Optional.of(modelRepository.save(existingCategory));
        } else {
            return Optional.empty();
        }
    }

    public Optional<ModelEntity> update(String id, ModelEntity updatedMake) {
        Optional<ModelEntity> existingMake = modelRepository.findById(id);

        if (existingMake.isPresent()) {
            // Effectuez la mise à jour du make existant avec les nouvelles données
            updatedMake.setId(id);

            updatedMake.verify(existingMake.get());
            return Optional.of(modelRepository.save(updatedMake));
        } else {
            return Optional.empty();
        }
    }

}
