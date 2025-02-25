package com.artTech.wisewallet.controller;

import com.artTech.wisewallet.dto.TransactionDTO;
import com.artTech.wisewallet.security.JwtService;
import com.artTech.wisewallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createTransaction(
            @RequestBody TransactionDTO transactionDTO,
            @RequestHeader("Authorization") String token) {
        try {
            System.out.println("TransactionDTO recebido: " + transactionDTO);

            transactionService.createTransaction(transactionDTO, token);


            Map<String, String> response = new HashMap<>();
            response.put("message", "Transação criada com sucesso!");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro ao criar transação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getUserTransactions(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(null);
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (!jwtService.validateToken(token, email)) {
            return ResponseEntity.status(401).body(null);
        }

        try {
            List<TransactionDTO> transactions = transactionService.getUserTransactions(email);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionDTO transactionDTO,
            @RequestHeader("Authorization") String token) {
        try {
            System.out.println("Atualizando transação com ID: " + id);
            transactionService.updateTransaction(id, transactionDTO, token);

            // Resposta de sucesso
            Map<String, String> response = new HashMap<>();
            response.put("message", "Transação atualizada com sucesso!");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Resposta de erro para problemas de validação
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            // Resposta de erro genérica
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erro ao atualizar transação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}