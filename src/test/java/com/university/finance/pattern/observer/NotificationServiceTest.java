package com.university.finance.pattern.observer;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotificationServiceTest {
    
    // Teste l'envoi de notification pour une transaction complétée
    @Test
    public void testOnTransactionCompleted() {
        NotificationService service = new NotificationService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        
        service.onTransactionCompleted(transaction);
        
        assertEquals(1, service.getNotifications(account.getAccountNumber()).size());
    }
    
    // Teste l'envoi de notification pour une transaction échouée
    @Test
    public void testOnTransactionFailed() {
        NotificationService service = new NotificationService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.WITHDRAW, 500.0, account, "Retrait");
        
        service.onTransactionFailed(transaction, new Exception("Test error"));
        
        assertFalse(service.getNotifications(account.getAccountNumber()).isEmpty());
    }
    
    // Teste l'activation/désactivation des notifications email
    @Test
    public void testEmailNotificationsEnabled() {
        NotificationService service = new NotificationService(true);
        assertTrue(service.isEmailNotificationsEnabled());
        
        service.setEmailNotificationsEnabled(false);
        assertFalse(service.isEmailNotificationsEnabled());
    }
}

