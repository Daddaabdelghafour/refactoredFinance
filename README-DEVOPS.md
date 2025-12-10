# 🚀 Documentation DevOps - refactored-finance

## 📋 Table des matières
1. [Architecture](#architecture)
2. [Prérequis](#prérequis)
3. [Installation](#installation)
4. [Services](#services)
5. [Pipeline CI/CD](#pipeline-cicd)
6. [Troubleshooting](#troubleshooting)

---

## 🏗️ Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                        CI/CD Pipeline                        │
└──────────────────────────────────────────────────────────────┘

     ┌──────────┐
     │  GitHub  │
     │   Repo   │
     └────┬─────┘
          │
          │ webhook/trigger
          │
     ┌────▼─────┐      ┌──────────────┐      ┌──────────────┐
     │ Jenkins  │─────→│  SonarQube   │─────→│  PostgreSQL  │
     │  :8080   │      │    :9000     │      │    :5432     │
     └────┬─────┘      └──────────────┘      └──────────────┘
          │
          │ build & test
          │
     ┌────▼─────┐      ┌──────────────┐
     │  Maven   │      │    Nexus     │
     │  Build   │─────→│    :8081     │
     └──────────┘      └──────────────┘
          │
          │ docker build
          │
     ┌────▼─────┐
     │  Docker  │
     │  Image   │
     └──────────┘
```

### Stack technique

| Service | Version | Port | Rôle |
|---------|---------|------|------|
| Jenkins | LTS (JDK 17) | 8080 | CI/CD Orchestration |
| SonarQube | 10.3.0 | 9000 | Code Quality Analysis |
| PostgreSQL | 15 | 5432 | SonarQube Database |
| Nexus | 3.64.0 | 8081 | Artifact Repository |
| Maven | 3.9 | - | Build Tool |
| Docker | Latest | - | Containerization |

---

## ✅ Prérequis

### Logiciels nécessaires

- **Docker** : version 24.0+
- **Docker Compose** : version 2.20+
- **Git** : version 2.30+
- **Minimum 8GB RAM** (16GB recommandé)
- **Minimum 20GB espace disque**

### Vérification

```bash
docker --version
docker compose version
git --version
```

---

## 🚀 Installation

### Étape 1 : Cloner le repository

```bash
git clone https://github.com/Daddaabdelghafour/refactored-finance.git
cd refactored-finance
git checkout devops-setup
```

### Étape 2 : Lancer la stack DevOps

```bash
docker compose up -d
```

⏱️ **Patience** : Le premier démarrage prend 3-5 minutes.

### Étape 3 : Vérifier le statut

```bash
docker compose ps
```

Vous devriez voir :

```
NAME         IMAGE                    STATUS        PORTS
jenkins      jenkins-custom           Up            0.0.0.0:8080->8080/tcp
sonarqube    sonarqube:10.3.0         Up            0.0.0.0:9000->9000/tcp
postgres     postgres:15-alpine       Up            5432/tcp
nexus        sonatype/nexus3:3.64.0   Up            0.0.0.0:8081->8081/tcp
```

### Étape 4 : Vérifier les logs

```bash
docker compose logs -f jenkins
docker compose logs -f sonarqube
```

---

## 🌐 Services

### 🔧 Jenkins

**URL** : http://localhost:8080

**Identifiants** :
- Username : `admin`
- Password : `admin`

**Premier accès** :
1. Accédez à http://localhost:8080
2.  Connectez-vous avec admin/admin
3. Jenkins est préconfiguré, pas de setup wizard

**Créer un pipeline** :
1. **New Item** → **Pipeline** → Nom : `refactored-finance-pipeline`
2. **Pipeline** → Definition : `Pipeline script from SCM`
3. SCM : `Git`
4. Repository URL : `https://github.com/Daddaabdelghafour/refactored-finance. git`
5. Branch : `*/devops-setup`
6. Script Path : `Jenkinsfile`
7. **Save**

---

### 🔍 SonarQube

**URL** : http://localhost:9000

**Identifiants par défaut** :
- Username : `admin`
- Password : `admin`

⚠️ **Important** : Changez le mot de passe au premier login.

**Configuration initiale** :

1. **Générer un token** :
    - My Account → Security → Generate Tokens
    - Name : `jenkins-token`
    - Type : Global Analysis Token
    - **Copiez le token**

2. **Ajouter le token dans Jenkins** :
    - Manage Jenkins → Credentials → Global → Add Credentials
    - Kind : Secret text
    - Secret : [VOTRE_TOKEN]
    - ID : `sonar-token`

3. **Configurer le serveur SonarQube** :
    - Manage Jenkins → Configure System → SonarQube servers
    - Name : `SonarQube`
    - Server URL : `http://sonarqube:9000`
    - Token : Sélectionner `sonar-token`

Voir le fichier complet : [sonar-setup.md](sonar-setup. md)

---

### 📦 Nexus Repository

**URL** : http://localhost:8081

**Récupérer le mot de passe initial** :

```bash
docker exec nexus cat /nexus-data/admin. password
```

**Identifiants** :
- Username : `admin`
- Password : [celui récupéré ci-dessus]

**Configuration Maven** :

Ajouter dans votre `pom.xml` :

```xml
<distributionManagement>
    <repository>
        <id>nexus-releases</id>
        <url>http://localhost:8081/repository/maven-releases/</url>
    </repository>
    <snapshotRepository>
        <id>nexus-snapshots</id>
        <url>http://localhost:8081/repository/maven-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

---

## 🔄 Pipeline CI/CD

### Architecture du pipeline

```
┌─────────────┐
│  Checkout   │  Clone du code depuis GitHub
└──────┬──────┘
       │
┌──────▼──────┐
│Set Env (JDK)│  Configuration Java 17 + Maven 3.9
└──────┬──────┘
       │
┌──────▼──────┐
│    Build    │  mvn clean compile
└──────┬──────┘
       │
┌──────▼──────┐
│ Unit Tests  │  mvn test + JUnit reports
└──────┬──────┘
       │
┌──────▼──────┐
│   JaCoCo    │  Code coverage analysis
└──────┬──────┘
       │
┌──────▼──────┐
│  SonarQube  │  Quality analysis
└──────┬──────┘
       │
┌──────▼──────┐
│Quality Gate │  Wait for SonarQube verdict
└──────┬──────┘
       │
┌──────▼──────┐
│   Package   │  mvn package → JAR file
└──────┬──────┘
       │
┌──────▼──────┐
│Docker Build │  Build Docker image
└──────┬──────┘
       │
┌──────▼──────┐
│Docker Push  │  Push to registry (only main)
└─────────────┘
```

### Stages du pipeline

| Stage | Description | Durée |
|-------|-------------|-------|
| Checkout | Clone du code | ~10s |
| Set Environment | Configuration JDK/Maven | ~5s |
| Build | Compilation Maven | ~30s |
| Unit Tests | Tests unitaires | ~20s |
| JaCoCo Coverage | Analyse de couverture | ~15s |
| SonarQube Analysis | Analyse qualité | ~45s |
| Quality Gate | Validation SonarQube | ~30s |
| Package | Création du JAR | ~20s |
| Docker Build | Build de l'image | ~40s |
| Docker Push | Push vers registry | ~30s |

**Durée totale** : ~4-5 minutes

---

## ▶️ Exécuter le pipeline

### Via l'interface Jenkins

1. Accédez à http://localhost:8080
2. Cliquez sur le job `refactored-finance-pipeline`
3. Cliquez sur **Build Now**
4. Suivez le build dans **Blue Ocean** (vue moderne)

### Via CLI (optionnel)

```bash
curl -X POST http://admin:admin@localhost:8080/job/refactored-finance-pipeline/build
```

### Déclencher automatiquement

Configurez un **GitHub webhook** :
1. GitHub → Repository → Settings → Webhooks
2. Payload URL : `http://YOUR_JENKINS_IP:8080/github-webhook/`
3. Content type : `application/json`
4. Events : `Just the push event`

---

## 📊 Métriques et rapports

### SonarQube Dashboard

Accédez à : http://localhost:9000/dashboard? id=refactored-finance

**Métriques clés** :
- **Bugs** : Erreurs détectées
- **Vulnerabilities** : Failles de sécurité
- **Code Smells** : Mauvaises pratiques
- **Coverage** : Couverture de tests (objectif : >60%)
- **Duplications** : Code dupliqué (objectif : <5%)

### JaCoCo Reports

Dans Jenkins, après un build :
- **Workspace** → `target/site/jacoco/index.html`

### JUnit Reports

Dans Jenkins :
- **Test Result** → Affichage des tests passés/échoués

---

## 🛠️ Commandes utiles

### Docker Compose

```bash
docker compose up -d          # Démarrer tous les services
docker compose down           # Arrêter tous les services
docker compose down -v        # Arrêter + supprimer volumes
docker compose ps             # Statut des services
docker compose logs -f jenkins  # Logs Jenkins en temps réel
docker compose restart jenkins  # Redémarrer Jenkins
```

### Maven

```bash
mvn clean compile             # Compiler le projet
mvn test                      # Exécuter les tests
mvn package                   # Créer le JAR
mvn sonar:sonar               # Lancer SonarQube analysis
mvn clean install             # Build + install dans . m2
```

### Docker

```bash
docker ps                     # Conteneurs actifs
docker images                 # Images disponibles
docker logs -f jenkins        # Logs conteneur
docker exec -it jenkins bash  # Shell dans le conteneur
docker system prune -a        # Nettoyer tout Docker
```

---

## 🐛 Troubleshooting

### Jenkins ne démarre pas

```bash
docker compose logs jenkins
```

**Solution** : Augmentez la mémoire Docker à 8GB minimum.

### SonarQube reste en "Starting"

```bash
docker compose logs sonarqube
```

**Raisons possibles** :
- PostgreSQL pas prêt → Attendez 2-3 minutes
- Mémoire insuffisante → Augmentez RAM

### Quality Gate échoue

**Vérifiez** :
1. Logs SonarQube : http://localhost:9000
2. Métriques du projet
3. Seuils du Quality Gate

**Solution** :
- Améliorez le code
- Ajoutez des tests
- Ou ajustez les seuils (temporairement)

### Maven build échoue

```bash
docker exec -it jenkins bash
mvn clean install -X
```

**Solutions courantes** :
- Proxy/firewall bloquant Maven Central
- Dépendances manquantes
- Version Java incorrecte

### Token SonarQube invalide

1. Régénérez un token dans SonarQube
2.  Mettez à jour le credential dans Jenkins
3. Relancez le build

---

## 🔐 Sécurité

### Changements recommandés pour la production

1. **Jenkins** :
    - Changer admin/admin
    - Activer HTTPS
    - Configurer RBAC

2. **SonarQube** :
    - Changer admin/admin
    - Utiliser LDAP/SSO
    - Activer force authentication

3. **Nexus** :
    - Changer le mot de passe admin
    - Configurer des rôles
    - Activer HTTPS

4. **Docker** :
    - Ne pas exposer Docker socket en prod
    - Utiliser secrets Docker pour les credentials

---

## 📦 Backup & Restore

### Backup des volumes

```bash
docker run --rm \
  -v refactored-finance_jenkins_home:/data \
  -v $(pwd):/backup \
  alpine tar czf /backup/jenkins-backup.tar.gz /data
```

### Restore

```bash
docker run --rm \
  -v refactored-finance_jenkins_home:/data \
  -v $(pwd):/backup \
  alpine tar xzf /backup/jenkins-backup.tar.gz -C /
```

---

## 🎓 Workflow de développement

Consultez le fichier complet : [devops-workflow.md](devops-workflow. md)

**Résumé** :
1. `git checkout -b feature/my-feature`
2.  Développement + commits
3. `git push origin feature/my-feature`
4.  Créer Pull Request vers `develop`
5. CI/CD s'exécute automatiquement
6. Code review
7. Merge

---

## 📚 Ressources

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Maven Documentation](https://maven.apache. org/guides/)
- [Docker Documentation](https://docs.docker.com/)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)

---

## 📞 Support

Pour toute question :
- 📧 Email : dev-team@refactored-finance.com
- 💬 Slack : #devops-support
- 🐛 Issues : https://github.com/Daddaabdelghafour/refactored-finance/issues

---

## 📝 Changelog

### v1.0.0 - 2025-12-08
- ✅ Configuration initiale DevOps
- ✅ Jenkins + SonarQube + Nexus
- ✅ Pipeline CI/CD complet
- ✅ Documentation complète

---

**Fait avec ❤️ par l'équipe DevOps**