[![cryptomator](cryptomator.png)](https://cryptomator.org/)

[![Build](https://github.com/cryptomator/cryptomator/workflows/Build/badge.svg)](https://github.com/cryptomator/cryptomator/actions?query=workflow%3ABuild)
[![Known Vulnerabilities](https://snyk.io/test/github/cryptomator/cryptomator/badge.svg)](https://snyk.io/test/github/cryptomator/cryptomator)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=cryptomator_cryptomator&metric=alert_status)](https://sonarcloud.io/dashboard?id=cryptomator_cryptomator)
[![Twitter](https://img.shields.io/badge/twitter-@Cryptomator-blue.svg?style=flat)](http://twitter.com/Cryptomator)
[![Crowdin](https://badges.crowdin.net/cryptomator/localized.svg)](https://translate.cryptomator.org/)
[![Latest Release](https://img.shields.io/github/release/cryptomator/cryptomator.svg)](https://github.com/cryptomator/cryptomator/releases/latest)
[![Community](https://img.shields.io/badge/help-Community-orange.svg)](https://community.cryptomator.org)

-----

## Instructions pour le cours IFT3913

Vous pouvez build le projet et générer les rapports jacoco coverage en exécutant la commande suivante.

```
mvn verify -Pcoverage
```

-----

## 10 nouveaux tests pour le cours IFT3913

### Méthodes et tests documentés

#### 1. `fulfillsMinimumRequirements(CharSequence)`
- **Localisation :** `org.cryptomator.ui.changepassword.PasswordStrengthUtil`
  
- **Test associé :** `testFulfillsMinimumRequirements_PasswordTooShort`    
  - **Choix de la méthode :**  
    La longueur du mot de passe est un critère fondamental pour la sécurité des utilisateurs. Ce test permet de vérifier qu'une des premières barrières de sécurité (longueur minimale) fonctionne correctement.

#### 2. `getStrengthDescription(Number)`
- **Localisation :** `org.cryptomator.ui.changepassword.PasswordStrengthUtil`
  
- **Test associé :** `testGetStrengthDescription_PasswordTooShort`    
  - **Choix de la méthode :**  
    Tester les messages de retour pour des mots de passe trop courts est important pour offrir des retours utilisateurs précis et améliorer l'expérience utilisateur. Ce test garantit que les messages d'erreur sont clairs et bien formatés.

#### 3. `getStrengthDescription(Number)`
- **Localisation :** `org.cryptomator.ui.changepassword.PasswordStrengthUtil`

- **Test associé :** `testGetStrengthDescription_ValidScore`
  - **Choix de la méthode :**  
    Assurer la correspondance entre les scores de force des mots de passe et les descriptions textuelles est crucial pour offrir des retours utilisateurs clairs et compréhensibles. Ce test vérifie que chaque score valide renvoie la bonne description.

#### 4. `revealAccessLocation()`
- **Localisation :** `org.cryptomator.ui.mainwindow.VaultDetailUnlockedController`

- **Test associé :** `testRevealAccessLocation`
  - **Choix de la méthode :**  
    Tester cette interaction est essentiel pour s'assurer que le contrôleur effectue correctement la demande d'affichage de l'emplacement d'accès du coffre-fort. Ce test garantit que le bon service est appelé avec les bons paramètres.

#### 5. `lock()`
- **Localisation :** `org.cryptomator.ui.mainwindow.VaultDetailUnlockedController`
  
- **Test associé :** `testLock`
  - **Choix de la méthode :**  
    Tester cette interaction garantit que le processus de verrouillage d'un coffre est correctement initié et que l'interface utilisateur (fenêtre principale) est correctement prise en compte. Cela permet de vérifier que l'application fonctionne comme prévu lorsqu'un coffre est verrouillé.

#### 6. `deletePassphrase(String key)`
- **Localisation :** `org.cryptomator.common.keychain.KeychainManager`

- **Test associé :** `testDeletePassphrase`
  - **Choix de la méthode :**  
    Il est important de tester la capacité du gestionnaire de clés à supprimer des passphrases pour garantir la sécurité des données sensibles. Ce test couvre à la fois le stockage, la récupération, et la suppression de la passphrase.

#### 7. `changePassphrase(String key, String description, String newPassphrase)`
- **Localisation :** `org.cryptomator.common.keychain.KeychainManager`
  
- **Test associé :** `testChangePassphraseWithFaker`
  - **Choix de la méthode :**  
    Tester la modification des passphrases est essentiel pour garantir la sécurité des données sensibles. L’utilisation de données aléatoires générées par `Faker` permet de tester divers scénarios réalistes.

#### 8. `finish()`
- **Localisation :** `org.cryptomator.ui.forgetpassword.ForgetPasswordController`
  
- **Test associé :** `testFinishKeychainSupported`
  - **Choix de la méthode :**  
    Il est important de tester cette interaction pour garantir que les passphrases sont correctement supprimées et que l'interface utilisateur se ferme comme prévu lorsque le gestionnaire de clés est pris en charge.

#### 9. `finish()`
- **Localisation :** `org.cryptomator.ui.forgetpassword.ForgetPasswordController`
  
- **Test associé :** `testFinishDeletePassphraseFails`
  - **Choix de la méthode :**  
    Tester la gestion des exceptions est essentiel pour garantir que l'application reste robuste même en cas d'erreurs. Ce test s'assure que la suppression de la passphrase échouée est correctement gérée, sans laisser l'interface dans un état incohérent.

#### 10. `getApiBaseUrl()`
- **Localisation :** `org.cryptomator.ui.keyloading.hub.HubConfig`
  
- **Test associé :** `testGetApiBaseUrlWithDevicesResourceUrl`
  - **Choix de la méthode :**  
    Il est important de tester ce comportement pour garantir que l'API fonctionne correctement même si `apiBaseUrl` n'est pas explicitement défini. Ce test s'assure que la méthode peut utiliser un fallback (`devicesResourceUrl`) pour dériver l'URL de base.

<br>

Cryptomator is provided free of charge as an open-source project despite the high development effort and is therefore dependent on donations. If you are also interested in further development, we offer you the opportunity to support us:

- [One-time or recurring donation via Cryptomator's website.](https://cryptomator.org/#donate)
- [Become a sponsor via Cryptomator's sponsors website.](https://cryptomator.org/sponsors/)

### Gold Sponsors

<table>
  <tbody>
    <tr>
      <td><a href="https://www.gee-whiz.de/"><img src="https://cryptomator.org/img/sponsors/geewhiz.svg" alt="gee-whiz" height="80"></a></td>
    </tr>
  </tbody>
</table>

### Silver Sponsors

<table>
  <tbody>
    <tr>
      <td><a href="https://mowcapital.com/"><img src="https://cryptomator.org/img/sponsors/mowcapital.svg" alt="Mow Capital" height="28"></a></td>
      <td><a href="https://www.hassmann-it-forensik.de/"><img src="https://cryptomator.org/img/sponsors/hassmannitforensik.png" alt="Hassmann IT-Forensik" height="56"></a></td>
      <td><a href="https://www.route4me.com/"><img src="https://cryptomator.org/img/sponsors/route4me.svg" alt="Route4Me" height="56"></a></td>
    </tr>
  </tbody>
</table>

### Special Shoutout

Continuous integration hosting for ARM64 builds is provided by [MacStadium](https://www.macstadium.com/opensource).

<a href="https://www.macstadium.com/opensource"><img src="https://uploads-ssl.webflow.com/5ac3c046c82724970fc60918/5c019d917bba312af7553b49_MacStadium-developerlogo.png" alt="MacStadium" height="100"></a>

---

## Introduction

Cryptomator offers multi-platform transparent client-side encryption of your files in the cloud.

Download native binaries of Cryptomator on [cryptomator.org](https://cryptomator.org/) or clone and build Cryptomator using Maven (instructions below).

## Features

- Works with Dropbox, Google Drive, OneDrive, MEGA, pCloud, ownCloud, Nextcloud and any other cloud storage service which synchronizes with a local directory
- Open Source means: No backdoors, control is better than trust
- Client-side: No accounts, no data shared with any online service
- Totally transparent: Just work on the virtual drive as if it were a USB flash drive
- AES encryption with 256-bit key length
- File names get encrypted
- Folder structure gets obfuscated
- Use as many vaults in your Dropbox as you want, each having individual passwords
- Four thousand commits for the security of your data!! :tada:

### Privacy

- 256-bit keys (unlimited strength policy bundled with native binaries)
- Scrypt key derivation
- Cryptographically secure random numbers for salts, IVs and the masterkey of course
- Sensitive data is wiped from the heap asap
- Lightweight: [Complexity kills security](https://www.schneier.com/essays/archives/1999/11/a_plea_for_simplicit.html)

### Consistency

- Authenticated encryption is used for file content to recognize changed ciphertext before decryption
- I/O operations are transactional and atomic, if the filesystems support it
- Each file contains all information needed for decryption (except for the key of course), no common metadata means no [SPOF](http://en.wikipedia.org/wiki/Single_point_of_failure)

### Security Architecture

For more information on the security details visit [cryptomator.org](https://docs.cryptomator.org/en/latest/security/architecture/).

## Building

### Dependencies

* JDK 22 (e.g. temurin, zulu)
* Maven 3

### Run Maven

```
mvn clean install
# or mvn clean install -Pwin
# or mvn clean install -Pmac
# or mvn clean install -Plinux
```

This will build all the jars and bundle them together with their OS-specific dependencies under `target`. This can now be used to build native packages.

## License

This project is dual-licensed under the GPLv3 for FOSS projects as well as a commercial license for independent software vendors and resellers. If you want to modify this application under different conditions, feel free to contact our support team.
