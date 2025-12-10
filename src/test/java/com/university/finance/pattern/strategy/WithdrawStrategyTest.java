package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.exception.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class WithdrawStrategyTest {
    
    // Teste l'exécution d'une stratégie de retrait
    @Test
    public void testWithdrawExecute() throws BusinessException {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        WithdrawStrategy strategy = new WithdrawStrategy();
        Transaction transaction = strategy.execute(account, 300.0, null);
        
        assertNotNull(transaction);
        assertEquals(Transaction.TransactionType.WITHDRAW, transaction.getType());
        assertEquals(700.0, account.getBalance(), 0.01);
    }
    
    // Teste une stratégie de retrait avec fonds insuffisants
    @Test(expected = BusinessException.class)
    public void testWithdrawInsufficientFunds() throws BusinessException {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 100.0);
        
        WithdrawStrategy strategy = new WithdrawStrategy();
        strategy.execute(account, 500.0, null);
    }
}

