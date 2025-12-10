package com.university.finance.pattern.singleton;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigurationManagerTest {
    
    // Teste que getInstance retourne toujours la même instance (Singleton)
    @Test
    public void testGetInstance() {
        ConfigurationManager instance1 = ConfigurationManager.getInstance();
        ConfigurationManager instance2 = ConfigurationManager.getInstance();
        
        assertNotNull(instance1);
        assertSame(instance1, instance2); // Même instance
    }
    
    // Teste la configuration par défaut du ConfigurationManager
    @Test
    public void testDefaultConfiguration() {
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.resetToDefaults(); // Réinitialiser avant le test
        
        assertTrue(config.isAuditEnabled());
        assertFalse(config.isEmailNotificationsEnabled());
        assertEquals(10000.0, config.getMaxTransactionAmount(), 0.01);
    }
    
    // Teste la modification de la configuration
    @Test
    public void testSetConfiguration() {
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.resetToDefaults(); // Réinitialiser avant le test
        
        config.setEmailNotificationsEnabled(true);
        assertTrue(config.isEmailNotificationsEnabled());
        
        config.setMaxTransactionAmount(5000.0);
        assertEquals(5000.0, config.getMaxTransactionAmount(), 0.01);
    }
}

