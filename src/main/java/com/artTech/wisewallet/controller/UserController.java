package com.artTech.wisewallet.controller;

import com.artTech.wisewallet.dto.UserDTO;
import com.artTech.wisewallet.security.JwtService;
import com.artTech.wisewallet.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        System.out.println("Endpoint /api/users acessado com os dados: " + userDTO);

        if (userDTO.getBirthDate() == null) {
            System.out.println("Erro: birthDate não pode ser nulo");
            return ResponseEntity.badRequest().body("birthDate cannot be null");
        }

        try {
            UserDTO createdUser = userService.createUser(userDTO);
            System.out.println("Usuário criado com sucesso: " + createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException ex) {
            System.out.println("Erro ao criar usuário: " + ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        logger.info("Endpoint /api/users/profile acessado");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Token ausente ou inválido");
            return ResponseEntity.status(401).body(null);
        }


        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        logger.info("Email extraído do token: {}", email);

        // Valida o token
        if (!jwtService.validateToken(token, email)) {
            logger.warn("Token inválido ou expirado");
            return ResponseEntity.status(401).body(null);
        }

        try {

            UserDTO userDTO = userService.getAuthenticatedUser(email);
            logger.info("Perfil do usuário retornado com sucesso: {}", userDTO);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            logger.error("Erro ao buscar perfil do usuário: ", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
            @RequestBody @Valid UserDTO userDTO,
            @RequestHeader("Authorization") String authHeader) {
        logger.info("Endpoint /api/users/profile acessado para atualização");

        // Verifica se o token está presente e é válido
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Token ausente ou inválido");
            return ResponseEntity.status(401).body(Map.of("message", "Token ausente ou inválido"));
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        // Valida o token
        if (!jwtService.validateToken(token, email)) {
            logger.warn("Token inválido ou expirado");
            return ResponseEntity.status(401).body(Map.of("message", "Token inválido ou expirado"));
        }

        try {
            // Chama o serviço para atualizar o perfil do usuário
            UserDTO updatedUser = userService.updateAuthenticatedUser(userDTO, email);
            logger.info("Perfil do usuário atualizado com sucesso: {}", updatedUser);
            return ResponseEntity.ok(Map.of("message", "Perfil atualizado com sucesso!", "data", updatedUser));
        } catch (Exception e) {
            logger.error("Erro ao atualizar perfil do usuário: ", e);
            return ResponseEntity.status(500).body(Map.of("message", "Erro ao atualizar perfil: " + e.getMessage()));
        }
    }

}