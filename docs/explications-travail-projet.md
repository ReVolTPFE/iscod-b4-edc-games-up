# Explications des choses effectuées dans le code

## 1. Respect principes SOLID et bonnes/mauvaises pratiques

### SOLID
Le code respecte les principes SOLID et les bonnes pratiques de développement. Voici quelques exemples :

Respect des principes SOLID :
- Chaque classe ne sert qu'à une chose. Que ce soit pour les modèles, services ou controllers, ils sont toujours liés aux actions d'une seule entité.
- Les classes sont ouvertes à l'extension mais fermées à la modification. On peut par exemple ajouter des nouvelles méthodes sans modifier le code existant.
- On peut dériver les classes existantes. Par exemple, une classe `AdminUser` pourrait être utilisée à la place de `User` sans modifier `User` pour autant.

### Bonnes pratiques

- DRY (Don't Repeat Yourself) : Les méthodes des classes sont réutilisables et j'ai tout codé de manière à ne pas répéter le code. Exemples :
    - Toutes les méthodes de controllers retournent une `ApiResponse` et sont donc réutilisables.
    - Certaines méthodes de services sont réutilisées dans plusieurs controllers ou autres services ex : `reviewService.getReviewsByUserId`.
- Architecture REST propre et modulaire
- Utilisation de DTOs pour exposer les données
- Sécurité centralisée avec Spring Security
- Couverture de tests avec JaCoCo
- Intégration de l’IA via API externe

A la base, je suis un développeur Symfony (équivalent PHP de Spring Boot). Les deux frameworks sont similaires donc j'ai appliqué les mêmes bonnes pratiques que j'utilise en Symfony.

### Mauvaises pratiques

Je ne dirais pas qu'il y a des mauvaises pratiques (en tout cas connues de mon côté), car si je les connaissais, j'aurais fait en sorte de ne pas les mettre dans le projet.
Mais il y en a certainement que je ne connais pas. J'ai donc essayé de garder le code le plus propre et lisible possible, en suivant les bonnes pratiques que j'ai apprises sur d'autres expériences pro/perso.

## 2. Cheminement de création du projet

J'ai créé le projet dans l'ordre suivant :
- Création des diagrammes UML à l'aide des informations fournies et de Mermaid UML
- Initialisation du projet Spring selon le code fourni
- Ajout de Docker et Docker Compose pour gérer le projet et la base de données simplement
- Ajout des entités, repositories et enums
- Ajout de la sécurité avec Spring Security et JWT
- Ajout des controllers, services, DTOs et tests unitaires et d'intégration pour chaque entité une par une
    - A chaque fois j'ai ajouté les endpoints REST sur Postman pour tester le bon fonctionnement de chaque route et les réponses en fonction des rôles et données envoyées
- Ajout du code coverage, de la documentation README et des endpoints API dans un fichier markdown
- Ajout de la base de l'API Python pour les recommandations ML
- Ajout du code pour faire fonctionner le modèle de recommandation ML KNN
- Ajout des routes de l'API Python sur Spring
- Création de ce fichier résumant mon cheminement sur ce projet

## 3. API Python de recommandations ML

A partir des fichiers donnés, j'ai remodelé le `main.py` pour qu'il y ait ces 2 endpoints :
- `/train` : pour entraîner le modèle de recommandation via données dans le body de la requête POST
- `/recommendations` : pour obtenir les recommandations pour un utilisateur via son id et ses jeux et reviews dessus

Pour le `data_loader.py`, j'ai juste ajouté une méthode permettant de convertir les données envoyées en DataFrame Pandas.

Pour le `models.py`, j'ai utilisé `Pydantic` à la manière de DTOs pour valider les données envoyées et reçues.

Enfin, pour le `recommendation.py`, j'ai implémenté la logique de recommandation en utilisant le modèle KNN de Scikit-learn :
- Le modèle KNN fonctionne sous la forme d'une matrice user_id x game_id.
- J'ai ajouté un système de sauvegarde du modèle entraîné via un volume Docker.
- Il y a une gestion d'erreurs si les données sont au mauvais format ou invalides.
- L'API renvoie les recommandations en fonction du contexte des données :
    - Si aucune donnée d'entraînement, je renvoie la liste des jeux par défaut.
    - Sinon, si l'utilisateur n'a pas noté de jeux qu'il a achetés, je renvoie la liste des jeux les plus populaires.
    - Sinon, je renvoie les recommandations de jeux basées sur les notes de l'utilisateur.
