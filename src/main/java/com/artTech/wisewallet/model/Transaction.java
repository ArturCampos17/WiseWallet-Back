package com.artTech.wisewallet.model;

import com.artTech.wisewallet.dto.TransactionDTO;
import com.artTech.wisewallet.dto.TransactionResponseDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")

public class Transaction {

    public enum TransactionPaymentType {
        BOLETO, CREDITO, DEBITO,DINHEIRO, PIX;
        @Override
        public String toString() {
            return this.name();
        }

        @JsonCreator
        public static TransactionPaymentType fromString(String value) {
            return TransactionPaymentType.valueOf(value.toUpperCase());
        }
    }

    public enum TransactionStats {
        ATRASADO, CANCELADO, PAGO, PENDENTE;
        @Override
        public String toString() {
            return this.name();
        }

        @JsonCreator
        public static TransactionStats fromString(String value) {
            return TransactionStats.valueOf(value.toUpperCase());
        }
    }

    public enum TransactionType {
        ENTRADA, SAIDA;
        @Override
        public String toString() {
            return this.name();
        }

        @JsonCreator
        public static TransactionType fromString(String value) {
            return TransactionType.valueOf(value.toUpperCase());
        }
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


    public static Transaction fromDTO(TransactionDTO transactionDTO, User user) {
        Transaction transaction = new Transaction();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDate(transactionDTO.getDate());
        transaction.setCategory(transactionDTO.getCategory());
        transaction.setPaymentType(transactionDTO.getPaymentType());
        transaction.setStats(transactionDTO.getStats());
        transaction.setType(transactionDTO.getType());
        transaction.setRecipient(transactionDTO.getRecipient());

        transaction.setUser(user);
        return transaction;
    }

    public TransactionResponseDTO toResponseDTO() {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(this.id);
        dto.setDescription(this.description);
        dto.setAmount(this.amount);
        dto.setDate(this.date);
        dto.setStats(this.stats);
        dto.setType(this.type);
        dto.setPaymentType(this.paymentType);
        dto.setCategory(this.category);
        dto.setRecipient(this.recipient);
        dto.setCreatedAt(this.createdAt);

        dto.setId(this.user.getId());
        return dto;
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
