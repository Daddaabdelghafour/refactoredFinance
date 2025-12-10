package com.university.finance.pattern.factory;

import com.university.finance.model.Account;
import com.university.finance.model.User;
import com.university.finance.exception.ValidationException;
import java.util.UUID;

/**
 * Factory pour la création d'objets Account.
 * 
 * Pattern Factory : Centralise la création d'objets Account avec validation
 * et initialisation cohérente. Permet de garantir que tous les comptes
 * créés sont valides et correctement initialisés.
 * 
 * Ce pattern respecte le principe Single Responsibility (SRP) en séparant
 * la logique de création de la logique métier.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class AccountFactory {
    
    /**
     * Crée un nouveau compte avec un solde initial de 0.
     * 
     * @param owner Propriétaire du compte
     * @param accountType Type de compte
     * @return Un nouveau compte valide
     * @throws ValidationException Si les données fournies sont invalides
     */
    public static Account createAccount(User owner, Account.AccountType accountType) throws ValidationException {
        return createAccount(owner, accountType, 0.0);
    }
    
    /**
     * Crée un nouveau compte avec un solde initial spécifié.
     * 
     * @param owner Propriétaire du compte
     * @param accountType Type de compte
     * @param initialBalance Solde initial
     * @return Un nouveau compte valide
     * @throws ValidationException Si les données fournies sont invalides
     */
    public static Account createAccount(User owner, Account.AccountType accountType, double initialBalance) 
            throws ValidationException {
        // Validation des paramètres
        if (owner == null) {
            throw new ValidationException("Le propriétaire du compte ne peut pas être null", "owner");
        }
        
        if (!owner.isValid()) {
            throw new ValidationException("Le propriétaire du compte n'est pas valide", "owner");
        }
        
        if (accountType == null) {
            throw new ValidationException("Le type de compte ne peut pas être null", "accountType");
        }
        
        if (initialBalance < 0) {
            throw new ValidationException("Le solde initial ne peut pas être négatif", "initialBalance");
        }
        
        // Génération d'un ID unique et d'un numéro de compte
        String accountId = UUID.randomUUID().toString();
        String accountNumber = generateAccountNumber(accountType);
        
        // Création du compte
        Account account = new Account(accountId, accountNumber, owner, accountType, initialBalance);
        
        // Vérification finale de validité
        if (!account.isValid()) {
            throw new ValidationException("Les données du compte sont invalides");
        }
        
        return account;
    }
    
    /**
     * Crée un nouveau compte avec un ID et un numéro de compte spécifiques.
     * 
     * @param accountId Identifiant unique du compte
     * @param accountNumber Numéro de compte
     * @param owner Propriétaire du compte
     * @param accountType Type de compte
     * @param initialBalance Solde initial
     * @return Un nouveau compte valide
     * @throws ValidationException Si les données fournies sont invalides
     */
    public static Account createAccountWithDetails(String accountId, String accountNumber, User owner, 
                                                   Account.AccountType accountType, double initialBalance) 
            throws ValidationException {
        // Validation des paramètres
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new ValidationException("L'ID du compte ne peut pas être vide", "accountId");
        }
        
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new ValidationException("Le numéro de compte ne peut pas être vide", "accountNumber");
        }
        
        if (owner == null) {
            throw new ValidationException("Le propriétaire du compte ne peut pas être null", "owner");
        }
        
        if (!owner.isValid()) {
            throw new ValidationException("Le propriétaire du compte n'est pas valide", "owner");
        }
        
        if (accountType == null) {
            throw new ValidationException("Le type de compte ne peut pas être null", "accountType");
        }
        
        if (initialBalance < 0) {
            throw new ValidationException("Le solde initial ne peut pas être négatif", "initialBalance");
        }
        
        // Création du compte avec les détails fournis
        Account account = new Account(
            accountId.trim(),
            accountNumber.trim(),
            owner,
            accountType,
            initialBalance
        );
        
        // Vérification finale de validité
        if (!account.isValid()) {
            throw new ValidationException("Les données du compte sont invalides");
        }
        
        return account;
    }
    
    /**
     * Génère un numéro de compte unique basé sur le type de compte.
     * 
     * @param accountType Type de compte
     * @return Un numéro de compte généré
     */
    private static String generateAccountNumber(Account.AccountType accountType) {
        String prefix;
        switch (accountType) {
            case CHECKING:
                prefix = "CHK";
                break;
            case SAVINGS:
                prefix = "SAV";
                break;
            case BUSINESS:
                prefix = "BUS";
                break;
            default:
                prefix = "ACC";
        }
        
        // Génération d'un suffixe numérique unique
        String suffix = String.format("%08d", Math.abs(UUID.randomUUID().hashCode()) % 100000000);
        
        return prefix + "-" + suffix;
    }
}
