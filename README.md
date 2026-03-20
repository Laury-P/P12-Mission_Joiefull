# Projet 12 – Créez une interface dynamique et accessible avec Jetpack Compose

## 📌 Contexte
Projet réalisé dans le cadre de ma formation **OpenClassrooms – Développeur d’Application Android**.

L'objectif principal était de transformer une interface statique en une application **dynamique, réactive et inclusive** en utilisant les standards modernes du développement Android. Ce projet simule une situation réelle où le développeur doit intégrer des données issues d'une API tout en garantissant une expérience utilisateur optimale sur différents formats d'écran (Téléphone et Tablette).

---

## 📸 Aperçu du projet
| Format Téléphone | Format Tablette (Dual-Pane) |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/5b79abbb-14b3-491d-8c2f-a913d9adb94a" width="300" /> <br> <img src="https://github.com/user-attachments/assets/ab3ba8fe-396e-4b8e-af83-b9c492b3f9eb" width="300" /> | <img src="https://github.com/user-attachments/assets/dbffe117-110a-40bc-b076-03fd58e6e750" width="600" /> |
 


---

## 🎯 Objectifs pédagogiques
- **Maîtrise de Jetpack Compose** : Création d'interfaces déclaratives, modulaires et performantes.
- **Architecture Moderne** : Implémentation du pattern **MVVM** combiné aux principes de la **Clean Architecture**.
- **Accessibilité (A11y)** : Optimisation complète du parcours utilisateur pour les lecteurs d'écran (**TalkBack**).
- **Interface Adaptative** : Conception d'un layout dynamique s'ajustant aux différentes tailles d'écran.

---

## ⚙️ Stack technique
- **Langage** : Kotlin
- **UI** : Jetpack Compose (Material 3)
- **Architecture** : MVVM, Repository Pattern, Clean Architecture
- **Gestion des données** : StateFlow, Flow, Coroutines
- **Réseau** : Retrofit, OkHttp, Moshi
- **Injection de dépendances** : Hilt
- **Chargement d'images** : Coil

---

## 🧩 Fonctionnalités implémentées
- **Catalogue Dynamique** : Récupération et affichage des produits via une API REST.
- **Navigation Fluide** : Passage du catalogue au détail avec gestion d'état optimisée.
- **Gestion Hybride des Données** : Orchestration entre données réelles (API) et données simulées (**Mocks**) pour les fonctionnalités interactives (favoris, avis).
- **Expérience Tablette** : Affichage simultané de la liste et du détail (Dual-Pane) pour exploiter l'espace disponible.
- **Accessibilité Avancée** : 
  - Regroupement sémantique des éléments (Semantics).
  - Descriptions d'images pertinentes et masquage des éléments décoratifs.
  - Respect strict des zones de clic minimales (48dp).

---

## 🧠 Apprentissages et compétences développées
- **UDF (Unidirectional Data Flow)** : Maîtrise du flux de données unique garantissant la cohérence de l'UI.
- **Découplage et Modularité** : Utilisation de **Hilt** pour faciliter la maintenance et l'évolution technique.
- **Logique de Mapping** : Conversion des données brutes (DTO) en modèles de domaine "purifiés" pour isoler la logique métier.
- **Audit d'Accessibilité** : Capacité à analyser une interface avec *Accessibility Scanner* et à prioriser le confort de navigation sonore.

---

## 🔍 Limites du projet
- **Persistance en mémoire** : Les favoris et commentaires sont stockés en RAM et réinitialisés à chaque fermeture de l'application.
- **Données en lecture seule** : L'API étant en consultation seule (GET), les interactions utilisateur sont simulées en local via le Repository.

---

## 💡 Ouvertures et pistes d’amélioration
- **Base de données locale** : Intégration de **Room** pour persister les favoris et commentaires hors-ligne.
- **Backend fonctionnel** : Remplacement des mocks par une API complète supportant les méthodes POST/PATCH.
- **Internationalisation** : Migration des textes codés en dur vers un fichier `strings.xml` pour supporter plusieurs langues.
- **Tests Automatisés** : Ajout de tests unitaires (JUnit/MockK) et de tests d'interface (Compose Test).

---

## 📚 Ressources et références
- Cours OpenClassrooms : *Utilisez Jetpack Compose pour développer des interfaces Android dynamiques*.
- Documentation officielle Google : *Jetpack Compose Accessibility* & *Modern Android Architecture*.

---

## 👤 Auteur
Projet réalisé individuellement par **[Laury PRIN]**, dans un cadre pédagogique.
