package com.artTech.wisewallet.dto;

import com.artTech.wisewallet.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("description")
    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    @JsonProperty("recipient")
    @NotBlank(message = "O destinatário é obrigatório")
    private String recipient;

    @JsonProperty("category")
    @NotBlank(message = "A categoria é obrigatória")
    private String category;

    @JsonProperty("paymentType")
    @NotNull(message = "O tipo de pagamento é obrigatório")
    private Transaction.TransactionPaymentType paymentType;

    @JsonProperty("type")
    @NotNull(message = "O tipo é obrigatório")
    private Transaction.TransactionType type;

    @JsonProperty("stats")
    @NotNull(message = "O status é obrigatório")
    private Transaction.TransactionStats stats;

    @JsonProperty("date")
    @NotNull(message = "A data é obrigatória")
    private LocalDate date;

    @JsonProperty("amount")
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal amount;

    public TransactionDTO() {
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                ", recipient='" + recipient + '\'' +
                ", category='" + category + '\'' +
                ", paymentType=" + paymentType +
                ", type=" + type +
                ", stats=" + stats +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }


    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.recipient = transaction.getRecipient();
        this.category = transaction.getCategory();
        this.paymentType = transaction.getPaymentType();
        this.type = transaction.getType();
        this.stats = transaction.getStats();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
    }

    public Long getId() {return id;}

    public void setId(Long id) {  this.id = id; }

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