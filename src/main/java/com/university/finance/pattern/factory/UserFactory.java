package com.university.finance.pattern.factory;

import com.university.finance.model.User;
import com.university.finance.exception.ValidationException;
import java.util.UUID;

/**
 * Factory pour la création d'objets User.
 * 
 * Pattern Factory : Centralise la création d'objets User avec validation
 * et initialisation cohérente. Permet de garantir que tous les utilisateurs
 * créés sont valides et correctement initialisés.
 * 
 * Ce pattern respecte le principe Single Responsibility (SRP) en séparant
 * la logique de création de la logique métier.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class UserFactory {
    
    /**
     * Crée un nouvel utilisateur avec les paramètres fournis.
     * 
     * @param username Nom d'utilisateur
     * @param passwordHash Mot de passe hashé
     * @param email Adresse email
     * @return Un nouvel utilisateur valide
     * @throws ValidationException Si les données fournies sont invalides
     */
    public static User createUser(String username, String passwordHash, String email) throws ValidationException {
        // Validation des paramètres
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Le nom d'utilisateur ne peut pas être vide", "username");
        }
        
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new ValidationException("Le mot de passe ne peut pas être vide", "passwordHash");
        }
        
        if (email == null || !email.contains("@")) {
            throw new ValidationException("L'email doit être valide", "email");
        }
        
        // Génération d'un ID unique
        String userId = UUID.randomUUID().toString();
        
        // Création de l'utilisateur
        User user = new User(userId, username.trim(), passwordHash, email.trim().toLowerCase());
        
        // Vérification finale de validité
        if (!user.isValid()) {
            throw new ValidationException("Les données de l'utilisateur sont invalides");
        }
        
        return user;
    }
    
    /**
     * Crée un nouvel utilisateur avec un ID spécifique.
     * 
     * @param userId Identifiant unique de l'utilisateur
     * @param username Nom d'utilisateur
     * @param passwordHash Mot de passe hashé
     * @param email Adresse email
     * @return Un nouvel utilisateur valide
     * @throws ValidationException Si les données fournies sont invalides
     */
    public static User createUserWithId(String userId, String username, String passwordHash, String email) 
            throws ValidationException {
        // Validation des paramètres
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException("L'ID utilisateur ne peut pas être vide", "userId");
        }
        
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Le nom d'utilisateur ne peut pas être vide", "username");
        }
        
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new ValidationException("Le mot de passe ne peut pas être vide", "passwordHash");
        }
        
        if (email == null || !email.contains("@")) {
            throw new ValidationException("L'email doit être valide", "email");
        }
        
        // Création de l'utilisateur avec l'ID fourni
        User user = new User(userId.trim(), username.trim(), passwordHash, email.trim().toLowerCase());
        
        // Vérification finale de validité
        if (!user.isValid()) {
            throw new ValidationException("Les données de l'utilisateur sont invalides");
        }
        
        return user;
    }
}
