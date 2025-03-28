package com.artTech.wisewallet.dto;

import com.artTech.wisewallet.model.Category;
import com.artTech.wisewallet.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private Long id;
    private String description;
    private Integer code;
    private String recipient;
    private Long categoryId; // ID da categoria
    private String categoryName; // Nome da categoria
    private Transaction.TransactionPaymentType paymentType;
    private Transaction.TransactionStats stats;
    private Transaction.TransactionType type;
    private BigDecimal amount;
    private LocalDate date;
    private UserResponseDTO user;
    private LocalDateTime createdAt;
    private Category category;

    // Getters e Setters
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}