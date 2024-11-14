## Documentation des JVM Flags dans la GitHub action pour le cours IFT3913

## Changements apportés à la GitHub action

### Principales modifications
L'action GitHub a été mise à jour pour intégrer une stratégie matricielle permettant d'exécuter des tests unitaires avec cinq flags JVM distincts. Les principales modifications incluent :

1. **Matrice des flags JVM :**
    - Une stratégie matricielle a été ajoutée pour tester chaque flag séparément et évaluer son impact sur la qualité et les performances.

2. **Intégration de flags JVM dynamiques :**
    - L'étape `Build and Test` utilise le flag courant, passé dynamiquement via `matrix.jvm_flag`, pour adapter le comportement du runtime JVM à chaque exécution.