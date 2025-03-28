package com.artTech.wisewallet.service;

import com.artTech.wisewallet.dto.TransactionDTO;
import com.artTech.wisewallet.dto.TransactionResponseDTO;
import com.artTech.wisewallet.model.Category;
import com.artTech.wisewallet.model.Transaction;
import com.artTech.wisewallet.model.User;
import com.artTech.wisewallet.repository.CategoryRepository;
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
    private CategoryRepository categoryRepository;

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

        Long categoryId = transactionDTO.getCategoryId();
        System.out.println("ID da categoria recebida: " + categoryId);

        // Busca a categoria pelo ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria com ID " + categoryId + " não encontrada"));
        System.out.println("Categoria encontrada: " + category);

        int nextCode = transactionRepository.countByUserId(userId) + 1;

        Transaction transaction = Transaction.fromDTO(transactionDTO, user, category);
        transaction.setCode(nextCode);

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

        Long categoryId = transactionDTO.getCategoryId();

        // Buscar a categoria pelo ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria com ID " + categoryId + " não encontrada"));

        existingTransaction.setDescription(transactionDTO.getDescription());
        existingTransaction.setRecipient(transactionDTO.getRecipient());
        existingTransaction.setCategory(category);
        existingTransaction.setPaymentType(transactionDTO.getPaymentType());
        existingTransaction.setType(transactionDTO.getType());
        existingTransaction.setStats(transactionDTO.getStats());
        existingTransaction.setDate(transactionDTO.getDate());
        existingTransaction.setAmount(transactionDTO.getAmount());


        transactionRepository.save(existingTransaction);
    }

    public void cancelTransaction(Long id, String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }


        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada."));


        if (transaction.getStats() == Transaction.TransactionStats.CANCELADO) {
            throw new IllegalArgumentException("A transação já está cancelada.");
        }

        if (transaction.getStats() == Transaction.TransactionStats.PAGO) {
            throw new IllegalArgumentException("Não é possível cancelar uma transação já paga.");
        }


        transaction.setStats(Transaction.TransactionStats.CANCELADO);


        transactionRepository.save(transaction);

        System.out.println("Transação ID {} foi cancelada com sucesso." +  transaction.getId());
    }

    public void reopenTransaction(Long id, String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada."));

        if (!(transaction.getStats() == Transaction.TransactionStats.CANCELADO ||
                transaction.getStats() == Transaction.TransactionStats.PAGO)) {
            throw new IllegalArgumentException("Só é possível reabrir transações canceladas ou pagas.");
        }

        transaction.setStats(Transaction.TransactionStats.PENDENTE);
        transactionRepository.save(transaction);

        System.out.println("Transação ID " + transaction.getId() + " foi reaberta com sucesso.");
    }

    public void payTransaction(Long id, String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente.");
        }

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada."));


        if (!(transaction.getStats() == Transaction.TransactionStats.ATRASADO ||
                transaction.getStats() == Transaction.TransactionStats.PENDENTE)) {
            throw new IllegalArgumentException("Só é possível pagar transações que estão atrasadas ou pendentes.");
        }

        transaction.setStats(Transaction.TransactionStats.PAGO);
        transactionRepository.save(transaction);

        System.out.println("Transação ID {} foi paga com sucesso." + transaction.getId());
    }

    public void deleteTransaction(Long id, String token) {
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
            throw new SecurityException("Você não tem permissão para excluir esta transação.");
        }


        transactionRepository.delete(existingTransaction);
    }
}
