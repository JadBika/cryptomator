package org.cryptomator.common.keychain;


import org.cryptomator.integrations.keychain.KeychainAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.javafaker.Faker;

public class KeychainManagerTest {

	@Test
	public void testStoreAndLoad() throws KeychainAccessException {
		KeychainManager keychainManager = new KeychainManager(new SimpleObjectProperty<>(new MapKeychainAccess()));
		keychainManager.storePassphrase("test", "Test", "asd");
		Assertions.assertArrayEquals("asd".toCharArray(), keychainManager.loadPassphrase("test"));
	}

	@Nested
	public static class WhenObservingProperties {

		@BeforeAll
		public static void startup() throws InterruptedException {
			CountDownLatch latch = new CountDownLatch(1);
			Platform.startup(latch::countDown);
			var javafxStarted = latch.await(5, TimeUnit.SECONDS);
			Assumptions.assumeTrue(javafxStarted);
		}

		@Test
		public void testPropertyChangesWhenStoringPassword() throws KeychainAccessException, InterruptedException {
			KeychainManager keychainManager = new KeychainManager(new SimpleObjectProperty<>(new MapKeychainAccess()));
			ReadOnlyBooleanProperty property = keychainManager.getPassphraseStoredProperty("test");
			Assertions.assertFalse(property.get());

			keychainManager.storePassphrase("test", null,"bar");

			AtomicBoolean result = new AtomicBoolean(false);
			CountDownLatch latch = new CountDownLatch(1);
			Platform.runLater(() -> {
				result.set(property.get());
				latch.countDown();
			});
			Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> latch.await());
			Assertions.assertTrue(result.get());
		}

	}

	@Test
	public void testDeletePassphrase() throws KeychainAccessException {
		// Test to ensure that a passphrase can be stored, retrieved, and then deleted.
		// The test verifies that after deletion, the passphrase can no longer be retrieved.

		// Arrange
		KeychainManager keychainManager = new KeychainManager(new SimpleObjectProperty<>(new MapKeychainAccess()));
		String passphraseKey = "testDelete";
		String passphrase = "deleteMe";

		// Act
		keychainManager.storePassphrase(passphraseKey, "Test Delete", passphrase);
		Assertions.assertArrayEquals(passphrase.toCharArray(), keychainManager.loadPassphrase(passphraseKey),
				"Expected the stored passphrase to be retrievable.");

		// Now delete the passphrase
		keychainManager.deletePassphrase(passphraseKey);

		// Assert
		Assertions.assertNull(keychainManager.loadPassphrase(passphraseKey),
				"Expected the passphrase to be null after deletion.");
	}

	@Test
	public void testChangePassphraseWithFaker() throws KeychainAccessException {
		// Test to ensure that the changePassphrase method correctly replaces an old passphrase with a new one.
		// Faker is used to generate random passphrases and keys for this test.

		// Arrange
		Faker faker = new Faker();
		KeychainManager keychainManager = new KeychainManager(new SimpleObjectProperty<>(new MapKeychainAccess()));

		String passphraseKey = faker.internet().uuid();  // Génère un UUID aléatoire pour la clé
		String oldPassphrase = faker.internet().password(8, 16);  // Génère un ancien mot de passe
		String newPassphrase = faker.internet().password(8, 16);  // Génère un nouveau mot de passe

		// Act
		// Stocke l'ancien mot de passe
		keychainManager.storePassphrase(passphraseKey, "Faker Generated", oldPassphrase);

		// Change le mot de passe
		keychainManager.changePassphrase(passphraseKey, "Faker Updated", newPassphrase);

		// Assert
		// Vérifie que l'ancien mot de passe n'est plus valable
		Assertions.assertNotEquals(oldPassphrase.toCharArray(), keychainManager.loadPassphrase(passphraseKey),
				"Expected the old passphrase to no longer be valid after the change.");

		// Vérifie que le nouveau mot de passe est celui récupéré
		Assertions.assertArrayEquals(newPassphrase.toCharArray(), keychainManager.loadPassphrase(passphraseKey),
				"Expected the new passphrase to be retrievable after the change.");
	}
}
