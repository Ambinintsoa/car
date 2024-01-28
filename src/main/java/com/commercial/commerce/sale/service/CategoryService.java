package com.commercial.commerce.sale.service;

import com.commercial.commerce.sale.entity.CategoryEntity;
import com.commercial.commerce.sale.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
            updatedCategory.setId(id);
            if (updatedCategory.getNom() == null) {
                updatedCategory.setNom(existingCategory.get().getNom());
            }
            if (updatedCategory.getState() == null) {
                updatedCategory.setState(existingCategory.get().getState());
            }
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
        category.setState(1);
        category.setId(id);
        return category;

    }

    public List<CategoryEntity> selectWithPagination(int offset, int limit) {
        return categoryRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public long pagination(int limit) {
        long number = categoryRepository.count();
        return (number + limit - 1) / limit;
    }
}
