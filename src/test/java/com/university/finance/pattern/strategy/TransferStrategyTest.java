package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.exception.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransferStrategyTest {
    
    // Teste l'exécution d'une stratégie de transfert
    @Test
    public void testTransferExecute() throws BusinessException {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        TransferStrategy strategy = new TransferStrategy();
        Transaction transaction = strategy.execute(fromAccount, 300.0, toAccount);
        
        assertNotNull(transaction);
        assertEquals(Transaction.TransactionType.TRANSFER, transaction.getType());
        assertEquals(700.0, fromAccount.getBalance(), 0.01);
        assertEquals(800.0, toAccount.getBalance(), 0.01);
    }
    
    // Teste la validation d'une stratégie de transfert
    @Test
    public void testTransferValidate() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        TransferStrategy strategy = new TransferStrategy();
        assertTrue(strategy.validate(fromAccount, 300.0, toAccount));
        assertFalse(strategy.validate(null, 300.0, toAccount));
        assertFalse(strategy.validate(fromAccount, 300.0, fromAccount)); // Même compte
    }
}

