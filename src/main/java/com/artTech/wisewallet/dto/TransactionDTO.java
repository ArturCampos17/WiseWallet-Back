//package com.artTech.wisewallet.dto;
//
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;
//
//public class TransactionDTO {
//
//    @NotNull(message = "O valor é obrigatório")
//    @Positive(message = "O valor deve ser positivo")
//    private Double amount;
//
//    @NotNull(message = "A descrição é obrigatória")
//    private String description;
//
//    @NotNull(message = "O ID do remetente é obrigatório")
//    private Long senderId;
//
//    @NotNull(message = "O destinatário é obrigatório")
//    private String recipient;  // Agora é um campo String (não um ID)
//
//    // Getters e Setters
//    public Double getAmount() { return amount; }
//    public void setAmount(Double amount) { this.amount = amount; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public Long getSenderId() { return senderId; }
//    public void setSenderId(Long senderId) { this.senderId = senderId; }
//
//    public @NotNull(message = "O destinatário é obrigatório") String getRecipient() { return recipient; }
//    public void setRecipient(String recipient) { this.recipient = recipient; }
//}