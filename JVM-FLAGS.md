## Documentation des JVM Flags dans la GitHub action pour le cours IFT3913

### Changements apportés à la [GitHub action](.github/workflows/test.yml)

La configuration de la GitHub Action a été mise à jour pour permettre l’exécution des tests avec cinq flags JVM différents en utilisant une **matrice**. Cela garantit que chaque flag est testé de manière isolée, améliorant ainsi la couverture des cas de test et fournissant des informations spécifiques sur leurs impacts respectifs.

Les principales modifications incluent :
- **Matrice des Flags JVM :** Une matrice a été définie avec cinq flags JVM (`-XX:+UseG1GC`, `-XX:+HeapDumpOnOutOfMemoryError`, `-XX:-AllowUserSignalHandlers`, `-XX:+EliminateLocks`, `-XX:+TieredCompilation`) pour exécuter les tests de manière indépendante avec chaque flag.
- **Passage des Flags à Maven :** Les flags sont transmis à Maven via la variable d’environnement `MAVEN_OPTS` pour configurer dynamiquement l’environnement d’exécution.
- **Réinitialisation des Flags :** Après chaque exécution, les options Maven sont réinitialisées à l’aide de la commande `unset MAVEN_OPTS` pour éviter toute interférence avec les autres exécutions.
- **Logs clairs :** Chaque étape documente explicitement le flag utilisé via des messages `echo`, facilitant le diagnostic des problèmes et l’analyse des résultats.

### Justification du choix des JVM Flags

#### 1. `-XX:+UseG1GC`
**Description :**
Ce flag active le Garbage Collector G1 (Garbage-First). G1GC est conçu pour offrir des temps de pause plus prévisibles en divisant le tas mémoire en régions, et en collectant prioritairement celles qui contiennent le plus de déchets.

**Impact :**
- **Qualité :** G1GC améliore la réactivité de l’application en minimisant les longues pauses dues à la gestion mémoire, ce qui réduit les risques de bugs liés à des délais inattendus.
- **Performance :** En réduisant les temps de pause et en optimisant la gestion mémoire, G1GC augmente les performances globales, surtout pour les applications avec de grands tas (>4 Go).
- **Observabilité :** G1GC fournit des informations granulaires sur les performances de la gestion mémoire grâce à des logs détaillés, ce qui facilite le diagnostic et l’optimisation.

---

#### 2. `-XX:+HeapDumpOnOutOfMemoryError`
**Description :**
Ce flag génère automatiquement un heap dump lorsqu’une erreur OutOfMemoryError survient. Un heap dump est une capture de l’état de la mémoire de l’application à un moment donné.

**Impact :**
- **Qualité :** Permet d’identifier les fuites de mémoire ou les erreurs critiques liées à la gestion de la mémoire, ce qui contribue à la fiabilité de l’application.
- **Performance :** Bien que le heap dump puisse ralentir légèrement le système lors de son écriture, il est essentiel pour résoudre les problèmes graves qui pourraient entraîner des interruptions prolongées.
- **Observabilité :** Le heap dump fournit une vue détaillée de la mémoire, essentielle pour le débogage et l’analyse post-mortem des erreurs critiques.

---

#### 3. `-XX:-AllowUserSignalHandlers`
**Description :**
Ce flag empêche les utilisateurs de redéfinir les gestionnaires de signaux dans l’application, tels que SIGSEGV ou SIGTERM.

**Impact :**
- **Qualité :** Réduit le risque de comportement non déterministe causé par des gestionnaires de signaux mal implémentés ou non conformes, améliorant la robustesse.
- **Performance :** En limitant la personnalisation des signaux, ce flag garantit un comportement plus prévisible, mais il peut limiter certaines optimisations spécifiques aux applications.
- **Observabilité :** Facilite l’analyse des crashs et des interruptions, car les signaux sont gérés de manière standard par la JVM.

---

#### 4. `-XX:+EliminateLocks`
**Description :**
Ce flag active l’élimination des verrous inutiles lors de l’optimisation du code par le compilateur JIT (Just-In-Time). Cela concerne principalement les blocs de synchronisation qui ne peuvent jamais être atteints de manière concurrente.

**Impact :**
- **Qualité :** Réduit les risques d’impasses et d’erreurs liées à la synchronisation excessive, améliorant la fiabilité du code.
- **Performance :** En éliminant les verrous inutiles, ce flag réduit les surcoûts liés à la synchronisation, ce qui améliore les performances des applications monothreadées ou faiblement concurrentes.
- **Observabilité :** Simplifie l’analyse des performances, car il réduit les points de contention artificiels dans le code.

---

#### 5. `-XX:+TieredCompilation`
**Description :**
Ce flag active la compilation tierée, où la JVM combine les avantages des compilateurs C1 (compilation rapide, optimisation minimale) et C2 (optimisations avancées).
**Impact :**
- **Qualité :** Améliore l’expérience utilisateur en permettant une montée en performances progressive, réduisant les délais initiaux lors de l’exécution.
- **Performance :** La compilation tierée accélère le démarrage tout en fournissant des optimisations avancées à mesure que l’application s’exécute, ce qui maximise les performances à long terme.
- **Observabilité :** La compilation tierée fournit des informations sur l’optimisation en cours, ce qui est utile pour analyser et ajuster les performances.