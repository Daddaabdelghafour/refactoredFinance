package com.university.finance.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe représentant une transaction bancaire.
 * 
 * Cette classe encapsule les informations d'une transaction et respecte
 * le principe de responsabilité unique (SRP) en ne gérant que les données de transaction.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class Transaction {
    private String id;
    private TransactionType type;
    private double amount;
    private Account fromAccount;
    private Account toAccount;
    private LocalDateTime timestamp;
    private String description;
    private TransactionStatus status;
    
    /**
     * Enum représentant les types de transactions.
     */
    public enum TransactionType {
        DEPOSIT,    // Dépôt
        WITHDRAW,   // Retrait
        TRANSFER,   // Transfert
        VIRIN,      // Virement interne (même banque)
        VIREST,     // Virement externe (autre banque)
        VIRMULTA    // Virement multiple (vers plusieurs comptes)
    }
    
    /**
     * Enum représentant le statut d'une transaction.
     */
    public enum TransactionStatus {
        PENDING,    // En attente
        COMPLETED,  // Complétée
        FAILED,     // Échouée
        CANCELLED   // Annulée
    }
    
    /**
     * Constructeur par défaut.
     */
    public Transaction() {
        this.timestamp = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }
    
    /**
     * Constructeur pour dépôt ou retrait (un seul compte).
     * 
     * @param id Identifiant unique de la transaction
     * @param type Type de transaction (DEPOSIT ou WITHDRAW)
     * @param amount Montant de la transaction
     * @param account Compte concerné
     * @param description Description de la transaction
     */
    public Transaction(String id, TransactionType type, double amount, Account account, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        if (type == TransactionType.DEPOSIT) {
            this.toAccount = account;
        } else {
            this.fromAccount = account;
        }
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }
    
    /**
     * Constructeur pour transfert (deux comptes).
     * 
     * @param id Identifiant unique de la transaction
     * @param type Type de transaction (TRANSFER, VIRIN, VIREST, VIRMULTA)
     * @param amount Montant de la transaction
     * @param fromAccount Compte source
     * @param toAccount Compte destination
     * @param description Description de la transaction
     */
    public Transaction(String id, TransactionType type, double amount, 
                      Account fromAccount, Account toAccount, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }
    
    // Getters et Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Account getFromAccount() {
        return fromAccount;
    }
    
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }
    
    public Account getToAccount() {
        return toAccount;
    }
    
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    
    /**
     * Valide les données de la transaction.
     * 
     * @return true si la transaction est valide, false sinon
     */
    public boolean isValid() {
        if (id == null || id.isEmpty() || type == null || amount <= 0) {
            return false;
        }
        
        // Pour DEPOSIT, toAccount doit être présent
        if (type == TransactionType.DEPOSIT) {
            return toAccount != null;
        }
        
        // Pour WITHDRAW, fromAccount doit être présent
        if (type == TransactionType.WITHDRAW) {
            return fromAccount != null;
        }
        
        // Pour TRANSFER et virements, les deux comptes doivent être présents
        if (type == TransactionType.TRANSFER || 
            type == TransactionType.VIRIN || 
            type == TransactionType.VIREST || 
            type == TransactionType.VIRMULTA) {
            return fromAccount != null && toAccount != null;
        }
        
        return false;
    }
    
    /**
     * Formate la transaction pour l'affichage.
     * 
     * @return String formatée de la transaction
     */
    public String formatForDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp).append("] ");
        sb.append(type).append(": ");
        
        if (type == TransactionType.DEPOSIT) {
            sb.append("+").append(amount).append(" vers ").append(toAccount.getAccountNumber());
        } else if (type == TransactionType.WITHDRAW) {
            sb.append("-").append(amount).append(" depuis ").append(fromAccount.getAccountNumber());
        } else {
            sb.append(amount).append(" de ").append(fromAccount.getAccountNumber())
              .append(" vers ").append(toAccount.getAccountNumber());
        }
        
        if (description != null && !description.isEmpty()) {
            sb.append(" - ").append(description);
        }
        
        sb.append(" [").append(status).append("]");
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", fromAccount=" + (fromAccount != null ? fromAccount.getAccountNumber() : "null") +
                ", toAccount=" + (toAccount != null ? toAccount.getAccountNumber() : "null") +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
