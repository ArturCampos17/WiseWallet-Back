package com.artTech.wisewallet.dto;

import com.artTech.wisewallet.model.Transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @NotBlank(message = "O destinatário é obrigatório")
    private String recipient;

    @NotBlank(message = "A categoria é obrigatória")
    private String category;

    @NotNull(message = "O tipo é obrigatório")
    private Transaction.TransactionType type;

    @NotNull(message = "O status é obrigatório")
    private Transaction.TransactionStats stats;

    @NotNull(message = "A data é obrigatória")
    private LocalDate date;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal amount;

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

    public Transaction.TransactionType getType() {
        return type;
    }

    public void setType(Transaction.TransactionType type) {
        this.type = type;
    }

    public Transaction.TransactionStats getStats() {
        return stats;
    }

    public void setStats(Transaction.TransactionStats stats) {
        this.stats = stats;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}