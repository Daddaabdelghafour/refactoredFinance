package com.university.finance.service;

import com.university.finance.model.Account;
import com.university.finance.model.User;
import com.university.finance.pattern.factory.AccountFactory;
import com.university.finance.pattern.factory.UserFactory;
import com.university.finance.exception.BusinessException;
import com.university.finance.exception.AccountNotFoundException;
import com.university.finance.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service pour gérer les utilisateurs et les comptes bancaires.
 * 
 * Ce service gère uniquement la création et la gestion des utilisateurs et des comptes.
 * Il utilise les patterns Factory pour créer les entités.
 * 
 * Pour les transactions, utilisez TransactionService séparément.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class BankingService {
    
    private Map<String, User> users;
    private Map<String, Account> accounts;
    
    /**
     * Constructeur par défaut.
     */
    public BankingService() {
        this.users = new HashMap<>();
        this.accounts = new HashMap<>();
    }
    
    // ========== Gestion des Utilisateurs ==========
    
    /**
     * Crée un nouvel utilisateur.
     * 
     * @param username Nom d'utilisateur
     * @param passwordHash Mot de passe hashé
     * @param email Adresse email
     * @return L'utilisateur créé
     * @throws BusinessException Si la création échoue
     */
    public User createUser(String username, String passwordHash, String email) throws BusinessException {
        User user = UserFactory.createUser(username, passwordHash, email);
        users.put(user.getId(), user);
        return user;
    }
    
    /**
     * Récupère un utilisateur par son ID.
     * 
     * @param userId L'ID de l'utilisateur
     * @return L'utilisateur trouvé
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     */
    public User getUserById(String userId) throws UserNotFoundException {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        return user;
    }
    
    /**
     * Récupère un utilisateur par son nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur
     * @return L'utilisateur trouvé
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     */
    public User getUserByUsername(String username) throws UserNotFoundException {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new UserNotFoundException(username, true);
    }
    
    /**
     * Récupère tous les utilisateurs.
     * 
     * @return Liste de tous les utilisateurs
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    // ========== Gestion des Comptes ==========
    
    /**
     * Crée un nouveau compte pour un utilisateur.
     * 
     * @param userId L'ID de l'utilisateur propriétaire
     * @param accountType Le type de compte
     * @param initialBalance Le solde initial
     * @return Le compte créé
     * @throws BusinessException Si la création échoue
     */
    public Account createAccount(String userId, Account.AccountType accountType, double initialBalance) 
            throws BusinessException {
        User owner = getUserById(userId);
        Account account = AccountFactory.createAccount(owner, accountType, initialBalance);
        accounts.put(account.getId(), account);
        return account;
    }
    
    /**
     * Crée un nouveau compte avec solde initial de 0.
     * 
     * @param userId L'ID de l'utilisateur propriétaire
     * @param accountType Le type de compte
     * @return Le compte créé
     * @throws BusinessException Si la création échoue
     */
    public Account createAccount(String userId, Account.AccountType accountType) throws BusinessException {
        return createAccount(userId, accountType, 0.0);
    }
    
    /**
     * Récupère un compte par son ID.
     * 
     * @param accountId L'ID du compte
     * @return Le compte trouvé
     * @throws AccountNotFoundException Si le compte n'existe pas
     */
    public Account getAccountById(String accountId) throws AccountNotFoundException {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException(accountId);
        }
        return account;
    }
    
    /**
     * Récupère un compte par son numéro de compte.
     * 
     * @param accountNumber Le numéro de compte
     * @return Le compte trouvé
     * @throws AccountNotFoundException Si le compte n'existe pas
     */
    public Account getAccountByNumber(String accountNumber) throws AccountNotFoundException {
        for (Account account : accounts.values()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new AccountNotFoundException(accountNumber, true);
    }
    
    /**
     * Récupère tous les comptes d'un utilisateur.
     * 
     * @param userId L'ID de l'utilisateur
     * @return Liste des comptes de l'utilisateur
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     */
    public List<Account> getUserAccounts(String userId) throws UserNotFoundException {
        User user = getUserById(userId);
        List<Account> userAccounts = new ArrayList<>();
        for (Account account : accounts.values()) {
            if (account.getOwner().equals(user)) {
                userAccounts.add(account);
            }
        }
        return userAccounts;
    }
    
    /**
     * Récupère tous les comptes.
     * 
     * @return Liste de tous les comptes
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
    
    /**
     * Récupère le solde d'un compte.
     * 
     * @param accountId L'ID du compte
     * @return Le solde du compte
     * @throws AccountNotFoundException Si le compte n'existe pas
     */
    public double getAccountBalance(String accountId) throws AccountNotFoundException {
        Account account = getAccountById(accountId);
        return account.getBalance();
    }
    
}
