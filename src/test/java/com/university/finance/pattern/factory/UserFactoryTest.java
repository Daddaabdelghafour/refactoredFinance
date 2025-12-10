package com.university.finance.pattern.factory;

import com.university.finance.model.User;
import com.university.finance.exception.ValidationException;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserFactoryTest {
    
    // Teste la création d'un utilisateur via UserFactory
    @Test
    public void testCreateUser() throws ValidationException {
        User user = UserFactory.createUser("john", "hashed_pwd", "john@example.com");
        
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals("john", user.getUsername());
        assertEquals("hashed_pwd", user.getPasswordHash());
        assertEquals("john@example.com", user.getEmail());
        assertTrue(user.isValid());
    }
    
    // Teste la création d'un utilisateur avec nom d'utilisateur invalide
    @Test(expected = ValidationException.class)
    public void testCreateUserInvalidUsername() throws ValidationException {
        UserFactory.createUser("", "hashed_pwd", "john@example.com");
    }
    
    // Teste la création d'un utilisateur avec email invalide
    @Test(expected = ValidationException.class)
    public void testCreateUserInvalidEmail() throws ValidationException {
        UserFactory.createUser("john", "hashed_pwd", "invalid-email");
    }
    
    // Teste la création d'un utilisateur avec un ID spécifique
    @Test
    public void testCreateUserWithId() throws ValidationException {
        User user = UserFactory.createUserWithId("U001", "john", "hashed_pwd", "john@example.com");
        
        assertEquals("U001", user.getId());
        assertEquals("john", user.getUsername());
        assertTrue(user.isValid());
    }
    
    // Teste la création d'un utilisateur avec ID invalide
    @Test(expected = ValidationException.class)
    public void testCreateUserWithIdInvalidId() throws ValidationException {
        UserFactory.createUserWithId("", "john", "hashed_pwd", "john@example.com");
    }
    
    // Teste que UserFactory supprime les espaces en début/fin des chaînes
    @Test
    public void testCreateUserTrimsWhitespace() throws ValidationException {
        User user = UserFactory.createUser("  john  ", "  pwd  ", "  john@example.com  ");
        
        assertEquals("john", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
    }
    
    // Teste la création d'un utilisateur avec nom d'utilisateur null
    @Test(expected = ValidationException.class)
    public void testCreateUserNullUsername() throws ValidationException {
        UserFactory.createUser(null, "pwd", "john@example.com");
    }
    
    // Teste la création d'un utilisateur avec mot de passe null
    @Test(expected = ValidationException.class)
    public void testCreateUserNullPassword() throws ValidationException {
        UserFactory.createUser("john", null, "john@example.com");
    }
    
    // Teste la création d'un utilisateur avec email null
    @Test(expected = ValidationException.class)
    public void testCreateUserNullEmail() throws ValidationException {
        UserFactory.createUser("john", "pwd", null);
    }
    
    // Teste la création d'un utilisateur avec ID null
    @Test(expected = ValidationException.class)
    public void testCreateUserWithIdNullId() throws ValidationException {
        UserFactory.createUserWithId(null, "john", "pwd", "john@example.com");
    }
    
    // Teste la création d'un utilisateur avec ID vide
    @Test(expected = ValidationException.class)
    public void testCreateUserWithIdEmptyId() throws ValidationException {
        UserFactory.createUserWithId("   ", "john", "pwd", "john@example.com");
    }
}
