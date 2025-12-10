package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.exception.BusinessException;
import com.university.finance.exception.ValidationException;
import com.university.finance.exception.InsufficientFundsException;
import java.util.UUID;

/**
 * Stratégie pour l'exécution des transferts.
 * 
 * Cette classe implémente le pattern Strategy pour gérer spécifiquement
 * les opérations de transfert d'argent entre deux comptes.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class TransferStrategy implements TransactionStrategy {
    
    private Transaction.TransactionType transferType;
    
    /**
     * Constructeur par défaut pour un transfert standard.
     */
    public TransferStrategy() {
        this.transferType = Transaction.TransactionType.TRANSFER;
    }
    
    /**
     * Constructeur avec type de virement spécifique.
     * 
     * @param transferType Type de virement (VIRIN, VIREST, VIRMULTA)
     */
    public TransferStrategy(Transaction.TransactionType transferType) {
        if (transferType == Transaction.TransactionType.VIRIN ||
            transferType == Transaction.TransactionType.VIREST ||
            transferType == Transaction.TransactionType.VIRMULTA ||
            transferType == Transaction.TransactionType.TRANSFER) {
            this.transferType = transferType;
        } else {
            this.transferType = Transaction.TransactionType.TRANSFER;
        }
    }
    
    @Override
    public Transaction execute(Account account, double amount, Account targetAccount) throws BusinessException {
        // Validation
        if (!validate(account, amount, targetAccount)) {
            throw new ValidationException(
                "Le transfert ne peut pas être exécuté. Vérifiez les comptes et le montant.",
                "transfer"
            );
        }
        
        // Vérification du solde suffisant sur le compte source
        if (!account.hasSufficientBalance(amount)) {
            throw new InsufficientFundsException(
                account.getId(),
                account.getBalance(),
                amount
            );
        }
        
        // Création de la transaction
        String transactionId = UUID.randomUUID().toString();
        String description = String.format("Transfert de %.2f de %s vers %s",
            amount,
            account.getAccountNumber(),
            targetAccount.getAccountNumber()
        );
        
        Transaction transaction = new Transaction(
            transactionId,
            transferType,
            amount,
            account,
            targetAccount,
            description
        );
        
        // Exécution : mise à jour des soldes
        double sourceNewBalance = account.getBalance() - amount;
        double targetNewBalance = targetAccount.getBalance() + amount;
        
        account.setBalance(sourceNewBalance);
        targetAccount.setBalance(targetNewBalance);
        
        // Mise à jour du statut de la transaction
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        
        return transaction;
    }
    
    @Override
    public boolean validate(Account account, double amount, Account targetAccount) {
        // Les deux comptes doivent exister
        if (account == null || targetAccount == null) {
            return false;
        }
        
        // Les deux comptes doivent être valides
        if (!account.isValid() || !targetAccount.isValid()) {
            return false;
        }
        
        // Le montant doit être positif
        if (amount <= 0) {
            return false;
        }
        
        // Les comptes source et destination doivent être différents
        if (account.getId().equals(targetAccount.getId())) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public Transaction.TransactionType getTransactionType() {
        return transferType;
    }
}
