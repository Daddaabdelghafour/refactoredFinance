package com.university.finance.exception;

/**
 * Exception de base pour toutes les exceptions métier du système bancaire.
 * 
 * Cette classe sert de classe parente pour toutes les exceptions spécifiques
 * au domaine bancaire, permettant une gestion centralisée des erreurs métier.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class BusinessException extends Exception {
    
    private String errorCode;
    
    /**
     * Constructeur par défaut.
     */
    public BusinessException() {
        super();
    }
    
    /**
     * Constructeur avec message d'erreur.
     * 
     * @param message Message décrivant l'erreur
     */
    public BusinessException(String message) {
        super(message);
    }
    
    /**
     * Constructeur avec message et code d'erreur.
     * 
     * @param message Message décrivant l'erreur
     * @param errorCode Code d'erreur pour identification
     */
    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * Constructeur avec message et cause.
     * 
     * @param message Message décrivant l'erreur
     * @param cause Exception à l'origine de cette exception
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructeur avec message, code d'erreur et cause.
     * 
     * @param message Message décrivant l'erreur
     * @param errorCode Code d'erreur pour identification
     * @param cause Exception à l'origine de cette exception
     */
    public BusinessException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    /**
     * Récupère le code d'erreur.
     * 
     * @return Le code d'erreur
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Définit le code d'erreur.
     * 
     * @param errorCode Le code d'erreur à définir
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        if (errorCode != null) {
            sb.append(" [").append(errorCode).append("]");
        }
        if (getMessage() != null) {
            sb.append(": ").append(getMessage());
        }
        return sb.toString();
    }
}

