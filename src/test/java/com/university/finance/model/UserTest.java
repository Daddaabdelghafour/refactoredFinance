package com.university.finance.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    // Teste la création d'un utilisateur avec tous ses attributs
    @Test
    public void testUserCreation() {
        User user = new User("U001", "john", "hashed_pwd", "john@example.com");
        
        assertEquals("U001", user.getId());
        assertEquals("john", user.getUsername());
        assertEquals("hashed_pwd", user.getPasswordHash());
        assertEquals("john@example.com", user.getEmail());
        assertNotNull(user.getCreatedAt());
    }
    
    // Teste la validation d'un utilisateur valide
    @Test
    public void testUserValidationValid() {
        User user = new User("U001", "john", "hashed_pwd", "john@example.com");
        assertTrue(user.isValid());
    }
    
    // Teste la validation d'un utilisateur avec ID invalide
    @Test
    public void testUserValidationInvalidId() {
        User user = new User();
        user.setUsername("john");
        user.setPasswordHash("hashed_pwd");
        user.setEmail("john@example.com");
        assertFalse(user.isValid());
    }
    
    // Teste la validation d'un utilisateur avec email invalide
    @Test
    public void testUserValidationInvalidEmail() {
        User user = new User("U001", "john", "hashed_pwd", "invalid-email");
        assertFalse(user.isValid());
    }
    
    // Teste l'égalité entre deux utilisateurs
    @Test
    public void testUserEquals() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U001", "john", "pwd", "john@example.com");
        User user3 = new User("U002", "jane", "pwd", "jane@example.com");
        
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }
    
    // Teste le hash code de deux utilisateurs identiques
    @Test
    public void testUserHashCode() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U001", "john", "pwd", "john@example.com");
        
        assertEquals(user1.hashCode(), user2.hashCode());
    }
    
    // Teste la représentation en chaîne de caractères d'un utilisateur
    @Test
    public void testUserToString() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        
        String str = user.toString();
        assertTrue(str.contains("U001"));
        assertTrue(str.contains("john"));
        assertTrue(str.contains("john@example.com"));
    }
    
    // Teste les setters de l'utilisateur
    @Test
    public void testUserSetters() {
        User user = new User();
        user.setId("U001");
        user.setUsername("john");
        user.setPasswordHash("pwd");
        user.setEmail("john@example.com");
        
        assertEquals("U001", user.getId());
        assertEquals("john", user.getUsername());
        assertEquals("pwd", user.getPasswordHash());
        assertEquals("john@example.com", user.getEmail());
    }
}

