package com.artTech.wisewallet.service;

import com.artTech.wisewallet.dto.TransactionDTO;
import com.artTech.wisewallet.dto.TransactionResponseDTO;
import com.artTech.wisewallet.model.Transaction;
import com.artTech.wisewallet.model.User;
import com.artTech.wisewallet.repository.TransactionRepository;
import com.artTech.wisewallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artTech.wisewallet.security.JwtService;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtService jwtService;

    public TransactionResponseDTO createTransaction(TransactionDTO transactionDTO, String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }
        String jwtToken = token.substring(7);


        Long userId = jwtService.extractUserId(jwtToken);


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Transaction transaction = Transaction.fromDTO(transactionDTO, user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Retorna o DTO de resposta
        return savedTransaction.toResponseDTO();
    }

    public List<Transaction> getTransactionsByUser(String token) {
        // Remove o prefixo "Bearer " do token
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }
        String jwtToken = token.substring(7);

        // Extrai o ID do usuário do token
        Long userId = jwtService.extractUserId(jwtToken);

        return transactionRepository.findByUserId(userId);
    }
}