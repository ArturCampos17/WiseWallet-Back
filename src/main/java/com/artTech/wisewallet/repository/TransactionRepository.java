//package com.artTech.wisewallet.repository;
//
//import com.artTech.wisewallet.model.Transaction;
//import com.artTech.wisewallet.model.Transaction.TransactionStatus;
//import com.artTech.wisewallet.model.Transaction.TransactionType;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@Repository
//public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByUserId(Long userId);
//    // Busca todas as transações por um usuário específico (pagador)
//    List<Transaction> findByPayerId(Long userId);
//
//    // Busca todas as transações por status (PAGO, PENDENTE, CANCELADO)
//    List<Transaction> findByStatus(TransactionStatus status);
//
//    // Busca todas as transações por tipo (CREDITO, DEBITO, PIX)
//    List<Transaction> findByType(TransactionType type);
//
//    // Busca todas as transações dentro de um intervalo de datas
//    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
//
//    // Soma total das transações
//    BigDecimal sumByAmount();
//
//    // Soma total por usuário pagador
//    BigDecimal sumByPayerId(Long payerId);
//}
