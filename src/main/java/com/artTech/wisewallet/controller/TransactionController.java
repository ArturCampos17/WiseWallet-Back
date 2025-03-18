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
        System.out.println("TransactionDTO recebido: " + transactionDTO);
        return processTransaction(() -> transactionService.createTransaction(transactionDTO, token),
                "Transação criada com sucesso!",
                "Erro ao criar transação");
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getUserTransactions(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        if (!jwtService.validateToken(token, email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<TransactionDTO> transactions = transactionService.getUserTransactions(email);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateTransaction(
            @PathVariable Long id,
            @RequestBody TransactionDTO transactionDTO,
            @RequestHeader("Authorization") String token) {
        System.out.println("Atualizando transação com ID: " + id);
        return processTransaction(() -> transactionService.updateTransaction(id, transactionDTO, token),
                "Transação atualizada com sucesso!",
                "Erro ao atualizar transação");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTransaction(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        System.out.println("Recebida requisição para deletar transação com ID: " + id);
        return processTransaction(() -> transactionService.deleteTransaction(id, token),
                "Transação deletada com sucesso!", "Erro ao deletar transação");
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Map<String, String>> cancelTransaction(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        System.out.println("Recebida requisição para cancelar transação com ID: " + id);
        return processTransaction(() -> transactionService.cancelTransaction(id, token),
                "Transação cancelada com sucesso!", "Erro ao cancelar transação");
    }

    @PatchMapping("/{id}/reopen")
    public ResponseEntity<Map<String, String>> reopenTransaction(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        System.out.println("Recebida requisição para reabrir transação com ID: " + id);
        return processTransaction(() -> transactionService.reopenTransaction(id, token),
                "Transação reaberta com sucesso!", "Erro ao reabrir transação");
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<Map<String, String>> payTransaction(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        System.out.println("Recebida requisição para pagar transação com ID: " + id);
        return processTransaction(() -> transactionService.payTransaction(id, token),
                "Transação paga com sucesso!", "Erro ao pagar transação");
    }

    //CRIADO PARA EVITAR DUPLICIDADE NO CODIGO DAS MENSAGENS
    private ResponseEntity<Map<String, String>> processTransaction(Runnable transactionAction, String successMessage, String errorMessage) {
        try {
            transactionAction.run();

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

}