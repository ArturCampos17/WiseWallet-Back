//package com.artTech.wisewallet.controller;
//
//import com.artTech.wisewallet.dto.TransactionDTO;
//import com.artTech.wisewallet.model.Transaction;
//import com.artTech.wisewallet.service.TransactionService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/transaction")
//public class TransactionController {
//
//    private final TransactionService transactionService;
//
//    @Autowired
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    // Criar uma nova transferência
//    @PostMapping
//    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO request) {
//        Transaction newTransaction = transactionService.createTransaction(request);  // Chama o serviço para criar a transação
//        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);  // Retorna o novo objeto com status CREATED
//    }
//
//    // Listar todas as transferências de um usuário
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
//        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
//        return ResponseEntity.ok(transactions);  // Retorna as transações do usuário
//    }
//
//    // Obter detalhes de uma transferência específica
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable Long id) {
//        Optional<Transaction> transaction = transactionService.getTransactionById(id);
//        return ResponseEntity.ok(transaction);  // Retorna a transação encontrada
//    }
//}
