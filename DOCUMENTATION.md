## 10 nouveaux tests pour le cours IFT3913

### Méthodes et tests documentés

#### 1. `fulfillsMinimumRequirements(CharSequence)`
- **Localisation :** `org.cryptomator.ui.changepassword.PasswordStrengthUtil` 
  
- **Test associé :** `testFulfillsMinimumRequirements_PasswordTooShort` [Lien](src/test/java/org/cryptomator/ui/changepassword/PasswordStrengthUtilTest.java)
  - **Choix de la méthode :**  
    La longueur du mot de passe est un critère fondamental pour la sécurité des utilisateurs. Ce test permet de vérifier qu'une des premières barrières de sécurité (longueur minimale) fonctionne correctement.

#### 2. `getStrengthDescription(Number)`
- **Localisation :** `org.cryptomator.ui.changepassword.PasswordStrengthUtil`
  
- **Test associé :** `testGetStrengthDescription_PasswordTooShort` [Lien](src/test/java/org/cryptomator/ui/changepassword/PasswordStrengthUtilTest.java)
  - **Choix de la méthode :**  
    Tester les messages de retour pour des mots de passe trop courts est important pour offrir des retours utilisateurs précis et améliorer l'expérience utilisateur. Ce test garantit que les messages d'erreur sont clairs et bien formatés.

#### 3. `getStrengthDescription(Number)`
- **Localisation :** `org.cryptomator.ui.changepassword.PasswordStrengthUtil`

- **Test associé :** `testGetStrengthDescription_ValidScore` [Lien](src/test/java/org/cryptomator/ui/changepassword/PasswordStrengthUtilTest.java)
  - **Choix de la méthode :**  
    Assurer la correspondance entre les scores de force des mots de passe et les descriptions textuelles est crucial pour offrir des retours utilisateurs clairs et compréhensibles. Ce test vérifie que chaque score valide renvoie la bonne description.

#### 4. `revealAccessLocation()`
- **Localisation :** `org.cryptomator.ui.mainwindow.VaultDetailUnlockedController`

- **Test associé :** `testRevealAccessLocation` [Lien](src/test/java/org/cryptomator/ui/mainwindow/VaultDetailUnlockedControllerTest.java)
  - **Choix de la méthode :**  
    Tester cette interaction est essentiel pour s'assurer que le contrôleur effectue correctement la demande d'affichage de l'emplacement d'accès du coffre-fort. Ce test garantit que le bon service est appelé avec les bons paramètres.

#### 5. `lock()`
- **Localisation :** `org.cryptomator.ui.mainwindow.VaultDetailUnlockedController`
  
- **Test associé :** `testLock` [Lien](src/test/java/org/cryptomator/ui/mainwindow/VaultDetailUnlockedControllerTest.java)
  - **Choix de la méthode :**  
    Tester cette interaction garantit que le processus de verrouillage d'un coffre est correctement initié et que l'interface utilisateur (fenêtre principale) est correctement prise en compte. Cela permet de vérifier que l'application fonctionne comme prévu lorsqu'un coffre est verrouillé.

#### 6. `deletePassphrase(String key)`
- **Localisation :** `org.cryptomator.common.keychain.KeychainManager`

- **Test associé :** `testDeletePassphrase` [Lien](src/test/java/org/cryptomator/common/keychain/KeychainManagerTest.java)
  - **Choix de la méthode :**  
    Il est important de tester la capacité du gestionnaire de clés à supprimer des passphrases pour garantir la sécurité des données sensibles. Ce test couvre à la fois le stockage, la récupération, et la suppression de la passphrase.

#### 7. `changePassphrase(String key, String description, String newPassphrase)`
- **Localisation :** `org.cryptomator.common.keychain.KeychainManager`
  
- **Test associé :** `testChangePassphraseWithFaker` [Lien](src/test/java/org/cryptomator/common/keychain/KeychainManagerTest.java)
  - **Choix de la méthode :**  
    Tester la modification des passphrases est essentiel pour garantir la sécurité des données sensibles. L’utilisation de données aléatoires générées par `Faker` permet de tester divers scénarios réalistes.

#### 8. `finish()`
- **Localisation :** `org.cryptomator.ui.forgetpassword.ForgetPasswordController`
  
- **Test associé :** `testFinishKeychainSupported` [Lien](src/test/java/org/cryptomator/ui/forgetpassword/ForgetPasswordControllerTest.java)
  - **Choix de la méthode :**  
    Il est important de tester cette interaction pour garantir que les passphrases sont correctement supprimées et que l'interface utilisateur se ferme comme prévu lorsque le gestionnaire de clés est pris en charge.

#### 9. `finish()`
- **Localisation :** `org.cryptomator.ui.forgetpassword.ForgetPasswordController`
  
- **Test associé :** `testFinishDeletePassphraseFails` [Lien](src/test/java/org/cryptomator/ui/forgetpassword/ForgetPasswordControllerTest.java)
  - **Choix de la méthode :**  
    Tester la gestion des exceptions est essentiel pour garantir que l'application reste robuste même en cas d'erreurs. Ce test s'assure que la suppression de la passphrase échouée est correctement gérée, sans laisser l'interface dans un état incohérent.

#### 10. `serialized()`
- **Localisation :** `org.cryptomator.common.settings.VaultSettings` [Lien](src/test/java/org/cryptomator/common/settings/VaultSettingsTest.java)

- **Test associé :** `testSerializedWithFaker`
  - **Choix de la méthode :**
    La sérialisation des paramètres du coffre est cruciale pour la sauvegarde et le chargement des configurations utilisateur. Tester cette méthode avec des données variées assure que tous les champs sont correctement pris en compte.
