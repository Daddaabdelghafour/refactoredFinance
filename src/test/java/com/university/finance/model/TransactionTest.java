package com.university.finance.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    
    // Teste la création d'une transaction de type dépôt
    @Test
    public void testDepositTransactionCreation() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        
        assertEquals("T001", transaction.getId());
        assertEquals(Transaction.TransactionType.DEPOSIT, transaction.getType());
        assertEquals(500.0, transaction.getAmount(), 0.01);
        assertEquals(account, transaction.getToAccount());
        assertNull(transaction.getFromAccount());
    }
    
    // Teste la création d'une transaction de type retrait
    @Test
    public void testWithdrawTransactionCreation() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.WITHDRAW, 200.0, account, "Retrait");
        
        assertEquals(Transaction.TransactionType.WITHDRAW, transaction.getType());
        assertEquals(account, transaction.getFromAccount());
        assertNull(transaction.getToAccount());
    }
    
    // Teste la création d'une transaction de type transfert
    @Test
    public void testTransferTransactionCreation() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.TRANSFER, 300.0, fromAccount, toAccount, "Transfert");
        
        assertEquals(Transaction.TransactionType.TRANSFER, transaction.getType());
        assertEquals(fromAccount, transaction.getFromAccount());
        assertEquals(toAccount, transaction.getToAccount());
    }
    
    // Teste la validation d'une transaction de dépôt valide
    @Test
    public void testTransactionValidationDeposit() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        assertTrue(transaction.isValid());
    }
    
    // Teste la validation d'une transaction invalide
    @Test
    public void testTransactionValidationInvalid() {
        Transaction transaction = new Transaction();
        assertFalse(transaction.isValid());
    }
    
    // Teste le formatage d'une transaction pour l'affichage (dépôt)
    @Test
    public void testTransactionFormatForDisplay() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        
        String formatted = transaction.formatForDisplay();
        assertTrue(formatted.contains("DEPOSIT"));
        assertTrue(formatted.contains("500.0"));
        assertTrue(formatted.contains("ACC-12345"));
    }
    
    // Teste l'égalité entre deux transactions
    @Test
    public void testTransactionEquals() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction1 = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        Transaction transaction2 = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        Transaction transaction3 = new Transaction("T002", Transaction.TransactionType.WITHDRAW, 200.0, account, "Retrait");
        
        assertEquals(transaction1, transaction2);
        assertNotEquals(transaction1, transaction3);
    }
    
    // Teste le hash code de deux transactions identiques
    @Test
    public void testTransactionHashCode() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction1 = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        Transaction transaction2 = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }
    
    // Teste les valeurs de l'enum TransactionType
    @Test
    public void testTransactionTypeEnum() {
        assertEquals(Transaction.TransactionType.DEPOSIT, Transaction.TransactionType.valueOf("DEPOSIT"));
        assertEquals(Transaction.TransactionType.WITHDRAW, Transaction.TransactionType.valueOf("WITHDRAW"));
        assertEquals(Transaction.TransactionType.TRANSFER, Transaction.TransactionType.valueOf("TRANSFER"));
        assertEquals(Transaction.TransactionType.VIRIN, Transaction.TransactionType.valueOf("VIRIN"));
        assertEquals(Transaction.TransactionType.VIREST, Transaction.TransactionType.valueOf("VIREST"));
        assertEquals(Transaction.TransactionType.VIRMULTA, Transaction.TransactionType.valueOf("VIRMULTA"));
    }
    
    // Teste les valeurs de l'enum TransactionStatus
    @Test
    public void testTransactionStatusEnum() {
        assertEquals(Transaction.TransactionStatus.PENDING, Transaction.TransactionStatus.valueOf("PENDING"));
        assertEquals(Transaction.TransactionStatus.COMPLETED, Transaction.TransactionStatus.valueOf("COMPLETED"));
        assertEquals(Transaction.TransactionStatus.FAILED, Transaction.TransactionStatus.valueOf("FAILED"));
        assertEquals(Transaction.TransactionStatus.CANCELLED, Transaction.TransactionStatus.valueOf("CANCELLED"));
    }
    
    // Teste les setters de la transaction
    @Test
    public void testTransactionSetters() {
        Transaction transaction = new Transaction();
        transaction.setId("T001");
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setAmount(500.0);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setDescription("Test");
        
        assertEquals("T001", transaction.getId());
        assertEquals(Transaction.TransactionType.DEPOSIT, transaction.getType());
        assertEquals(500.0, transaction.getAmount(), 0.01);
        assertEquals(Transaction.TransactionStatus.COMPLETED, transaction.getStatus());
        assertEquals("Test", transaction.getDescription());
    }
    
    // Teste le constructeur par défaut de Transaction
    @Test
    public void testTransactionDefaultConstructor() {
        Transaction transaction = new Transaction();
        assertNull(transaction.getId());
        assertNull(transaction.getType());
        assertEquals(Transaction.TransactionStatus.PENDING, transaction.getStatus());
        assertNotNull(transaction.getTimestamp());
    }
    
    // Teste la validation d'un dépôt sans compte destination
    @Test
    public void testTransactionValidationDepositMissingToAccount() {
        Transaction transaction = new Transaction();
        transaction.setId("T001");
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setAmount(500.0);
        assertFalse(transaction.isValid());
    }
    
    // Teste la validation d'un retrait sans compte source
    @Test
    public void testTransactionValidationWithdrawMissingFromAccount() {
        Transaction transaction = new Transaction();
        transaction.setId("T001");
        transaction.setType(Transaction.TransactionType.WITHDRAW);
        transaction.setAmount(500.0);
        assertFalse(transaction.isValid());
    }
    
    // Teste la validation d'un transfert sans comptes
    @Test
    public void testTransactionValidationTransferMissingAccounts() {
        Transaction transaction = new Transaction();
        transaction.setId("T001");
        transaction.setType(Transaction.TransactionType.TRANSFER);
        transaction.setAmount(500.0);
        assertFalse(transaction.isValid());
    }
    
    // Teste la validation d'une transaction VIRIN
    @Test
    public void testTransactionValidationVirin() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.VIRIN, 300.0, fromAccount, toAccount, "Virement");
        assertTrue(transaction.isValid());
    }
    
    // Teste la validation d'une transaction VIREST
    @Test
    public void testTransactionValidationVirest() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.VIREST, 200.0, fromAccount, toAccount, "Virement externe");
        assertTrue(transaction.isValid());
    }
    
    // Teste la validation d'une transaction VIRMULTA
    @Test
    public void testTransactionValidationVirmulta() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.VIRMULTA, 100.0, fromAccount, toAccount, "Virement multiple");
        assertTrue(transaction.isValid());
    }
    
    // Teste la validation d'une transaction avec montant invalide
    @Test
    public void testTransactionValidationInvalidAmount() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.DEPOSIT, -100.0, account, "Dépôt");
        assertFalse(transaction.isValid());
    }
    
    // Teste la validation d'une transaction avec ID null
    @Test
    public void testTransactionValidationNullId() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        Transaction transaction = new Transaction();
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setAmount(500.0);
        transaction.setToAccount(account);
        assertFalse(transaction.isValid());
    }
    
    // Teste le formatage d'une transaction de retrait pour l'affichage
    @Test
    public void testTransactionFormatForDisplayWithdraw() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.WITHDRAW, 300.0, account, "Retrait");
        
        String formatted = transaction.formatForDisplay();
        assertTrue(formatted.contains("WITHDRAW"));
        assertTrue(formatted.contains("-300.0"));
        assertTrue(formatted.contains("ACC-12345"));
    }
    
    // Teste le formatage d'une transaction de transfert pour l'affichage
    @Test
    public void testTransactionFormatForDisplayTransfer() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account fromAccount = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account toAccount = new Account("A002", "ACC-67890", user2, Account.AccountType.CHECKING, 500.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.TRANSFER, 300.0, fromAccount, toAccount, "Transfert");
        
        String formatted = transaction.formatForDisplay();
        assertTrue(formatted.contains("TRANSFER"));
        assertTrue(formatted.contains("300.0"));
        assertTrue(formatted.contains("ACC-12345"));
        assertTrue(formatted.contains("ACC-67890"));
    }
    
    // Teste la représentation en chaîne de caractères d'une transaction
    @Test
    public void testTransactionToString() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Transaction transaction = new Transaction("T001", Transaction.TransactionType.DEPOSIT, 500.0, account, "Dépôt");
        
        String str = transaction.toString();
        assertTrue(str.contains("T001"));
        assertTrue(str.contains("DEPOSIT"));
        assertTrue(str.contains("500.0"));
    }
    
    // Teste les setters et getters de la transaction
    @Test
    public void testTransactionSettersGetters() {
        Transaction transaction = new Transaction();
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        transaction.setFromAccount(account);
        transaction.setToAccount(account);
        transaction.setTimestamp(java.time.LocalDateTime.now());
        
        assertEquals(account, transaction.getFromAccount());
        assertEquals(account, transaction.getToAccount());
        assertNotNull(transaction.getTimestamp());
    }
}

