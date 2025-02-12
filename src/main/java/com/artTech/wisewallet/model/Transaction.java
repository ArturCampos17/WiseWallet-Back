//package com.artTech.wisewallet.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "transactions")
//public class Transaction {
//
//    public enum TransactionType {
//        CREDIT, DEBIT, PIX
//    }
//
//    public enum TransactionStatus {
//        PAID, PENDING, CANCELED
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User payer; // "pagador" -> "payer"
//
//    private String recipient; // "recebedor" -> "recipient"
//    private String category; // "categoria" -> "category"
//
//    @Enumerated(EnumType.STRING)
//    private TransactionType type; // "tipo" -> "type"
//
//    @Enumerated(EnumType.STRING)
//    private TransactionStatus status; // "situacao" -> "status"
//
//    private String description; // "descricao" -> "description"
//
//    private BigDecimal amount; // "valor" -> "amount"
//
//    private LocalDate createdAt;
//
//    private LocalDate date; // "data" -> "date"
//
//    public Transaction() {
//        this.date = LocalDate.now();
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public User getPayer() {
//        return payer;
//    }
//
//    public void setPayer(User payer) {
//        this.payer = payer;
//    }
//
//    public String getRecipient() {
//        return recipient;
//    }
//
//    public void setRecipient(String recipient) {
//        this.recipient = recipient;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public TransactionType getType() {
//        return type;
//    }
//
//    public void setType(TransactionType type) {
//        this.type = type;
//    }
//
//    public TransactionStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(TransactionStatus status) {
//        this.status = status;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
