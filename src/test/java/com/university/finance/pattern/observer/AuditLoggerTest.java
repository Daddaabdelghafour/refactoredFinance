package com.university.finance.pattern.observer;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuditLoggerTest {
    
    // Teste l'enregistrement d'une transaction complétée dans l'audit
    @Test
    public void testOnTransactionCompleted() {
        AuditLogger logger = new AuditLogger();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        
        logger.onTransactionCompleted(transaction);
        
        assertEquals(1, logger.getLogCount());
        assertFalse(logger.getAuditLog().isEmpty());
    }
    
    // Teste l'enregistrement d'une transaction échouée dans l'audit
    @Test
    public void testOnTransactionFailed() {
        AuditLogger logger = new AuditLogger();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.WITHDRAW, 500.0, account, "Retrait");
        
        logger.onTransactionFailed(transaction, new Exception("Test error"));
        
        assertEquals(1, logger.getLogCount());
    }
}

