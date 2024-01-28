package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.MotorEntity;
import com.commercial.commerce.sale.repository.MotorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MotorService {
    @Autowired
    private MotorRepository motorRepository;

    public List<MotorEntity> getAllMotors() {
        return motorRepository.findAllActive();
    }

    public Optional<MotorEntity> getMotorById(String id) {
        return motorRepository.findByIdActive(id);
    }

    public MotorEntity createMotor(MotorEntity motor) {
        return motorRepository.save(motor);
    }

    public Optional<MotorEntity> updateMotor(String id, MotorEntity updatedMotor) {
        Optional<MotorEntity> existingMotor = motorRepository.findById(id);

        if (existingMotor.isPresent()) {
            // Effectuez la mise à jour du moteur existant avec les nouvelles données
            updatedMotor.setId(id);
            if (updatedMotor.getNom() == null) {
                updatedMotor.setNom(existingMotor.get().getNom());
            }
            if (updatedMotor.getState() == null) {
                updatedMotor.setState(existingMotor.get().getState());
            }
            return Optional.of(motorRepository.save(updatedMotor));
        } else {
            return Optional.empty();
        }
    }

    public Optional<MotorEntity> deleteMotor(String id) {
        MotorEntity existingCategory = motorRepository.findById(id).orElse(null);

        if (existingCategory != null) {
            existingCategory.setState(0);
            return Optional.of(motorRepository.save(existingCategory));
        } else {
            return Optional.empty();
        }
    }

    public MotorEntity insertCustom(MotorEntity motor) {
        String id = motorRepository.insertCustom(motor.getNom());
        motor.setState(1);
        motor.setId(id);
        return motor;
    }

    public List<MotorEntity> selectWithPagination(int offset, int limit) {
        return motorRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public long pagination(int limit) {
        long number = motorRepository.count();
        return (number + limit - 1) / limit;
    }
}
