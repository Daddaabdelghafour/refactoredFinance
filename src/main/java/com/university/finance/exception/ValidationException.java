package com.university.finance.exception;

/**
 * Exception levée lorsqu'une validation de données échoue.
 * 
 * Cette exception est utilisée pour signaler des erreurs de validation
 * telles que des données manquantes, invalides ou non conformes aux règles métier.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class ValidationException extends BusinessException {
    
    private String fieldName;
    
    /**
     * Constructeur avec message d'erreur.
     * 
     * @param message Message décrivant l'erreur de validation
     */
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR");
    }
    
    /**
     * Constructeur avec message et nom du champ.
     * 
     * @param message Message décrivant l'erreur de validation
     * @param fieldName Nom du champ qui a échoué la validation
     */
    public ValidationException(String message, String fieldName) {
        super(message, "VALIDATION_ERROR");
        this.fieldName = fieldName;
    }
    
    /**
     * Constructeur avec message, nom du champ et cause.
     * 
     * @param message Message décrivant l'erreur de validation
     * @param fieldName Nom du champ qui a échoué la validation
     * @param cause Exception à l'origine de cette exception
     */
    public ValidationException(String message, String fieldName, Throwable cause) {
        super(message, "VALIDATION_ERROR", cause);
        this.fieldName = fieldName;
    }
    
    /**
     * Récupère le nom du champ qui a échoué la validation.
     * 
     * @return Le nom du champ
     */
    public String getFieldName() {
        return fieldName;
    }
    
    /**
     * Définit le nom du champ qui a échoué la validation.
     * 
     * @param fieldName Le nom du champ
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (fieldName != null) {
            sb.append(" (Field: ").append(fieldName).append(")");
        }
        return sb.toString();
    }
}

