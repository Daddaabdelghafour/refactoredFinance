package com.university.finance.pattern.observer;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service de notification qui envoie des notifications aux utilisateurs
 * lorsqu'une transaction est effectuée sur leur compte.
 * 
 * Cette classe implémente le pattern Observer pour notifier automatiquement
 * les utilisateurs des transactions effectuées sur leurs comptes.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class NotificationService implements TransactionObserver {
    
    private Map<String, List<String>> notifications; // accountNumber -> liste de notifications
    private boolean emailNotificationsEnabled;
    
    /**
     * Constructeur par défaut.
     */
    public NotificationService() {
        this.notifications = new HashMap<>();
        this.emailNotificationsEnabled = false; // Par défaut, seulement en mémoire
    }
    
    /**
     * Constructeur avec activation des notifications email.
     * 
     * @param emailNotificationsEnabled true pour activer les notifications email
     */
    public NotificationService(boolean emailNotificationsEnabled) {
        this();
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }
    
    @Override
    public void onTransactionCompleted(Transaction transaction) {
        String notification = createNotification(transaction, true);
        
        // Notification pour le compte source (si retrait/transfert)
        if (transaction.getFromAccount() != null) {
            addNotification(transaction.getFromAccount(), notification);
            sendEmailNotification(transaction.getFromAccount(), notification);
        }
        
        // Notification pour le compte destination (si dépôt/transfert)
        if (transaction.getToAccount() != null) {
            String depositNotification = createDepositNotification(transaction);
            addNotification(transaction.getToAccount(), depositNotification);
            sendEmailNotification(transaction.getToAccount(), depositNotification);
        }
    }
    
    @Override
    public void onTransactionFailed(Transaction transaction, Exception error) {
        String notification = createNotification(transaction, false) + 
                             " - Erreur: " + error.getMessage();
        
        // Notification pour le compte concerné
        Account account = transaction.getFromAccount() != null ? 
                         transaction.getFromAccount() : transaction.getToAccount();
        
        if (account != null) {
            addNotification(account, notification);
            sendEmailNotification(account, notification);
        }
    }
    
    /**
     * Crée un message de notification pour une transaction.
     * 
     * @param transaction La transaction
     * @param success true si la transaction a réussi, false sinon
     * @return Le message de notification
     */
    private String createNotification(Transaction transaction, boolean success) {
        StringBuilder sb = new StringBuilder();
        
        if (success) {
            sb.append("✓ Transaction réussie: ");
        } else {
            sb.append("✗ Transaction échouée: ");
        }
        
        sb.append(transaction.getType()).append(" de ");
        sb.append(transaction.getAmount());
        
        if (transaction.getFromAccount() != null && transaction.getToAccount() != null) {
            sb.append(" de ").append(transaction.getFromAccount().getAccountNumber());
            sb.append(" vers ").append(transaction.getToAccount().getAccountNumber());
        } else if (transaction.getFromAccount() != null) {
            sb.append(" depuis ").append(transaction.getFromAccount().getAccountNumber());
        } else if (transaction.getToAccount() != null) {
            sb.append(" vers ").append(transaction.getToAccount().getAccountNumber());
        }
        
        sb.append(" - ").append(transaction.getTimestamp());
        
        return sb.toString();
    }
    
    /**
     * Crée un message de notification spécifique pour un dépôt.
     * 
     * @param transaction La transaction de dépôt
     * @return Le message de notification
     */
    private String createDepositNotification(Transaction transaction) {
        return String.format(
            "✓ Dépôt reçu: %.2f sur le compte %s - %s",
            transaction.getAmount(),
            transaction.getToAccount().getAccountNumber(),
            transaction.getTimestamp()
        );
    }
    
    /**
     * Ajoute une notification pour un compte.
     * 
     * @param account Le compte
     * @param notification Le message de notification
     */
    private void addNotification(Account account, String notification) {
        String accountNumber = account.getAccountNumber();
        notifications.computeIfAbsent(accountNumber, k -> new ArrayList<>()).add(notification);
    }
    
    /**
     * Envoie une notification par email si activé.
     * 
     * @param account Le compte concerné
     * @param notification Le message de notification
     */
    private void sendEmailNotification(Account account, String notification) {
        if (emailNotificationsEnabled && account.getOwner() != null && account.getOwner().getEmail() != null) {
            // Simulation d'envoi d'email
            System.out.println("[EMAIL] Envoi à " + account.getOwner().getEmail() + ": " + notification);
            // Dans une vraie application, on utiliserait un service d'email ici
        }
    }
    
    /**
     * Récupère toutes les notifications pour un compte.
     * 
     * @param accountNumber Numéro de compte
     * @return Liste des notifications pour ce compte
     */
    public List<String> getNotifications(String accountNumber) {
        return new ArrayList<>(notifications.getOrDefault(accountNumber, new ArrayList<>()));
    }
    
    /**
     * Efface les notifications pour un compte.
     * 
     * @param accountNumber Numéro de compte
     */
    public void clearNotifications(String accountNumber) {
        notifications.remove(accountNumber);
    }
    
    /**
     * Efface toutes les notifications.
     */
    public void clearAllNotifications() {
        notifications.clear();
    }
    
    /**
     * Active ou désactive les notifications email.
     * 
     * @param enabled true pour activer, false pour désactiver
     */
    public void setEmailNotificationsEnabled(boolean enabled) {
        this.emailNotificationsEnabled = enabled;
    }
    
    /**
     * Vérifie si les notifications email sont activées.
     * 
     * @return true si activées, false sinon
     */
    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }
}
