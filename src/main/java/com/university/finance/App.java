package com.university.finance;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.service.BankingService;
import com.university.finance.service.TransactionService;
import com.university.finance.pattern.observer.AuditLogger;
import com.university.finance.pattern.observer.NotificationService;
import com.university.finance.pattern.singleton.ConfigurationManager;
import com.university.finance.exception.BusinessException;
import java.util.List;
import java.util.Scanner;

/**
 * Application principale du système bancaire refactoré.
 * 
 * Cette application remplace le code spaghetti initial par une architecture
 * modulaire utilisant les services, patterns et exceptions métier.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class App {
    
    private BankingService bankingService;
    private TransactionService transactionService;
    private Scanner scanner;
    
    /**
     * Constructeur par défaut.
     */
    public App() {
        this.bankingService = new BankingService();
        this.transactionService = new TransactionService();
        this.scanner = new Scanner(System.in);
        
        // Configuration des observateurs
        setupObservers();
        
        // Initialisation avec des données de démonstration
        initializeDemoData();
    }
    
    /**
     * Configure les observateurs pour les transactions.
     */
    private void setupObservers() {
        ConfigurationManager config = ConfigurationManager.getInstance();
        
        if (config.isAuditEnabled()) {
            transactionService.addObserver(new AuditLogger());
        }
        
        NotificationService notificationService = new NotificationService(
            config.isEmailNotificationsEnabled()
        );
        transactionService.addObserver(notificationService);
    }
    
    /**
     * Initialise des données de démonstration.
     */
    private void initializeDemoData() {
        try {
            // Créer des utilisateurs de démonstration
            User user1 = bankingService.createUser("user1", "password1_hash", "user1@example.com");
            User user2 = bankingService.createUser("user2", "password2_hash", "user2@example.com");
            
            // Créer des comptes avec soldes initiaux
            Account account1 = bankingService.createAccount(user1.getId(), Account.AccountType.CHECKING, 1000.0);
            Account account2 = bankingService.createAccount(user2.getId(), Account.AccountType.CHECKING, 500.0);
            
            System.out.println("=== Données de démonstration initialisées ===");
            System.out.println("User1 - Compte: " + account1.getAccountNumber() + " - Solde: " + account1.getBalance());
            System.out.println("User2 - Compte: " + account2.getAccountNumber() + " - Solde: " + account2.getBalance());
            System.out.println();
        } catch (BusinessException e) {
            System.err.println("Erreur lors de l'initialisation: " + e.getMessage());
        }
    }
    
    /**
     * Point d'entrée de l'application.
     * 
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    
    /**
     * Lance la boucle principale de l'application.
     */
    public void run() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = readChoice();
            
            try {
                switch (choice) {
                    case 1:
                        displayBalance();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        displayHistory();
                        break;
                    case 6:
                        createUser();
                        break;
                    case 7:
                        createAccount();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Au revoir!");
                        break;
                    default:
                        System.out.println("Choix invalide!");
                }
            } catch (BusinessException e) {
                System.err.println("Erreur: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erreur inattendue: " + e.getMessage());
            }
            
            if (running) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Affiche le menu principal.
     */
    private void displayMenu() {
        System.out.println("\n=== Système Bancaire Refactoré ===");
        System.out.println("1. Afficher solde");
        System.out.println("2. Déposer argent");
        System.out.println("3. Retirer argent");
        System.out.println("4. Transfert");
        System.out.println("5. Historique des transactions");
        System.out.println("6. Créer un utilisateur");
        System.out.println("7. Créer un compte");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }
    
    /**
     * Lit le choix de l'utilisateur.
     * 
     * @return Le choix de l'utilisateur
     */
    private int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Affiche le solde d'un compte.
     */
    private void displayBalance() throws BusinessException {
        System.out.print("Numéro de compte: ");
        String accountNumber = scanner.nextLine().trim();
        
        Account account = bankingService.getAccountByNumber(accountNumber);
        System.out.println("Solde du compte " + accountNumber + ": " + account.getBalance());
    }
    
    /**
     * Effectue un dépôt.
     */
    private void deposit() throws BusinessException {
        System.out.print("Numéro de compte: ");
        String accountNumber = scanner.nextLine().trim();
        
        System.out.print("Montant: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        
        Account account = bankingService.getAccountByNumber(accountNumber);
        Transaction transaction = transactionService.deposit(account, amount);
        
        System.out.println("Dépôt réussi! Transaction ID: " + transaction.getId());
        System.out.println("Nouveau solde: " + account.getBalance());
    }
    
    /**
     * Effectue un retrait.
     */
    private void withdraw() throws BusinessException {
        System.out.print("Numéro de compte: ");
        String accountNumber = scanner.nextLine().trim();
        
        System.out.print("Montant: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        
        Account account = bankingService.getAccountByNumber(accountNumber);
        Transaction transaction = transactionService.withdraw(account, amount);
        
        System.out.println("Retrait réussi! Transaction ID: " + transaction.getId());
        System.out.println("Nouveau solde: " + account.getBalance());
    }
    
    /**
     * Effectue un transfert.
     */
    private void transfer() throws BusinessException {
        System.out.print("Compte source (numéro): ");
        String fromAccountNumber = scanner.nextLine().trim();
        
        System.out.print("Compte destination (numéro): ");
        String toAccountNumber = scanner.nextLine().trim();
        
        System.out.print("Montant: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());
        
        Account fromAccount = bankingService.getAccountByNumber(fromAccountNumber);
        Account toAccount = bankingService.getAccountByNumber(toAccountNumber);
        
        Transaction transaction = transactionService.transfer(fromAccount, toAccount, amount);
        
        System.out.println("Transfert réussi! Transaction ID: " + transaction.getId());
        System.out.println("Solde compte source: " + fromAccount.getBalance());
        System.out.println("Solde compte destination: " + toAccount.getBalance());
    }
    
    /**
     * Affiche l'historique des transactions d'un compte.
     */
    private void displayHistory() throws BusinessException {
        System.out.print("Numéro de compte: ");
        String accountNumber = scanner.nextLine().trim();
        
        Account account = bankingService.getAccountByNumber(accountNumber);
        List<Transaction> transactions = transactionService.getTransactionHistory(account);
        
        if (transactions.isEmpty()) {
            System.out.println("Aucune transaction pour ce compte.");
        } else {
            System.out.println("\n=== Historique des transactions ===");
            for (Transaction transaction : transactions) {
                System.out.println(transaction.formatForDisplay());
            }
        }
    }
    
    /**
     * Crée un nouvel utilisateur.
     */
    private void createUser() throws BusinessException {
        System.out.print("Nom d'utilisateur: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Mot de passe (sera hashé): ");
        String password = scanner.nextLine().trim();
        String passwordHash = hashPassword(password); // Simulation de hashage
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        User user = bankingService.createUser(username, passwordHash, email);
        System.out.println("Utilisateur créé avec succès! ID: " + user.getId());
    }
    
    /**
     * Crée un nouveau compte.
     */
    private void createAccount() throws BusinessException {
        System.out.print("ID utilisateur: ");
        String userId = scanner.nextLine().trim();
        
        System.out.println("Type de compte:");
        System.out.println("1. CHECKING (Compte courant)");
        System.out.println("2. SAVINGS (Compte épargne)");
        System.out.println("3. BUSINESS (Compte professionnel)");
        System.out.print("Choix: ");
        int typeChoice = Integer.parseInt(scanner.nextLine().trim());
        
        Account.AccountType accountType;
        switch (typeChoice) {
            case 1:
                accountType = Account.AccountType.CHECKING;
                break;
            case 2:
                accountType = Account.AccountType.SAVINGS;
                break;
            case 3:
                accountType = Account.AccountType.BUSINESS;
                break;
            default:
                accountType = Account.AccountType.CHECKING;
        }
        
        System.out.print("Solde initial: ");
        double initialBalance = Double.parseDouble(scanner.nextLine().trim());
        
        Account account = bankingService.createAccount(userId, accountType, initialBalance);
        System.out.println("Compte créé avec succès!");
        System.out.println("Numéro de compte: " + account.getAccountNumber());
        System.out.println("Solde initial: " + account.getBalance());
    }
    
    /**
     * Simule le hashage d'un mot de passe.
     * Dans une vraie application, utiliseriez BCrypt ou similaire.
     * 
     * @param password Le mot de passe en clair
     * @return Le mot de passe "hashé" (simulation)
     */
    private String hashPassword(String password) {
        // Simulation simple - dans la vraie vie, utiliser BCrypt ou similaire
        return "hashed_" + password.hashCode();
    }
}
