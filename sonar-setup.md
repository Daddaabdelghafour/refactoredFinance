# 🔍 Configuration SonarQube pour refactored-finance

## 📋 Table des matières
1. [Accès initial SonarQube](#accès-initial)
2. [Génération du token](#génération-du-token)
3. [Configuration Jenkins](#configuration-jenkins)
4.  [Quality Gates](#quality-gates)

---

## 🚀 Accès initial

### Étape 1 : Accéder à SonarQube
```bash
http://localhost:9000
```

**Identifiants par défaut :**
- Username: `admin`
- Password: `admin`

⚠️ **Important :** SonarQube vous demandera de changer le mot de passe au premier login.

---

## 🔑 Génération du token

### Étape 2 : Créer un token d'authentification

1. Cliquez sur votre profil (coin supérieur droit)
2.  **My Account** → **Security**
3. **Generate Tokens**
4.  Nom du token : `jenkins-token`
5. Type : **Global Analysis Token**
6. Cliquez sur **Generate**
7. **COPIEZ LE TOKEN** (vous ne pourrez plus le revoir)

Exemple de token : `squ_a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6`

---

## 🔧 Configuration Jenkins

### Étape 3 : Ajouter le token dans Jenkins

1. Accédez à Jenkins : `http://localhost:8080`
2. **Manage Jenkins** → **Credentials** → **System** → **Global credentials**
3.  Cliquez sur **Add Credentials**
4. Remplissez :
    - Kind: **Secret text**
    - Secret: `[VOTRE_TOKEN_SONARQUBE]`
    - ID: `sonar-token`
    - Description: `SonarQube Authentication Token`
5. Cliquez sur **Create**

### Étape 4 : Configurer le serveur SonarQube

1. **Manage Jenkins** → **Configure System**
2. Descendez à la section **SonarQube servers**
3. Cliquez sur **Add SonarQube**
4. Remplissez :
    - Name: `SonarQube`
    - Server URL: `http://sonarqube:9000`
    - Server authentication token: Sélectionnez `sonar-token`
5. Cliquez sur **Save**

### Étape 5 : Configurer Maven pour SonarQube

1. **Manage Jenkins** → **Global Tool Configuration**
2. Section **Maven** :
    - Cliquez sur **Add Maven**
    - Name: `Maven 3.9`
    - Cochez **Install automatically**
    - Version: `3.9.6`
3. Section **JDK** :
    - Cliquez sur **Add JDK**
    - Name: `JDK 17`
    - Cochez **Install automatically**
    - Version: `jdk-17.0.9+9`
4. Cliquez sur **Save**

---

## 🚦 Quality Gates

### Étape 6 : Créer un Quality Gate simple

1. Dans SonarQube, allez dans **Quality Gates**
2. Cliquez sur **Create**
3. Nom : `refactored-finance-gate`
4. Ajoutez les conditions suivantes :

| Metric | Operator | Value |
|--------|----------|-------|
| Coverage | is less than | 60% |
| Duplicated Lines (%) | is greater than | 5% |
| Maintainability Rating | is worse than | B |
| Reliability Rating | is worse than | B |
| Security Rating | is worse than | B |

5. Cliquez sur **Save**

### Étape 7 : Associer le Quality Gate au projet

1. Allez dans **Projects**
2. Sélectionnez `refactored-finance`
3. **Project Settings** → **Quality Gate**
4. Sélectionnez `refactored-finance-gate`
5. Cliquez sur **Save**

---

## ✅ Vérification

### Test de connexion

Exécutez manuellement une analyse depuis la ligne de commande :

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=refactored-finance \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=YOUR_TOKEN
```

Si tout fonctionne, vous verrez :
```
[INFO] ANALYSIS SUCCESSFUL
[INFO] You can browse http://localhost:9000/dashboard? id=refactored-finance
```

---

## 🐛 Troubleshooting

### Erreur : "Unauthorized"
- Vérifiez que le token est correct
- Vérifiez que le token n'a pas expiré
- Régénérez un nouveau token si nécessaire

### Erreur : "Quality Gate failed"
- Consultez le dashboard SonarQube
- Vérifiez les métriques qui échouent
- Ajustez les seuils du Quality Gate si nécessaire

### Erreur : "Connection refused"
- Vérifiez que SonarQube est démarré : `docker ps`
- Vérifiez le réseau Docker : `docker network ls`
- Utilisez `http://sonarqube:9000` depuis Jenkins (pas localhost)

---

## 📚 Ressources

- [Documentation SonarQube](https://docs.sonarqube.org/)
- [SonarQube Java Plugin](https://docs.sonarqube.org/latest/analysis/languages/java/)
- [Jenkins SonarQube Plugin](https://plugins.jenkins.io/sonar/)