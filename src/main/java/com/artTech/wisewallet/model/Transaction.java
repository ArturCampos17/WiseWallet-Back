package com.artTech.wisewallet.model;

import com.artTech.wisewallet.dto.TransactionDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")

public class Transaction {

//    description String
//
//    recipient String
//
//    category String
//
//    paymentType - PIX-DEBITO-CREDITO-BOLETO-DINEHIRO
//
//    stats - PAGO -PENDENTE - CANCELADO - ATRASADO
//
//    data localDate
//
//    valor BigDecimal



    public enum TransactionPaymentType {
        BOLETO, CREDITO, DEBITO,DINHEIRO, PIX
    }

    public enum TransactionStats {
        ATRASADO, CANCELADO, PAGO, PENDENTE
    }

    public enum TransactionType {
        ENTRADA, SAIDA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String recipient;

    private String category;

    @Enumerated(EnumType.STRING)
    private TransactionPaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private TransactionStats stats;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;


    private LocalDate date;

    @ManyToOne
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public static Transaction fromDTO(TransactionDTO dto, User user) {
        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setRecipient(dto.getRecipient());
        transaction.setCategory(dto.getCategory());
        transaction.setPaymentType(dto.getPaymentType());
        transaction.setType(dto.getType());
        transaction.setStats(dto.getStats());
        transaction.setDate(dto.getDate());
        transaction.setAmount(dto.getAmount());
        transaction.setUser(user);
        return transaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TransactionPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(TransactionPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public TransactionStats getStats() {
        return stats;
    }

    public void setStats(TransactionStats stats) {
        this.stats = stats;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
