package com.artTech.wisewallet.controller;

import com.artTech.wisewallet.dto.UserDTO;
import com.artTech.wisewallet.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        System.out.println("Name recebido: " + userDTO.getName());

         if (userDTO.getBirthDate() == null) {
            return ResponseEntity.badRequest().body("birthDate cannot be null");
        }
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException ex) {
            logger.error("Erro ao criar usuário: ", ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


}