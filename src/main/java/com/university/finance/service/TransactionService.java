package com.university.finance.service;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.pattern.observer.TransactionObserver;
import com.university.finance.pattern.strategy.DepositStrategy;
import com.university.finance.pattern.strategy.TransactionStrategy;
import com.university.finance.pattern.strategy.TransferStrategy;
import com.university.finance.pattern.strategy.WithdrawStrategy;
import com.university.finance.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour gérer les transactions bancaires.
 * 
 * Ce service orchestre l'exécution des transactions en utilisant le pattern Strategy
 * et notifie les observateurs (pattern Observer) des événements de transaction.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class TransactionService {
    
    private List<TransactionObserver> observers;
    private List<Transaction> transactionHistory;
    
    /**
     * Constructeur par défaut.
     */
    public TransactionService() {
        this.observers = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }
    
    /**
     * Ajoute un observateur de transactions.
     * 
     * @param observer L'observateur à ajouter
     */
    public void addObserver(TransactionObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    /**
     * Retire un observateur de transactions.
     * 
     * @param observer L'observateur à retirer
     */
    public void removeObserver(TransactionObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Notifie tous les observateurs qu'une transaction a réussi.
     * 
     * @param transaction La transaction complétée
     */
    private void notifyObserversCompleted(Transaction transaction) {
        for (TransactionObserver observer : observers) {
            observer.onTransactionCompleted(transaction);
        }
    }
    
    /**
     * Effectue un dépôt sur un compte.
     * 
     * @param account Le compte sur lequel effectuer le dépôt
     * @param amount Le montant à déposer
     * @return La transaction créée
     * @throws BusinessException Si le dépôt ne peut pas être effectué
     */
    public Transaction deposit(Account account, double amount) throws BusinessException {
        TransactionStrategy strategy = new DepositStrategy();
        
        try {
            Transaction transaction = strategy.execute(account, amount, null);
            transactionHistory.add(transaction);
            notifyObserversCompleted(transaction);
            return transaction;
        } catch (BusinessException e) {
            // Si la transaction a été créée mais a échoué, elle est déjà gérée dans la stratégie
            // On propage simplement l'exception
            throw e;
        }
    }
    
    /**
     * Effectue un retrait depuis un compte.
     * 
     * @param account Le compte depuis lequel effectuer le retrait
     * @param amount Le montant à retirer
     * @return La transaction créée
     * @throws BusinessException Si le retrait ne peut pas être effectué
     */
    public Transaction withdraw(Account account, double amount) throws BusinessException {
        TransactionStrategy strategy = new WithdrawStrategy();
        
        try {
            Transaction transaction = strategy.execute(account, amount, null);
            transactionHistory.add(transaction);
            notifyObserversCompleted(transaction);
            return transaction;
        } catch (BusinessException e) {
            // Si la transaction a été créée mais a échoué, elle est déjà gérée dans la stratégie
            // On propage simplement l'exception
            throw e;
        }
    }
    
    /**
     * Effectue un transfert entre deux comptes.
     * 
     * @param fromAccount Le compte source
     * @param toAccount Le compte destination
     * @param amount Le montant à transférer
     * @return La transaction créée
     * @throws BusinessException Si le transfert ne peut pas être effectué
     */
    public Transaction transfer(Account fromAccount, Account toAccount, double amount) throws BusinessException {
        return transfer(fromAccount, toAccount, amount, Transaction.TransactionType.TRANSFER);
    }
    
    /**
     * Effectue un virement interne (VIRIN).
     * 
     * @param fromAccount Le compte source
     * @param toAccount Le compte destination
     * @param amount Le montant à transférer
     * @return La transaction créée
     * @throws BusinessException Si le virement ne peut pas être effectué
     */
    public Transaction virementInterne(Account fromAccount, Account toAccount, double amount) throws BusinessException {
        return transfer(fromAccount, toAccount, amount, Transaction.TransactionType.VIRIN);
    }
    
    /**
     * Effectue un virement externe (VIREST).
     * 
     * @param fromAccount Le compte source
     * @param toAccount Le compte destination
     * @param amount Le montant à transférer
     * @return La transaction créée
     * @throws BusinessException Si le virement ne peut pas être effectué
     */
    public Transaction virementExterne(Account fromAccount, Account toAccount, double amount) throws BusinessException {
        return transfer(fromAccount, toAccount, amount, Transaction.TransactionType.VIREST);
    }
    
    /**
     * Effectue un transfert avec un type spécifique.
     * 
     * @param fromAccount Le compte source
     * @param toAccount Le compte destination
     * @param amount Le montant à transférer
     * @param transferType Le type de virement
     * @return La transaction créée
     * @throws BusinessException Si le transfert ne peut pas être effectué
     */
    private Transaction transfer(Account fromAccount, Account toAccount, double amount, 
                                Transaction.TransactionType transferType) throws BusinessException {
        TransactionStrategy strategy = new TransferStrategy(transferType);
        
        try {
            Transaction transaction = strategy.execute(fromAccount, amount, toAccount);
            transactionHistory.add(transaction);
            notifyObserversCompleted(transaction);
            return transaction;
        } catch (BusinessException e) {
            // Si la transaction a été créée mais a échoué, elle est déjà gérée dans la stratégie
            // On propage simplement l'exception
            throw e;
        }
    }
    
    /**
     * Récupère l'historique des transactions pour un compte.
     * 
     * @param account Le compte
     * @return Liste des transactions pour ce compte
     */
    public List<Transaction> getTransactionHistory(Account account) {
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction transaction : transactionHistory) {
            if ((transaction.getFromAccount() != null && transaction.getFromAccount().equals(account)) ||
                (transaction.getToAccount() != null && transaction.getToAccount().equals(account))) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }
    
    /**
     * Récupère toutes les transactions.
     * 
     * @return Liste de toutes les transactions
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactionHistory);
    }
    
    /**
     * Récupère une transaction par son ID.
     * 
     * @param transactionId L'ID de la transaction
     * @return La transaction trouvée, ou null si non trouvée
     */
    public Transaction getTransactionById(String transactionId) {
        for (Transaction transaction : transactionHistory) {
            if (transaction.getId().equals(transactionId)) {
                return transaction;
            }
        }
        return null;
    }
}
