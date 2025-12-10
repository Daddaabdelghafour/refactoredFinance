package com.university.finance.exception;

/**
 * Exception levée lorsqu'un utilisateur n'est pas trouvé dans le système.
 * 
 * Cette exception est utilisée lorsqu'une opération tente d'accéder à un utilisateur
 * qui n'existe pas ou qui n'est plus disponible.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class UserNotFoundException extends BusinessException {
    
    private String userId;
    private String username;
    
    /**
     * Constructeur avec identifiant de l'utilisateur.
     * 
     * @param userId Identifiant de l'utilisateur non trouvé
     */
    public UserNotFoundException(String userId) {
        super(String.format("Utilisateur non trouvé avec l'ID: %s", userId), "USER_NOT_FOUND");
        this.userId = userId;
    }
    
    /**
     * Constructeur avec nom d'utilisateur.
     * 
     * @param username Nom d'utilisateur non trouvé
     */
    public UserNotFoundException(String username, boolean byUsername) {
        super(String.format("Utilisateur non trouvé avec le nom: %s", username), "USER_NOT_FOUND");
        this.username = username;
    }
    
    /**
     * Constructeur avec message personnalisé et identifiant.
     * 
     * @param message Message personnalisé
     * @param userId Identifiant de l'utilisateur non trouvé
     */
    public UserNotFoundException(String message, String userId) {
        super(message, "USER_NOT_FOUND");
        this.userId = userId;
    }
    
    /**
     * Récupère l'identifiant de l'utilisateur.
     * 
     * @return L'identifiant de l'utilisateur
     */
    public String getUserId() {
        return userId;
    }
    
    /**
     * Récupère le nom d'utilisateur.
     * 
     * @return Le nom d'utilisateur
     */
    public String getUsername() {
        return username;
    }
}

