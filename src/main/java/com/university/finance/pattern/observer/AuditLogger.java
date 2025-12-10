package com.university.finance.pattern.observer;

import com.university.finance.model.Transaction;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Observateur qui enregistre toutes les transactions dans un journal d'audit.
 * 
 * Cette classe implémente le pattern Observer pour logger toutes les transactions
 * effectuées dans le système, permettant un audit complet des opérations.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class AuditLogger implements TransactionObserver {
    
    private List<String> auditLog;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Constructeur par défaut.
     */
    public AuditLogger() {
        this.auditLog = new ArrayList<>();
    }
    
    @Override
    public void onTransactionCompleted(Transaction transaction) {
        String logEntry = formatLogEntry(transaction, "COMPLETED");
        auditLog.add(logEntry);
        System.out.println("[AUDIT] " + logEntry);
    }
    
    @Override
    public void onTransactionFailed(Transaction transaction, Exception error) {
        String logEntry = formatLogEntry(transaction, "FAILED") + " - Error: " + error.getMessage();
        auditLog.add(logEntry);
        System.out.println("[AUDIT] " + logEntry);
    }
    
    /**
     * Formate une entrée de log pour une transaction.
     * 
     * @param transaction La transaction à logger
     * @param status Le statut de la transaction
     * @return Une chaîne formatée représentant l'entrée de log
     */
    private String formatLogEntry(Transaction transaction, String status) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(transaction.getTimestamp().format(FORMATTER)).append("] ");
        sb.append("Transaction ").append(transaction.getId()).append(" - ");
        sb.append("Type: ").append(transaction.getType()).append(", ");
        sb.append("Amount: ").append(transaction.getAmount()).append(", ");
        
        if (transaction.getFromAccount() != null) {
            sb.append("From: ").append(transaction.getFromAccount().getAccountNumber()).append(", ");
        }
        
        if (transaction.getToAccount() != null) {
            sb.append("To: ").append(transaction.getToAccount().getAccountNumber()).append(", ");
        }
        
        sb.append("Status: ").append(status);
        
        if (transaction.getDescription() != null && !transaction.getDescription().isEmpty()) {
            sb.append(", Description: ").append(transaction.getDescription());
        }
        
        return sb.toString();
    }
    
    /**
     * Récupère toutes les entrées du journal d'audit.
     * 
     * @return Une liste de toutes les entrées de log
     */
    public List<String> getAuditLog() {
        return new ArrayList<>(auditLog);
    }
    
    /**
     * Récupère les entrées du journal d'audit pour un compte spécifique.
     * 
     * @param accountNumber Numéro de compte
     * @return Une liste des entrées de log pour ce compte
     */
    public List<String> getAuditLogForAccount(String accountNumber) {
        List<String> accountLogs = new ArrayList<>();
        for (String log : auditLog) {
            if (log.contains(accountNumber)) {
                accountLogs.add(log);
            }
        }
        return accountLogs;
    }
    
    /**
     * Efface le journal d'audit.
     */
    public void clearAuditLog() {
        auditLog.clear();
    }
    
    /**
     * Retourne le nombre d'entrées dans le journal.
     * 
     * @return Le nombre d'entrées
     */
    public int getLogCount() {
        return auditLog.size();
    }
}
