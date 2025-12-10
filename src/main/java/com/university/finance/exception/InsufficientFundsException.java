package com.university.finance.exception;

/**
 * Exception levée lorsqu'un compte n'a pas suffisamment de fonds pour effectuer une opération.
 * 
 * Cette exception est utilisée lors des tentatives de retrait ou de transfert
 * lorsque le solde du compte est insuffisant.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class InsufficientFundsException extends BusinessException {
    
    private String accountId;
    private double currentBalance;
    private double requestedAmount;
    
    /**
     * Constructeur avec message d'erreur.
     * 
     * @param message Message décrivant l'erreur
     */
    public InsufficientFundsException(String message) {
        super(message, "INSUFFICIENT_FUNDS");
    }
    
    /**
     * Constructeur avec détails du compte.
     * 
     * @param accountId Identifiant du compte
     * @param currentBalance Solde actuel du compte
     * @param requestedAmount Montant demandé
     */
    public InsufficientFundsException(String accountId, double currentBalance, double requestedAmount) {
        super(String.format(
            "Solde insuffisant pour le compte %s. Solde actuel: %.2f, Montant demandé: %.2f",
            accountId, currentBalance, requestedAmount
        ), "INSUFFICIENT_FUNDS");
        this.accountId = accountId;
        this.currentBalance = currentBalance;
        this.requestedAmount = requestedAmount;
    }
    
    /**
     * Constructeur avec message personnalisé et détails.
     * 
     * @param message Message personnalisé
     * @param accountId Identifiant du compte
     * @param currentBalance Solde actuel du compte
     * @param requestedAmount Montant demandé
     */
    public InsufficientFundsException(String message, String accountId, 
                                      double currentBalance, double requestedAmount) {
        super(message, "INSUFFICIENT_FUNDS");
        this.accountId = accountId;
        this.currentBalance = currentBalance;
        this.requestedAmount = requestedAmount;
    }
    
    /**
     * Récupère l'identifiant du compte.
     * 
     * @return L'identifiant du compte
     */
    public String getAccountId() {
        return accountId;
    }
    
    /**
     * Récupère le solde actuel du compte.
     * 
     * @return Le solde actuel
     */
    public double getCurrentBalance() {
        return currentBalance;
    }
    
    /**
     * Récupère le montant demandé.
     * 
     * @return Le montant demandé
     */
    public double getRequestedAmount() {
        return requestedAmount;
    }
    
    /**
     * Calcule le montant manquant.
     * 
     * @return Le montant manquant pour effectuer l'opération
     */
    public double getMissingAmount() {
        return requestedAmount - currentBalance;
    }
}

