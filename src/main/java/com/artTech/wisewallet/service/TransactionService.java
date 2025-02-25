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
import java.util.stream.Collectors;

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
        transactionRepository.save(transaction);
        return transaction.toResponseDTO();
    }

    public List<TransactionDTO> getUserTransactions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Transaction> transactions = transactionRepository.findByUserId(user.getId());
        return transactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    public void updateTransaction(Long id, TransactionDTO transactionDTO, String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }

        String jwtToken = token.substring(7);
        Long userId = jwtService.extractUserId(jwtToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));


        if (!existingTransaction.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Você não tem permissão para atualizar esta transação.");
        }

        existingTransaction.setDescription(transactionDTO.getDescription());
        existingTransaction.setRecipient(transactionDTO.getRecipient());
        existingTransaction.setCategory(transactionDTO.getCategory());
        existingTransaction.setPaymentType(transactionDTO.getPaymentType());
        existingTransaction.setType(transactionDTO.getType());
        existingTransaction.setStats(transactionDTO.getStats());
        existingTransaction.setDate(transactionDTO.getDate());
        existingTransaction.setAmount(transactionDTO.getAmount());


        transactionRepository.save(existingTransaction);
    }
}