package com.artTech.wisewallet.controller;

import com.artTech.wisewallet.dto.LoginRequest;
import com.artTech.wisewallet.dto.UserDTO;
import com.artTech.wisewallet.model.User;
import com.artTech.wisewallet.security.JwtService;
import com.artTech.wisewallet.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autentica o usuário e gera o token JWT
            String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            // Retorna uma resposta JSON com o token
            Map<String, Object> response = Map.of(
                    "message", "Login realizado com sucesso!",
                    "authenticated", true,
                    "token", token
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = Map.of(
                    "message", "Erro ao realizar login: " + e.getMessage(),
                    "authenticated", false
            );
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {

        session.invalidate();

        Map<String, Object> response = Map.of(
                "message", "Logout realizado com sucesso!",
                "authenticated", false
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-auth")
    public ResponseEntity<Map<String, Boolean>> checkAuth(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtService.extractEmail(token);
            boolean isValid = jwtService.validateToken(token, email);
            return ResponseEntity.ok(Map.of("authenticated", isValid));
        }
        return ResponseEntity.ok(Map.of("authenticated", false));
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUser(HttpSession session) {

        String email = (String) session.getAttribute("email");

        if (email == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Usuário não autenticado"));
        }

        User user = authService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Usuário não encontrado"));
        }

        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(Map.of("user", userDTO));
    }
}