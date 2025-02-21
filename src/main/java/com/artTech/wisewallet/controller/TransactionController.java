package com.artTech.wisewallet.controller;

import com.artTech.wisewallet.dto.TransactionDTO;
import com.artTech.wisewallet.dto.TransactionResponseDTO;
import com.artTech.wisewallet.model.Transaction;
import com.artTech.wisewallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionDTO transactionDTO, @RequestHeader("Authorization") String token) {
        TransactionResponseDTO responseDTO = transactionService.createTransaction(transactionDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@RequestHeader("Authorization") String token) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(token);
        return ResponseEntity.ok(transactions);
    }
}