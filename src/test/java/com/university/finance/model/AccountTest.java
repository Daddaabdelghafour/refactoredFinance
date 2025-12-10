package com.university.finance.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
    
    // Teste la création d'un compte avec tous ses attributs
    @Test
    public void testAccountCreation() {
        User owner = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", owner, Account.AccountType.CHECKING, 1000.0);
        
        assertEquals("A001", account.getId());
        assertEquals("ACC-12345", account.getAccountNumber());
        assertEquals(owner, account.getOwner());
        assertEquals(Account.AccountType.CHECKING, account.getAccountType());
        assertEquals(1000.0, account.getBalance(), 0.01);
    }
    
    // Teste la vérification du solde suffisant pour une opération
    @Test
    public void testAccountHasSufficientBalance() {
        User owner = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", owner, Account.AccountType.CHECKING, 1000.0);
        
        assertTrue(account.hasSufficientBalance(500.0));
        assertTrue(account.hasSufficientBalance(1000.0));
        assertFalse(account.hasSufficientBalance(1500.0));
        assertFalse(account.hasSufficientBalance(-100.0));
    }
    
    // Teste la validation d'un compte valide
    @Test
    public void testAccountValidation() {
        User owner = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", owner, Account.AccountType.CHECKING, 1000.0);
        
        assertTrue(account.isValid());
    }
    
    // Teste la validation d'un compte invalide
    @Test
    public void testAccountValidationInvalid() {
        Account account = new Account();
        assertFalse(account.isValid());
    }
    
    // Teste l'égalité entre deux comptes
    @Test
    public void testAccountEquals() {
        User user1 = new User("U001", "john", "pwd", "john@example.com");
        User user2 = new User("U002", "jane", "pwd", "jane@example.com");
        Account account1 = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account account2 = new Account("A001", "ACC-12345", user1, Account.AccountType.CHECKING, 1000.0);
        Account account3 = new Account("A002", "ACC-67890", user2, Account.AccountType.SAVINGS, 500.0);
        
        assertEquals(account1, account2);
        assertNotEquals(account1, account3);
    }
    
    // Teste le hash code de deux comptes identiques
    @Test
    public void testAccountHashCode() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account1 = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        Account account2 = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        assertEquals(account1.hashCode(), account2.hashCode());
    }
    
    // Teste la représentation en chaîne de caractères d'un compte
    @Test
    public void testAccountToString() {
        User user = new User("U001", "john", "pwd", "john@example.com");
        Account account = new Account("A001", "ACC-12345", user, Account.AccountType.CHECKING, 1000.0);
        
        String str = account.toString();
        assertTrue(str.contains("A001"));
        assertTrue(str.contains("ACC-12345"));
        assertTrue(str.contains("john"));
    }
    
    // Teste les valeurs de l'enum AccountType
    @Test
    public void testAccountTypeEnum() {
        assertEquals(Account.AccountType.CHECKING, Account.AccountType.valueOf("CHECKING"));
        assertEquals(Account.AccountType.SAVINGS, Account.AccountType.valueOf("SAVINGS"));
        assertEquals(Account.AccountType.BUSINESS, Account.AccountType.valueOf("BUSINESS"));
    }
}

