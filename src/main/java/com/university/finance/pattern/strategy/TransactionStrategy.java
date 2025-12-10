package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.exception.BusinessException;

/**
 * Interface définissant la stratégie pour l'exécution des transactions.
 * 
 * Pattern Strategy : Permet de définir une famille d'algorithmes (dépôt, retrait, transfert)
 * et de les rendre interchangeables. Chaque stratégie encapsule un algorithme spécifique.
 * 
 * Ce pattern respecte le principe Open/Closed (OCP) : on peut ajouter de nouveaux types
 * de transactions sans modifier le code existant.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public interface TransactionStrategy {
    
    /**
     * Exécute la transaction selon la stratégie spécifique.
     * 
     * @param account Compte concerné (pour dépôt/retrait) ou compte source (pour transfert)
     * @param amount Montant de la transaction
     * @param targetAccount Compte destination (pour transfert, peut être null pour dépôt/retrait)
     * @return La transaction créée et exécutée
     * @throws BusinessException Si la transaction ne peut pas être exécutée
     */
    Transaction execute(Account account, double amount, Account targetAccount) throws BusinessException;
    
    /**
     * Valide si la transaction peut être exécutée.
     * 
     * @param account Compte concerné
     * @param amount Montant de la transaction
     * @param targetAccount Compte destination (si applicable)
     * @return true si la transaction est valide, false sinon
     */
    boolean validate(Account account, double amount, Account targetAccount);
    
    /**
     * Retourne le type de transaction géré par cette stratégie.
     * 
     * @return Le type de transaction
     */
    Transaction.TransactionType getTransactionType();
}
