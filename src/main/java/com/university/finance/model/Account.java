package com.university.finance.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe représentant un compte bancaire.
 * 
 * Cette classe encapsule les informations d'un compte et respecte
 * le principe de responsabilité unique (SRP) en ne gérant que les données de compte.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class Account {
    private String id;
    private String accountNumber;
    private double balance;
    private User owner;
    private AccountType accountType;
    private LocalDateTime createdAt;
    
    /**
     * Enum représentant les types de comptes disponibles.
     */
    public enum AccountType {
        CHECKING,   // Compte courant
        SAVINGS,    // Compte épargne
        BUSINESS    // Compte professionnel
    }
    
    /**
     * Constructeur par défaut.
     */
    public Account() {
        this.balance = 0.0;
        this.createdAt = LocalDateTime.now();
    }
    
    /**
     * Constructeur avec paramètres essentiels.
     * 
     * @param id Identifiant unique du compte
     * @param accountNumber Numéro de compte
     * @param owner Propriétaire du compte
     * @param accountType Type de compte
     */
    public Account(String id, String accountNumber, User owner, AccountType accountType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.accountType = accountType;
        this.balance = 0.0;
        this.createdAt = LocalDateTime.now();
    }
    
    /**
     * Constructeur avec solde initial.
     * 
     * @param id Identifiant unique du compte
     * @param accountNumber Numéro de compte
     * @param owner Propriétaire du compte
     * @param accountType Type de compte
     * @param initialBalance Solde initial
     */
    public Account(String id, String accountNumber, User owner, AccountType accountType, double initialBalance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.accountType = accountType;
        this.balance = initialBalance;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters et Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public AccountType getAccountType() {
        return accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Vérifie si le compte a un solde suffisant pour une opération.
     * 
     * @param amount Montant à vérifier
     * @return true si le solde est suffisant, false sinon
     */
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount && amount > 0;
    }
    
    /**
     * Valide les données du compte.
     * 
     * @return true si le compte est valide, false sinon
     */
    public boolean isValid() {
        return id != null && !id.isEmpty() &&
               accountNumber != null && !accountNumber.isEmpty() &&
               owner != null &&
               accountType != null &&
               balance >= 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
               Objects.equals(accountNumber, account.accountNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber);
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", owner=" + (owner != null ? owner.getUsername() : "null") +
                ", accountType=" + accountType +
                ", createdAt=" + createdAt +
                '}';
    }
}
