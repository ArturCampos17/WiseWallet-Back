package com.artTech.wisewallet.controller;

import com.artTech.wisewallet.dto.CategoryDTO;
import com.artTech.wisewallet.security.JwtService;
import com.artTech.wisewallet.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JwtService jwtService;

    // Endpoint para criar uma nova categoria
    @PostMapping
    public ResponseEntity<Map<String, String>> createCategory(
            @RequestBody CategoryDTO categoryDTO,
            @RequestHeader("Authorization") String authHeader) {

        return processCategory(() -> {
            Long userId = extractUserIdFromToken(authHeader);
            categoryService.createCategory(userId, categoryDTO.getName());
        }, "Categoria criada com sucesso!", "Erro ao criar categoria");
    }

    // Endpoint para listar todas as categorias de um usuário
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getUserCategories(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (!jwtService.validateToken(token, email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Long userId = jwtService.extractUserId(token);
            List<CategoryDTO> categories = categoryService.getUserCategories(userId);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para atualizar uma categoria existente
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO,
            @RequestHeader("Authorization") String authHeader) {

        return processCategory(() -> {
            Long userId = extractUserIdFromToken(authHeader);
            categoryService.updateCategory(id, userId, categoryDTO.getName());
        }, "Categoria atualizada com sucesso!", "Erro ao atualizar categoria");
    }

    // Endpoint para excluir uma categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        return processCategory(() -> {
            Long userId = extractUserIdFromToken(authHeader);
            categoryService.deleteCategory(id, userId);
        }, "Categoria excluída com sucesso!", "Erro ao excluir categoria");
    }

    // Metodo auxiliar para processar ações relacionadas a categorias
    private ResponseEntity<Map<String, String>> processCategory(Runnable categoryAction, String successMessage, String errorMessage) {
        try {
            categoryAction.run();

            Map<String, String> response = new HashMap<>();
            response.put("message", successMessage);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Metodo auxiliar para extrair o ID do usuário do token JWT
    private Long extractUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }

        String token = authHeader.substring(7);
        return jwtService.extractUserId(token);
    }
}