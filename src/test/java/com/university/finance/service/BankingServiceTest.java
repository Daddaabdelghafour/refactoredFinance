package com.university.finance.service;

import com.university.finance.model.Account;
import com.university.finance.model.User;
import com.university.finance.exception.BusinessException;
import com.university.finance.exception.AccountNotFoundException;
import com.university.finance.exception.UserNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankingServiceTest {
    
    // Teste la création d'un utilisateur via BankingService
    @Test
    public void testCreateUser() throws BusinessException {
        BankingService service = new BankingService();
        User user = service.createUser("john", "hashed_pwd", "john@example.com");
        
        assertNotNull(user);
        assertEquals("john", user.getUsername());
    }
    
    // Teste la récupération d'un utilisateur par son ID
    @Test
    public void testGetUserById() throws BusinessException {
        BankingService service = new BankingService();
        User created = service.createUser("john", "pwd", "john@example.com");
        User retrieved = service.getUserById(created.getId());
        
        assertEquals(created, retrieved);
    }
    
    // Teste la récupération d'un utilisateur inexistant par ID
    @Test(expected = UserNotFoundException.class)
    public void testGetUserByIdNotFound() throws UserNotFoundException {
        BankingService service = new BankingService();
        service.getUserById("NONEXISTENT");
    }
    
    // Teste la création d'un compte via BankingService
    @Test
    public void testCreateAccount() throws BusinessException {
        BankingService service = new BankingService();
        User user = service.createUser("john", "pwd", "john@example.com");
        Account account = service.createAccount(user.getId(), Account.AccountType.CHECKING, 1000.0);
        
        assertNotNull(account);
        assertEquals(user, account.getOwner());
        assertEquals(1000.0, account.getBalance(), 0.01);
    }
    
    // Teste la récupération d'un compte par son ID
    @Test
    public void testGetAccountById() throws BusinessException {
        BankingService service = new BankingService();
        User user = service.createUser("john", "pwd", "john@example.com");
        Account created = service.createAccount(user.getId(), Account.AccountType.CHECKING, 1000.0);
        Account retrieved = service.getAccountById(created.getId());
        
        assertEquals(created, retrieved);
    }
    
    // Teste la récupération d'un compte inexistant par ID
    @Test(expected = AccountNotFoundException.class)
    public void testGetAccountByIdNotFound() throws AccountNotFoundException {
        BankingService service = new BankingService();
        service.getAccountById("NONEXISTENT");
    }
    
    // Teste la récupération d'un utilisateur par son nom d'utilisateur
    @Test
    public void testGetUserByUsername() throws BusinessException {
        BankingService service = new BankingService();
        User created = service.createUser("john", "pwd", "john@example.com");
        User retrieved = service.getUserByUsername("john");
        
        assertEquals(created, retrieved);
    }
    
    // Teste la récupération d'un compte par son numéro
    @Test
    public void testGetAccountByNumber() throws BusinessException {
        BankingService service = new BankingService();
        User user = service.createUser("john", "pwd", "john@example.com");
        Account created = service.createAccount(user.getId(), Account.AccountType.CHECKING, 1000.0);
        Account retrieved = service.getAccountByNumber(created.getAccountNumber());
        
        assertEquals(created, retrieved);
    }
    
    // Teste la récupération de tous les comptes d'un utilisateur
    @Test
    public void testGetUserAccounts() throws BusinessException {
        BankingService service = new BankingService();
        User user = service.createUser("john", "pwd", "john@example.com");
        Account account1 = service.createAccount(user.getId(), Account.AccountType.CHECKING, 1000.0);
        Account account2 = service.createAccount(user.getId(), Account.AccountType.SAVINGS, 500.0);
        
        assertEquals(2, service.getUserAccounts(user.getId()).size());
    }
    
    // Teste la récupération du solde d'un compte
    @Test
    public void testGetAccountBalance() throws BusinessException {
        BankingService service = new BankingService();
        User user = service.createUser("john", "pwd", "john@example.com");
        Account account = service.createAccount(user.getId(), Account.AccountType.CHECKING, 1500.0);
        
        assertEquals(1500.0, service.getAccountBalance(account.getId()), 0.01);
    }
    
    // Teste la récupération de tous les utilisateurs
    @Test
    public void testGetAllUsers() throws BusinessException {
        BankingService service = new BankingService();
        service.createUser("john", "pwd", "john@example.com");
        service.createUser("jane", "pwd", "jane@example.com");
        
        assertEquals(2, service.getAllUsers().size());
    }
    
    // Teste la récupération de tous les comptes
    @Test
    public void testGetAllAccounts() throws BusinessException {
        BankingService service = new BankingService();
        User user = service.createUser("john", "pwd", "john@example.com");
        service.createAccount(user.getId(), Account.AccountType.CHECKING, 1000.0);
        service.createAccount(user.getId(), Account.AccountType.SAVINGS, 500.0);
        
        assertEquals(2, service.getAllAccounts().size());
    }
}

