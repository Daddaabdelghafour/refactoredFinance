package com.university.finance.exception;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccountNotFoundExceptionTest {
    
    // Teste AccountNotFoundException avec un ID de compte
    @Test
    public void testAccountNotFoundExceptionWithId() {
        AccountNotFoundException exception = new AccountNotFoundException("A001");
        
        assertEquals("A001", exception.getAccountId());
        assertTrue(exception.getMessage().contains("A001"));
    }
    
    // Teste AccountNotFoundException avec un numéro de compte
    @Test
    public void testAccountNotFoundExceptionWithNumber() {
        AccountNotFoundException exception = new AccountNotFoundException("ACC-12345", true);
        
        assertEquals("ACC-12345", exception.getAccountNumber());
        assertTrue(exception.getMessage().contains("ACC-12345"));
    }
}

