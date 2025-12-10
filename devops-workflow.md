# 🌿 Git Branch Workflow - refactored-finance

## 📋 Stratégie de branches

### Structure des branches

```
main (production)
  ↑
develop (intégration)
  ↑
  ├── devops-setup (CI/CD infrastructure)
  ├── refactoring-app (refactoring du code)
  └── feature/* (nouvelles fonctionnalités)
```

---

## 🔀 Types de branches

### 1. **main**
- Branche de production
- Code stable uniquement
- Protected branch
- Merge via Pull Request uniquement
- Déploiement automatique en production

### 2. **develop**
- Branche d'intégration
- Merge de toutes les features
- Tests complets avant merge vers main
- Protected branch

### 3. **devops-setup**
- Configuration CI/CD
- Docker, Jenkins, SonarQube
- Merge vers develop après validation

### 4. **refactoring-app**
- Refactoring du code Java
- Amélioration de la qualité
- Merge vers develop

### 5. **feature/***
- Nouvelles fonctionnalités
- Nommage : `feature/nom-de-la-fonctionnalite`
- Merge vers develop

### 6. **bugfix/***
- Corrections de bugs
- Nommage : `bugfix/description-du-bug`
- Merge vers develop

### 7. **hotfix/***
- Corrections urgentes en production
- Part de main
- Merge vers main ET develop

---

## 🚀 Workflow de développement

### Créer une nouvelle branche

```bash
git checkout develop
git pull origin develop
git checkout -b feature/my-new-feature
```

### Travailler sur la branche

```bash
git add .
git commit -m "feat: add new feature"
git push origin feature/my-new-feature
```

### Créer une Pull Request

1. Aller sur GitHub
2. **Pull Requests** → **New Pull Request**
3. Base : `develop` ← Compare : `feature/my-new-feature`
4. Remplir la description
5. Assigner des reviewers
6. Créer la PR

### Merger la Pull Request

1. Attendre les checks CI/CD
2. Attendre l'approbation des reviewers
3. Résoudre les conflits si nécessaire
4. **Squash and merge** ou **Merge commit**
5. Supprimer la branche après merge

---

## 📝 Convention de commits

Nous utilisons la convention **Conventional Commits**.

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

| Type | Description | Exemple |
|------|-------------|---------|
| `feat` | Nouvelle fonctionnalité | `feat(auth): add login endpoint` |
| `fix` | Correction de bug | `fix(api): resolve null pointer exception` |
| `docs` | Documentation | `docs(readme): update installation steps` |
| `style` | Formatage code | `style(app): fix indentation` |
| `refactor` | Refactoring | `refactor(service): simplify logic` |
| `test` | Ajout de tests | `test(user): add unit tests` |
| `chore` | Tâches diverses | `chore(deps): update dependencies` |
| `ci` | CI/CD | `ci(jenkins): add sonarqube stage` |
| `perf` | Performance | `perf(db): optimize query` |

### Exemples complets

```bash
feat(payment): add stripe payment integration

- Add Stripe SDK dependency
- Create PaymentService
- Add payment endpoints
- Add unit tests

Closes #123
```

```bash
fix(security): patch XSS vulnerability

The user input was not properly sanitized. 
This could lead to XSS attacks. 

Fixes #456
```

---

## 🛡️ Protection des branches

### Règles pour `main`

- ✅ Require pull request before merging
- ✅ Require approvals (minimum 2)
- ✅ Dismiss stale pull request approvals
- ✅ Require status checks to pass (CI/CD)
- ✅ Require branches to be up to date
- ✅ Require linear history
- ✅ Do not allow bypassing the above settings

### Règles pour `develop`

- ✅ Require pull request before merging
- ✅ Require approvals (minimum 1)
- ✅ Require status checks to pass
- ✅ Require branches to be up to date

---

## 🔄 Pull Request Process

### Checklist avant de créer une PR

- [ ] Mon code compile sans erreurs
- [ ] Tous les tests passent
- [ ] J'ai ajouté des tests pour mon code
- [ ] J'ai mis à jour la documentation
- [ ] Mon code respecte les conventions
- [ ] J'ai résolu tous les conflits
- [ ] La CI/CD passe (build, tests, sonar)

### Template de Pull Request

```markdown
## 📝 Description
Brève description de ce qui a été fait. 

## 🎯 Type de changement
- [ ] Bug fix
- [ ] Nouvelle fonctionnalité
- [ ] Refactoring
- [ ] Documentation
- [ ] CI/CD

## 🧪 Tests
Comment tester ce changement ? 

## 📸 Screenshots (si applicable)
Ajouter des captures d'écran si UI. 

## ✅ Checklist
- [ ] Code compile
- [ ] Tests passent
- [ ] Documentation mise à jour
- [ ] Pas de conflits

## 📌 Issues liées
Closes #123
```

---

## 🏷️ Versioning

Nous suivons **Semantic Versioning** (SemVer) : `MAJOR.MINOR. PATCH`

### Format

```
v1.2.3
 │ │ │
 │ │ └─ PATCH : bug fixes
 │ └─── MINOR : nouvelles fonctionnalités (compatible)
 └───── MAJOR : breaking changes
```

### Exemples

- `v1.0.0` : première version stable
- `v1.1. 0` : ajout de fonctionnalités
- `v1.1.1` : correction de bugs
- `v2.0.0` : breaking changes

### Tagging

```bash
git checkout main
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

---

## 🔥 Hotfix workflow

Pour les corrections urgentes en production :

```bash
git checkout main
git pull origin main
git checkout -b hotfix/critical-bug

git add .
git commit -m "hotfix: resolve critical security issue"
git push origin hotfix/critical-bug
```

Créer **2 Pull Requests** :
1. `hotfix/critical-bug` → `main`
2. `hotfix/critical-bug` → `develop`

---

## 📊 Diagramme du workflow
```
┌─────────────────────────────────────────────────────────┐
│                         main                            │
│                    (production)                         │
└────────────────────────┬────────────────────────────────┘
                         │
                         │ PR (2 approvals)
                         │
┌────────────────────────▼────────────────────────────────┐
│                       develop                           │
│                   (intégration)                         │
└─┬─────────────┬─────────────┬─────────────┬────────────┘
  │             │             │             │
  │ PR          │ PR          │ PR          │ PR
  │             │             │             │
┌─▼───────┐ ┌──▼──────┐ ┌────▼─────┐ ┌────▼──────┐
│ devops- │ │refactor-│ │ feature/ │ │  bugfix/  │
│  setup  │ │ing-app  │ │   xxx    │ │    xxx    │
└─────────┘ └─────────┘ └──────────┘ └───────────┘
```

---

## 🎓 Bonnes pratiques

1. **Toujours partir de develop** pour une nouvelle branche
2. **Commits fréquents** et atomiques
3. **Messages explicites** (convention commits)
4. **Pull avant push** pour éviter les conflits
5. **Code review obligatoire** avant merge
6.  **Supprimer les branches** après merge
7. **Garder develop à jour** avec main régulièrement

---

## 🐛 Résolution de conflits

```bash
git checkout develop
git pull origin develop
git checkout feature/my-feature
git merge develop

# Résoudre les conflits dans les fichiers
git add .
git commit -m "merge: resolve conflicts with develop"
git push origin feature/my-feature
```

---

## 📚 Ressources

- [Conventional Commits](https://www.conventionalcommits.org/)
- [Semantic Versioning](https://semver.org/)
- [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/)