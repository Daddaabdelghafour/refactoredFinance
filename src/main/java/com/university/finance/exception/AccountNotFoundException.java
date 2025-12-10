package com.university.finance.exception;

/**
 * Exception levée lorsqu'un compte n'est pas trouvé dans le système.
 * 
 * Cette exception est utilisée lorsqu'une opération tente d'accéder à un compte
 * qui n'existe pas ou qui n'est plus disponible.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class AccountNotFoundException extends BusinessException {
    
    private String accountId;
    private String accountNumber;
    
    /**
     * Constructeur avec identifiant du compte.
     * 
     * @param accountId Identifiant du compte non trouvé
     */
    public AccountNotFoundException(String accountId) {
        super(String.format("Compte non trouvé avec l'ID: %s", accountId), "ACCOUNT_NOT_FOUND");
        this.accountId = accountId;
    }
    
    /**
     * Constructeur avec numéro de compte.
     * 
     * @param accountNumber Numéro de compte non trouvé
     * @param byAccountNumber Indique que la recherche se fait par numéro de compte
     */
    public AccountNotFoundException(String accountNumber, boolean byAccountNumber) {
        super(String.format("Compte non trouvé avec le numéro: %s", accountNumber), "ACCOUNT_NOT_FOUND");
        this.accountNumber = accountNumber;
    }
    
    /**
     * Constructeur avec identifiant et message personnalisé.
     * 
     * @param message Message personnalisé
     * @param accountId Identifiant du compte non trouvé
     */
    public AccountNotFoundException(String message, String accountId) {
        super(message, "ACCOUNT_NOT_FOUND");
        this.accountId = accountId;
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
     * Récupère le numéro de compte.
     * 
     * @return Le numéro de compte
     */
    public String getAccountNumber() {
        return accountNumber;
    }
}

