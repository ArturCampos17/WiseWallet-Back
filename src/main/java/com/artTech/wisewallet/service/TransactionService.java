//package com.artTech.wisewallet.service;
//
//import com.artTech.wisewallet.dto.TransactionDTO;
//import com.artTech.wisewallet.model.Transaction;
//import com.artTech.wisewallet.model.User;
//import com.artTech.wisewallet.repository.TransactionRepository;
//import com.artTech.wisewallet.repository.UserRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TransactionService {
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Autowired
//    private UserRepository userRepository;  // Needed for fetching users
//
//    public List<Transaction> getAllTransactions() {
//        return transactionRepository.findAll();
//    }
//
//    public Optional<Transaction> getTransactionById(Long id) {
//        return transactionRepository.findById(id);
//    }
//
//    // Corrected createTransaction method
//    public Transaction createTransaction(@Valid TransactionDTO transactionDTO) {
//        // Create a new Transaction instance
//        Transaction transaction = new Transaction();
//
//        // Converting Double to BigDecimal
//        transaction.setAmount(BigDecimal.valueOf(transactionDTO.getAmount()));
//
//        // Assigning the values from the DTO
//        transaction.setDescription(transactionDTO.getDescription());
//
//        // Fetching the users by their IDs
//        User sender = userRepository.findById(transactionDTO.getSenderId())
//                .orElseThrow(() -> new RuntimeException("Sender not found"));
//
//
//        // Associating the users to the transaction
//        transaction.setPayer(sender);  // Assuming "payer" is the sender
//
//
//        // Setting other values of the transaction
//        transaction.setType(Transaction.TransactionType.CREDIT);  // Or according to the DTO if a type field exists
//        transaction.setStatus(Transaction.TransactionStatus.PAID);  // Similarly, adjust if status is passed in DTO
//        transaction.setCategory("Default category");  // Use the correct value if available in DTO
//        transaction.setDate(LocalDate.now());  // Set the current date
//        //transaction.setCreatedAt(LocalDate.now());  // Set creation date as current date
//
//        // Saving the transaction in the database
//        return transactionRepository.save(transaction);
//    }
//
//    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
//        return transactionRepository.findById(id).map(transaction -> {
//            transaction.setPayer(updatedTransaction.getPayer());
//            transaction.setRecipient(updatedTransaction.getRecipient());
//            transaction.setCategory(updatedTransaction.getCategory());
//            transaction.setType(updatedTransaction.getType());
//            transaction.setStatus(updatedTransaction.getStatus());
//            transaction.setDescription(updatedTransaction.getDescription());
//            transaction.setAmount(updatedTransaction.getAmount());
//            transaction.setDate(updatedTransaction.getDate());
//            return transactionRepository.save(transaction);
//        }).orElseThrow(() -> new RuntimeException("Transaction not found"));
//    }
//
//    public void deleteTransaction(Long id) {
//        transactionRepository.deleteById(id);
//    }
//
//    public BigDecimal getTotalAmount() {
//        return transactionRepository.findAll()
//                .stream()
//                .map(Transaction::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    public List<Transaction> getTransactionsByUserId(Long userId) {
//        return transactionRepository.findByUserId(userId);
//    }
//}
