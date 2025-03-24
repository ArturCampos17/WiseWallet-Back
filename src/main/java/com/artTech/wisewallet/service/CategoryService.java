package com.artTech.wisewallet.service;

import com.artTech.wisewallet.dto.CategoryDTO;
import com.artTech.wisewallet.model.Category;
import com.artTech.wisewallet.model.User;
import com.artTech.wisewallet.repository.CategoryRepository;
import com.artTech.wisewallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    public Category createCategory(Long userId, String categoryName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        int nextCodigo = categoryRepository.countByUserId(userId) + 1;

        Category category = new Category();
        category.setName(categoryName);
        category.setUser(user);
        category.setCodigo(nextCodigo);

        return categoryRepository.save(category);
    }


    public List<CategoryDTO> getUserCategories(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updateCategory(Long categoryId, Long userId, String categoryName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(userId)) {
            throw new SecurityException("Você não tem permissão para alterar esta categoria.");
        }

        category.setName(categoryName);
        categoryRepository.save(category);
    }


    public void deleteCategory(Long categoryId, Long userId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(userId)) {
            throw new SecurityException("Você não tem permissão para excluir esta categoria.");
        }

        categoryRepository.delete(category);
    }


    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCodigo(category.getCodigo());
        return dto;
    }
}