package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.exception.BusinessException;
import com.university.finance.exception.ValidationException;
import java.util.UUID;

/**
 * Stratégie pour l'exécution des dépôts.
 * 
 * Cette classe implémente le pattern Strategy pour gérer spécifiquement
 * les opérations de dépôt d'argent sur un compte.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class DepositStrategy implements TransactionStrategy {
    
    @Override
    public Transaction execute(Account account, double amount, Account targetAccount) throws BusinessException {
        // Validation
        if (!validate(account, amount, targetAccount)) {
            throw new ValidationException(
                "Le dépôt ne peut pas être exécuté. Vérifiez le compte et le montant.",
                "deposit"
            );
        }
        
        // Création de la transaction
        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(
            transactionId,
            Transaction.TransactionType.DEPOSIT,
            amount,
            account,
            "Dépôt de " + amount
        );
        
        // Exécution : mise à jour du solde
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        
        // Mise à jour du statut de la transaction
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        
        return transaction;
    }
    
    @Override
    public boolean validate(Account account, double amount, Account targetAccount) {
        // Le compte doit exister
        if (account == null) {
            return false;
        }
        
        // Le compte doit être valide
        if (!account.isValid()) {
            return false;
        }
        
        // Le montant doit être positif
        if (amount <= 0) {
            return false;
        }
        
        // Pour un dépôt, on n'a pas besoin de targetAccount
        return true;
    }
    
    @Override
    public Transaction.TransactionType getTransactionType() {
        return Transaction.TransactionType.DEPOSIT;
    }
}
