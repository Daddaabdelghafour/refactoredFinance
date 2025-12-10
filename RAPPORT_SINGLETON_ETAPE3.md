# Sous-partie : Pattern Singleton (Bonus) - Étape 3

## Pattern Singleton : ConfigurationManager

### Objectif

Centraliser la configuration de l'application dans un gestionnaire unique accessible globalement.

### Implémentation

**Classe :** `ConfigurationManager` dans le package `pattern.singleton`

**Caractéristiques :**
- Instance unique garantie (constructeur privé)
- Méthode `getInstance()` pour accès global
- Synchronisation pour thread-safety

### Configuration gérée

1. **Observateurs :**
   - `auditEnabled` : Active/désactive l'audit
   - `emailNotificationsEnabled` : Active/désactive les emails

2. **Règles métier :**
   - `maxTransactionAmount` : Limite maximale de transaction
   - `minAccountBalance` : Solde minimum autorisé
   - `maxAccountBalance` : Solde maximum autorisé

3. **Système :**
   - `dateFormat` : Format de date utilisé

### Utilisation dans l'application

Dans `App.setupObservers()` :
```java
ConfigurationManager config = ConfigurationManager.getInstance();

if (config.isAuditEnabled()) {
    transactionService.addObserver(new AuditLogger());
}

NotificationService notificationService = new NotificationService(
    config.isEmailNotificationsEnabled()
);
```

### Bénéfices

- **Configuration centralisée** : Un seul point d'accès
- **Flexibilité** : Modification sans recompiler
- **Maintenabilité** : Facile à étendre avec de nouveaux paramètres

