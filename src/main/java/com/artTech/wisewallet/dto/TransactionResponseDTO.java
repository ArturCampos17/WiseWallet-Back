package com.artTech.wisewallet.dto;

import com.artTech.wisewallet.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private Long id;
    private String description;
    private String recipient;
    private String category;
    private Transaction.TransactionPaymentType paymentType;
    private Transaction.TransactionStats stats;
    private Transaction.TransactionType type;
    private BigDecimal amount;
    private LocalDate date;
    private UserResponseDTO user;
    private LocalDateTime createdAt;


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

    public Transaction.TransactionPaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Transaction.TransactionPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Transaction.TransactionStats getStats() {
        return stats;
    }

    public void setStats(Transaction.TransactionStats stats) {
        this.stats = stats;
    }

    public Transaction.TransactionType getType() {
        return type;
    }

    public void setType(Transaction.TransactionType type) {
        this.type = type;
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

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}