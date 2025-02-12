package com.artTech.wisewallet.controller;

import com.artTech.wisewallet.dto.LoginRequest;
import com.artTech.wisewallet.model.User;
import com.artTech.wisewallet.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            // Autentica o usuário
            authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            // Armazena o email na sessão
            session.setAttribute("email", loginRequest.getEmail());

            // Retorna uma resposta JSON
            Map<String, Object> response = Map.of(
                    "message", "Login realizado com sucesso!",
                    "authenticated", true
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
        // Invalida a sessão
        session.invalidate();

        // Retorna uma resposta JSON
        Map<String, Object> response = Map.of(
                "message", "Logout realizado com sucesso!",
                "authenticated", false
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/check-auth")
    public ResponseEntity<Map<String, Boolean>> checkAuth(@CookieValue(value = "auth_cookie", required = false) String authCookie) {
        boolean isAuthenticated = authCookie != null && !authCookie.isEmpty();

        // Retorna o estado de autenticação como JSON
        Map<String, Boolean> response = Map.of("authenticated", isAuthenticated);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUser(HttpSession session) {
        // Recupera o email da sessão
        String email = (String) session.getAttribute("email");

        User user = authService.getUserByEmail(email);

        if (email == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Usuário não autenticado"));
        }

        Map<String, Object> userData = Map.of(
                "name", user.getName(),
                "email", user.getEmail()
        );

        return ResponseEntity.ok(userData);
    }
}