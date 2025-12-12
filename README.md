# Finance App - Application Bancaire Refactorée

Application bancaire Java refactorée à partir d'un code spaghetti, utilisant des design patterns et une architecture modulaire.

## Architecture

```
src/main/java/com/university/finance/
├── model/          # Modèles de données (User, Account, Transaction)
├── exception/      # Exceptions métier personnalisées
├── pattern/
│   ├── strategy/   # Pattern Strategy (transactions)
│   ├── factory/    # Pattern Factory (création d'entités)
│   ├── observer/   # Pattern Observer (audit et notifications)
│   └── singleton/  # Pattern Singleton (ConfigurationManager)
└── service/        # Services métier (BankingService, TransactionService)
```

## Design Patterns

- **Strategy** : Gestion des différents types de transactions (Deposit, Withdraw, Transfer)
- **Factory** : Création centralisée d'utilisateurs et comptes avec validation
- **Observer** : Système d'audit et notifications pour les transactions
- **Singleton** : Gestionnaire de configuration global

## Build & Run

### Prérequis
- Java 11+
- Maven 3.6+

### Compilation
```bash
mvn clean compile
```

### Tests
```bash
mvn test
```

### Génération rapport de couverture
```bash
mvn test jacoco:report
```
Rapport disponible dans `target/site/jacoco/index.html`

### Documentation JavaDoc
```bash
mvn javadoc:javadoc
```
Documentation disponible dans `target/reports/apidocs/index.html`

### Exécution
```bash
mvn exec:java -Dexec.mainClass="com.university.finance.App"
```

## 📊 Métriques

- **122 tests unitaires** (0 échecs)
- **86% de couverture de code** (objectif > 80% ✓)
- **21 classes** couvertes à 100%

## 📝 Fonctionnalités

- Gestion d'utilisateurs et comptes bancaires
- Transactions : dépôt, retrait, transfert
- Virements : interne (VIRIN), externe (VIREST), multiple (VIRMULTA)
- Audit automatique des transactions
- Notifications par email (configurable)
- Gestion d'exceptions métier

## 🛠️ Technologies

- Java 11
- JUnit 4
- JaCoCo (couverture de code)
- Maven

## 👥 Auteurs

- **Membre 1** : DevOps Stack (Jenkins, SonarQube)
- **Membre 2** : Refactoring Java + Tests