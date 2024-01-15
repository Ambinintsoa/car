package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.CategoryEntity;
import com.commercial.commerce.sale.repository.CategoryRepository;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAllActive();
    }

    public Optional<CategoryEntity> getCategoryById(String id) {
        return categoryRepository.findByIdActive(id);
    }

    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    public Optional<CategoryEntity> updateCategory(String id, CategoryEntity updatedCategory) {
        Optional<CategoryEntity> existingCategory = categoryRepository.findById(id);

        if (existingCategory.isPresent()) {
            // Effectuez la mise à jour de la catégorie existante avec les nouvelles données
            updatedCategory.setId(id);
            return Optional.of(categoryRepository.save(updatedCategory));
        } else {
            return Optional.empty();
        }
    }

    public Optional<CategoryEntity> deleteCategory(String id) {
        CategoryEntity existingCategory = categoryRepository.findById(id).orElse(null);

        if (existingCategory != null) {
            existingCategory.setState(0);
            return Optional.of(categoryRepository.save(existingCategory));
        } else {
            return Optional.empty();
        }
    }

    public CategoryEntity insertCustom(CategoryEntity category) {
        String id = categoryRepository.insertCustom(category.getNom());
        category.setId(id);
        return category;

    }
}
