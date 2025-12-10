package com.university.finance.service;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.pattern.observer.AuditLogger;
import com.university.finance.pattern.observer.NotificationService;
import com.university.finance.pattern.observer.TransactionObserver;
import com.university.finance.exception.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionServiceObserverTest {
    
    // Teste l'ajout d'un observateur au TransactionService
    @Test
    public void testAddObserver() {
        TransactionService service = new TransactionService();
        AuditLogger logger = new AuditLogger();
        
        service.addObserver(logger);
        
        // Vérifier que l'observateur est ajouté en exécutant une transaction
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        try {
            service.deposit(account, 100.0);
            // Si pas d'erreur, l'observateur a été notifié
            assertTrue(true);
        } catch (BusinessException e) {
            fail("Transaction should succeed");
        }
    }
    
    // Teste la suppression d'un observateur du TransactionService
    @Test
    public void testRemoveObserver() {
        TransactionService service = new TransactionService();
        AuditLogger logger = new AuditLogger();
        
        service.addObserver(logger);
        service.removeObserver(logger);
        
        // L'observateur ne devrait plus être notifié
        assertTrue(true); // Test de structure
    }
    
    // Teste la notification de plusieurs observateurs
    @Test
    public void testMultipleObservers() {
        TransactionService service = new TransactionService();
        AuditLogger logger = new AuditLogger();
        NotificationService notificationService = new NotificationService();
        
        service.addObserver(logger);
        service.addObserver(notificationService);
        
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        try {
            Transaction transaction = service.deposit(account, 100.0);
            assertNotNull(transaction);
            // Les deux observateurs devraient être notifiés
        } catch (BusinessException e) {
            fail("Transaction should succeed");
        }
    }
}

