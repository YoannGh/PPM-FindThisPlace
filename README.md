# Projet PPM 2017/2018 - FindThisPlace

## Auteurs

- Yoann Ghigoff (3506454)
- Florian Reynier (3506673)

## Architecture du projet

### Packages

- activities
    + GameMapActivity
    + MainActivity
    + ScoreActivity
- adapters
    + ScoreAdapter
- fragments
    + ScoreFragment
- game
    + CircumferenceBasedScoreCalculator
    + CountryBasedScoreCalculator
    + GameDifficulty
    + GameMode
    + IScoreCalculatorStrategy
    + ReverseCircumferenceScoreCalculator
- model
    + StaticPlaces
    + PlacesViewModel
    + PlayerProfile
    + PlayerProfileViewModel
    + Score
    + ScoreViewModel
- util
    + DistanceUtil

### Navigation

Depuis MainActivity il est possible d'accéder à ScoreActivity
et GameMapActivity.

Aucune activité n'est lancée depuis ces dernières (hormis le retour à
la MainActivity).

### Classes

###### GameMapActivity

Activity responsable du controle des fragments GoogleMap et StreetViewPanorama
et de la logique du jeu (via les méthode privées principalement).

###### MainActivity

Activity de démarrage, elle permet de choisir/créer un profil de joueur,
d'accéder à l'activité listant les scores et de commencer une partie.

###### ScoreActivity

Activity contenant un simple fragment **ScoreFragment**, ne contient aucun
traitement.

###### ScoreAdapter

Adapter pour les données de score.

###### ScoreFragment

Fragment contenant la ListView des scores.

###### GameDifficulty

Enum décrivant les différentes difficultés.
- NOVICE
- MEDIUM
- EXPERT

###### GameMode

Enum décrivant les différents mode de jeu.
- CLASSIC (trouver le bon lieu)
- COUNTRY (trouver le bon pays)
- REVERSE (trouver le lieu le plus éloigné)

###### IScoreCalculatorStrategy

Interface définissant une unique méthode
implémentée par les 3 classes qui suivent:

```java
long calculateScore(LatLng expected, LatLng guess, double distance, GameDifficulty difficulty)
```

L'instanciation de la stratégie de calcul de score se fait à la création
de l'activité **GameMapActivity** en fonction du mode de jeu choisi par
l'utilisateur.

L'utilisation du design pattern Strategy permet d'éviter l'accumulation
de blocs *if else* dans le code de l'activité.

###### CircumferenceBasedScoreCalculator

Classe implémentant **IScoreCalculatorStrategy**.

Elle est utilisée si l'utilisateur à choisi le mode **CLASSIC** et
calcule le score en prenant comme score maximal la moitié de la
circonférence de la Terre puis en réduisant le paramètre *distance*.

###### CountryBasedScoreCalculator

Classe implémentant **IScoreCalculatorStrategy**.

Elle est utilisée si l'utilisateur à choisi le mode **COUNTRY** et
calcule le score en renvoyant le score maximal si les coordonnées
*expected* et *guess* correspondent au même pays, 0 sinon.

###### ReverseCircumferenceScoreCalculator

Classe implémentant **IScoreCalculatorStrategy**.

Elle est utilisée si l'utilisateur à choisi le mode **REVERSE** et
calcule le score en renvoyant la distance entre les coordonnées
*expected* et *guess*.

###### StaticPlaces

Classe contenant la liste statique des coordonnées GPS de lieux à faire
deviner.

###### PlacesViewModel

Classe étendant **ViewModel**.

Elle permet garder en mémoire, pendant toute la durée de vie de
l'activité **GameMapActivity**, les lieux choisis aléatoirement
lors du démarrage d'une partie afin d'éviter de les perdre si
l'activité redémarre suite à un changement de configuration du téléphone.

###### PlayerProfile

Classe implémentant **Serializable**.

Elle contient les données persistantes d'un profil de joueur.

###### PlayerProfileViewModel

Classe étendant **AndroidViewModel**.

Elle permet de charger/sauvegarder dans un fichier une liste de
**PlayerProfile**.
Elle permet garder en mémoire, pendant toute la durée de vie de
l'activité **MainActivity**, les profils de joueurs afin d'éviter
d'avoir à les recharger depuis le fichier si l'activité redémarre suite
à un changement de configuration du téléphone.

###### Score

Classe implémentant **Serializable**.

Elle contient les données persistantes d'un score.

###### ScoreViewModel

Classe étendant **AndroidViewModel**.

Elle permet de charger/sauvegarder dans un fichier une liste de
**Score**.
Elle permet garder en mémoire, pendant toute la durée de vie de
l'activité **ScoreActivity**, les scores afin d'éviter
d'avoir à les recharger depuis le fichier si l'activité redémarre suite
à un changement de configuration du téléphone.

Elle est aussi utilisée par l'activité **GameMapActivity** afin de
sauvegarder les nouveaux scores.

###### DistanceUtil

Classe contenant deux méthodes statiques:

 - *distanceBetweenInKilometers* permettant de calculer la distance
 en kilomètres entre deux coordonnées GPS.
- *distanceToString* permettant de convertir en chaine de caractères
une distance en kilomètres.


## Options implémentées

###### Gestion de la rotation sur l'ensemble des écrans avec affichage dédié pour la carte

La rotation est disponible pour les 3 activités,
avec un affichage vertical des fragments de carte et streetview pour
l'orientation portrait du téléphone et un affichage horizontal de
ces derniers pour l'orientation paysage.

L'activité **MainAcitivity** dispose aussi d'un affichage spécifique
pour les deux orientations.

###### Persistence durable

Les scores et les profils de joueur sont sauvegardées dans des fichiers.

Le dernier profil de joueur utilisé est sauvegardé dans les
SharedPreferences de l'application.

Les variables des activités sont sauvegardées/restaurées à chaque
changement de configuration du téléphone.

###### Animation lorsqu'un utilisateur saisit une position GPS

Lorsqu'un utilisateur valide sa réponse pour le lieu à deviner,
un zoom est réalisé sur la carte sur une zone géographique contenant
la coordonnée devinée et la coordonnée correcte, lui montrant ainsi
la bonne réponse et la distance le séparant de celle-ci (via un tracé
sur la carte).

###### Profil utilisateur nom/prénom/avec possibilité de le modifier

Sur l'écran de l'activité **MainActivity**, l'utilisateur a la
possibilité de choisir son profil de joueur ou d'en créer un nouveau
en renseignant son prénom et son nom. Il n'a cependant pas la
possibilité des modifier ceux qui existent déjà.

###### Mode de jeu dans lequel si l'utilisateur est dans le bon pays c'est gagné

Mode de jeu appelé **COUNTRY** dans l'énumération **GameMode** et
implémenté par la classe **CountryBasedScoreCalculator**.

###### Mode de jeu inversé le plus loin gagne le plus de points

Mode de jeu appelé **REVERSE** dans l'énumération **GameMode** et
implémenté par la classe **ReverseCircumferenceScoreCalculator**.


## Difficultés rencontrées

Le projet n'a pas été très difficile dans l'ensemble.

Nous aurions cependant voulu implémenter un système ne nécessitant pas
la définition statique des coordonées GPS des lieux à deviner, notamment
via une API Web d'opendata, afin de maximiser la rejouabilité.

Nous n'avons malheureusement pas trouver d'API diffusant des coordonnées GPS permettant
d'obtenir une vue valide (et non un écran noir) dans le fragment
StreetViewPanorama à coup sûr.
