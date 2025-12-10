package com.university.finance.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe représentant un utilisateur du système bancaire.
 * 
 * Cette classe encapsule les informations d'un utilisateur et respecte
 * le principe de responsabilité unique (SRP) en ne gérant que les données utilisateur.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class User {
    private String id;
    private String username;
    private String passwordHash; // Mot de passe hashé pour la sécurité
    private String email;
    private LocalDateTime createdAt;
    
    /**
     * Constructeur par défaut.
     */
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    /**
     * Constructeur avec paramètres essentiels.
     * 
     * @param id Identifiant unique de l'utilisateur
     * @param username Nom d'utilisateur
     * @param passwordHash Mot de passe hashé
     * @param email Adresse email
     */
    public User(String id, String username, String passwordHash, String email) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters et Setters
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Valide les données de l'utilisateur.
     * 
     * @return true si l'utilisateur est valide, false sinon
     */
    public boolean isValid() {
        return id != null && !id.isEmpty() &&
               username != null && !username.isEmpty() &&
               passwordHash != null && !passwordHash.isEmpty() &&
               email != null && email.contains("@");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(username, user.username);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
