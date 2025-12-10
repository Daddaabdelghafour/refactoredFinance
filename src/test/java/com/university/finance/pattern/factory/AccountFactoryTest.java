package com.university.finance.pattern.factory;

import com.university.finance.model.Account;
import com.university.finance.model.User;
import com.university.finance.exception.ValidationException;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountFactoryTest {
    
    // Teste la création d'un compte via AccountFactory
    @Test
    public void testCreateAccount() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        Account account = AccountFactory.createAccount(owner, Account.AccountType.CHECKING, 1000.0);
        
        assertNotNull(account);
        assertNotNull(account.getId());
        assertNotNull(account.getAccountNumber());
        assertEquals(owner, account.getOwner());
        assertEquals(Account.AccountType.CHECKING, account.getAccountType());
        assertEquals(1000.0, account.getBalance(), 0.01);
        assertTrue(account.isValid());
    }
    
    // Teste la création d'un compte avec solde zéro
    @Test
    public void testCreateAccountWithZeroBalance() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        Account account = AccountFactory.createAccount(owner, Account.AccountType.SAVINGS);
        
        assertEquals(0.0, account.getBalance(), 0.01);
    }
    
    // Teste la création d'un compte avec propriétaire null
    @Test(expected = ValidationException.class)
    public void testCreateAccountInvalidOwner() throws ValidationException {
        AccountFactory.createAccount(null, Account.AccountType.CHECKING, 1000.0);
    }
    
    // Teste la création d'un compte avec solde négatif
    @Test(expected = ValidationException.class)
    public void testCreateAccountNegativeBalance() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        AccountFactory.createAccount(owner, Account.AccountType.CHECKING, -100.0);
    }
    
    // Teste la création d'un compte avec détails spécifiques
    @Test
    public void testCreateAccountWithDetails() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        Account account = AccountFactory.createAccountWithDetails("A001", "ACC-12345", owner, Account.AccountType.BUSINESS, 2000.0);
        
        assertEquals("A001", account.getId());
        assertEquals("ACC-12345", account.getAccountNumber());
        assertEquals(Account.AccountType.BUSINESS, account.getAccountType());
        assertEquals(2000.0, account.getBalance(), 0.01);
    }
    
    // Teste la création d'un compte avec ID invalide
    @Test(expected = ValidationException.class)
    public void testCreateAccountWithDetailsInvalidId() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        AccountFactory.createAccountWithDetails("", "ACC-12345", owner, Account.AccountType.CHECKING, 1000.0);
    }
    
    // Teste la création de comptes de différents types
    @Test
    public void testCreateAccountDifferentTypes() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        
        Account checking = AccountFactory.createAccount(owner, Account.AccountType.CHECKING, 1000.0);
        Account savings = AccountFactory.createAccount(owner, Account.AccountType.SAVINGS, 500.0);
        Account business = AccountFactory.createAccount(owner, Account.AccountType.BUSINESS, 2000.0);
        
        assertEquals(Account.AccountType.CHECKING, checking.getAccountType());
        assertEquals(Account.AccountType.SAVINGS, savings.getAccountType());
        assertEquals(Account.AccountType.BUSINESS, business.getAccountType());
    }
    
    // Teste la création d'un compte avec propriétaire invalide
    @Test(expected = ValidationException.class)
    public void testCreateAccountInvalidOwnerData() throws ValidationException {
        User invalidOwner = new User();
        invalidOwner.setUsername("john");
        AccountFactory.createAccount(invalidOwner, Account.AccountType.CHECKING, 1000.0);
    }
    
    // Teste la création d'un compte avec type null
    @Test(expected = ValidationException.class)
    public void testCreateAccountNullAccountType() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        AccountFactory.createAccount(owner, null, 1000.0);
    }
    
    // Teste la création d'un compte avec détails et type null
    @Test(expected = ValidationException.class)
    public void testCreateAccountWithDetailsNullAccountType() throws ValidationException {
        User owner = UserFactory.createUser("john", "pwd", "john@example.com");
        AccountFactory.createAccountWithDetails("A001", "ACC-12345", owner, null, 1000.0);
    }
    
    // Teste la création d'un compte avec détails et propriétaire invalide
    @Test(expected = ValidationException.class)
    public void testCreateAccountWithDetailsInvalidOwner() throws ValidationException {
        User invalidOwner = new User();
        AccountFactory.createAccountWithDetails("A001", "ACC-12345", invalidOwner, Account.AccountType.CHECKING, 1000.0);
    }
}

