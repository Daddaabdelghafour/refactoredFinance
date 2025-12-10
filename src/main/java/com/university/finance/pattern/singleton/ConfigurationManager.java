package com.university.finance.pattern.singleton;

/**
 * Gestionnaire de configuration utilisant le pattern Singleton.
 * 
 * Ce pattern garantit qu'il n'existe qu'une seule instance de ConfigurationManager
 * dans toute l'application, permettant un accès centralisé à la configuration.
 * 
 * @author Membre 2 - Refactoring Java + Tests
 * @version 1.0
 */
public class ConfigurationManager {
    
    private static ConfigurationManager instance;
    
    // Configuration des observateurs
    private boolean auditEnabled = true;
    private boolean emailNotificationsEnabled = false;
    
    // Configuration métier
    private double maxTransactionAmount = 10000.0;
    private double minAccountBalance = 0.0;
    private double maxAccountBalance = 1000000.0;
    
    // Configuration système
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * Constructeur privé pour empêcher l'instanciation directe.
     */
    private ConfigurationManager() {
        // Initialisation privée
    }
    
    /**
     * Récupère l'instance unique du ConfigurationManager.
     * 
     * @return L'instance unique
     */
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    // Getters et Setters pour la configuration des observateurs
    
    public boolean isAuditEnabled() {
        return auditEnabled;
    }
    
    public void setAuditEnabled(boolean auditEnabled) {
        this.auditEnabled = auditEnabled;
    }
    
    public boolean isEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }
    
    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }
    
    // Getters et Setters pour la configuration métier
    
    public double getMaxTransactionAmount() {
        return maxTransactionAmount;
    }
    
    public void setMaxTransactionAmount(double maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
    }
    
    public double getMinAccountBalance() {
        return minAccountBalance;
    }
    
    public void setMinAccountBalance(double minAccountBalance) {
        this.minAccountBalance = minAccountBalance;
    }
    
    public double getMaxAccountBalance() {
        return maxAccountBalance;
    }
    
    public void setMaxAccountBalance(double maxAccountBalance) {
        this.maxAccountBalance = maxAccountBalance;
    }
    
    // Getters et Setters pour la configuration système
    
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    /**
     * Réinitialise la configuration aux valeurs par défaut.
     */
    public void resetToDefaults() {
        this.auditEnabled = true;
        this.emailNotificationsEnabled = false;
        this.maxTransactionAmount = 10000.0;
        this.minAccountBalance = 0.0;
        this.maxAccountBalance = 1000000.0;
        this.dateFormat = "yyyy-MM-dd HH:mm:ss";
    }
}

