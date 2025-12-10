package com.university.finance.service;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.exception.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionServiceTest {
    
    // Teste l'exécution d'un dépôt
    @Test
    public void testDeposit() throws BusinessException {
        TransactionService service = new TransactionService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction transaction = service.deposit(account, 500.0);
        
        assertNotNull(transaction);
        assertEquals(1500.0, account.getBalance(), 0.01);
    }
    
    // Teste l'exécution d'un retrait
    @Test
    public void testWithdraw() throws BusinessException {
        TransactionService service = new TransactionService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction transaction = service.withdraw(account, 300.0);
        
        assertNotNull(transaction);
        assertEquals(700.0, account.getBalance(), 0.01);
    }
    
    // Teste un retrait avec fonds insuffisants
    @Test(expected = BusinessException.class)
    public void testWithdrawInsufficientFunds() throws BusinessException {
        TransactionService service = new TransactionService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 100.0);
        
        service.withdraw(account, 500.0);
    }
    
    // Teste l'exécution d'un transfert
    @Test
    public void testTransfer() throws BusinessException {
        TransactionService service = new TransactionService();
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        Transaction transaction = service.transfer(fromAccount, toAccount, 300.0);
        
        assertNotNull(transaction);
        assertEquals(700.0, fromAccount.getBalance(), 0.01);
        assertEquals(800.0, toAccount.getBalance(), 0.01);
    }
    
    // Teste la récupération de l'historique des transactions d'un compte
    @Test
    public void testGetTransactionHistory() throws BusinessException {
        TransactionService service = new TransactionService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        service.deposit(account, 500.0);
        service.withdraw(account, 200.0);
        
        assertEquals(2, service.getTransactionHistory(account).size());
    }
    
    // Teste l'exécution d'un virement interne (VIRIN)
    @Test
    public void testVirementInterne() throws BusinessException {
        TransactionService service = new TransactionService();
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        Transaction transaction = service.virementInterne(fromAccount, toAccount, 300.0);
        
        assertEquals(Transaction.TransactionType.VIRIN, transaction.getType());
        assertEquals(700.0, fromAccount.getBalance(), 0.01);
        assertEquals(800.0, toAccount.getBalance(), 0.01);
    }
    
    // Teste l'exécution d'un virement externe (VIREST)
    @Test
    public void testVirementExterne() throws BusinessException {
        TransactionService service = new TransactionService();
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        Transaction transaction = service.virementExterne(fromAccount, toAccount, 200.0);
        
        assertEquals(Transaction.TransactionType.VIREST, transaction.getType());
    }
    
    // Teste la récupération d'une transaction par son ID
    @Test
    public void testGetTransactionById() throws BusinessException {
        TransactionService service = new TransactionService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction created = service.deposit(account, 500.0);
        Transaction retrieved = service.getTransactionById(created.getId());
        
        assertEquals(created, retrieved);
    }
    
    // Teste la récupération d'une transaction inexistante par ID
    @Test
    public void testGetTransactionByIdNotFound() {
        TransactionService service = new TransactionService();
        Transaction transaction = service.getTransactionById("NONEXISTENT");
        
        assertNull(transaction);
    }
    
    // Teste la récupération de toutes les transactions
    @Test
    public void testGetAllTransactions() throws BusinessException {
        TransactionService service = new TransactionService();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        service.deposit(account, 100.0);
        service.deposit(account, 200.0);
        service.withdraw(account, 50.0);
        
        assertEquals(3, service.getAllTransactions().size());
    }
}

