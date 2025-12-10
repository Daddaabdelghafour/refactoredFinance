package com.university.finance.exception;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserNotFoundExceptionTest {
    
    // Teste UserNotFoundException avec un ID d'utilisateur
    @Test
    public void testUserNotFoundExceptionWithId() {
        UserNotFoundException exception = new UserNotFoundException("U001");
        
        assertEquals("U001", exception.getUserId());
        assertTrue(exception.getMessage().contains("U001"));
    }
    
    // Teste UserNotFoundException avec un nom d'utilisateur
    @Test
    public void testUserNotFoundExceptionWithUsername() {
        UserNotFoundException exception = new UserNotFoundException("john", true);
        
        assertEquals("john", exception.getUsername());
        assertTrue(exception.getMessage().contains("john"));
    }
}

