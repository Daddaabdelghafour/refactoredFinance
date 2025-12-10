package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.exception.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepositStrategyTest {
    
    // Teste l'exécution d'une stratégie de dépôt
    @Test
    public void testDepositExecute() throws BusinessException {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        DepositStrategy strategy = new DepositStrategy();
        Transaction transaction = strategy.execute(account, 500.0, null);
        
        assertNotNull(transaction);
        assertEquals(Transaction.TransactionType.DEPOSIT, transaction.getType());
        assertEquals(500.0, transaction.getAmount(), 0.01);
        assertEquals(1500.0, account.getBalance(), 0.01);
    }
    
    // Teste la validation d'une stratégie de dépôt
    @Test
    public void testDepositValidate() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        DepositStrategy strategy = new DepositStrategy();
        assertTrue(strategy.validate(account, 500.0, null));
        assertFalse(strategy.validate(null, 500.0, null));
        assertFalse(strategy.validate(account, -100.0, null));
    }
    
    // Teste une stratégie de dépôt avec compte invalide
    @Test(expected = BusinessException.class)
    public void testDepositInvalidAccount() throws BusinessException {
        DepositStrategy strategy = new DepositStrategy();
        strategy.execute(null, 500.0, null);
    }
}

