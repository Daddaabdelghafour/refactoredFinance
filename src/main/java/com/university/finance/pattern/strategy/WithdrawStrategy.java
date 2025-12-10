package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.exception.BusinessException;
import com.university.finance.exception.ValidationException;
import com.university.finance.exception.InsufficientFundsException;
import java.util.UUID;

/**
 * Stratégie pour l'exécution des retraits.
 * 
 * Cette classe implémente le pattern Strategy pour gérer spécifiquement
 * les opérations de retrait d'argent depuis un compte.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class WithdrawStrategy implements TransactionStrategy {
    
    @Override
    public Transaction execute(Account account, double amount, Account targetAccount) throws BusinessException {
        // Validation
        if (!validate(account, amount, targetAccount)) {
            throw new ValidationException(
                "Le retrait ne peut pas être exécuté. Vérifiez le compte et le montant.",
                "withdraw"
            );
        }
        
        // Vérification du solde suffisant
        if (!account.hasSufficientBalance(amount)) {
            throw new InsufficientFundsException(
                account.getId(),
                account.getBalance(),
                amount
            );
        }
        
        // Création de la transaction
        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(
            transactionId,
            Transaction.TransactionType.WITHDRAW,
            amount,
            account,
            "Retrait de " + amount
        );
        
        // Exécution : mise à jour du solde
        double newBalance = account.getBalance() - amount;
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
        
        // Pour un retrait, on n'a pas besoin de targetAccount
        return true;
    }
    
    @Override
    public Transaction.TransactionType getTransactionType() {
        return Transaction.TransactionType.WITHDRAW;
    }
}
