package com.university.finance.pattern.observer;

import com.university.finance.model.Transaction;

/**
 * Interface définissant un observateur de transactions.
 * 
 * Pattern Observer : Permet à des objets de s'abonner aux événements de transaction
 * et d'être notifiés automatiquement lorsqu'une transaction est effectuée.
 * 
 * Ce pattern permet de découpler les composants qui observent les transactions
 * (comme l'audit, les notifications) du composant qui exécute les transactions.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public interface TransactionObserver {
    
    /**
     * Méthode appelée lorsqu'une transaction est effectuée.
     * 
     * @param transaction La transaction qui vient d'être effectuée
     */
    void onTransactionCompleted(Transaction transaction);
    
    /**
     * Méthode appelée lorsqu'une transaction échoue.
     * 
     * @param transaction La transaction qui a échoué
     * @param error L'erreur qui a causé l'échec
     */
    void onTransactionFailed(Transaction transaction, Exception error);
}
