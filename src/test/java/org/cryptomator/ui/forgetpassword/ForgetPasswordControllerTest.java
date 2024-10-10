package org.cryptomator.ui.forgetpassword;

import org.cryptomator.integrations.keychain.KeychainAccessException;
import org.cryptomator.common.keychain.KeychainManager;
import org.cryptomator.common.vaults.Vault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

public class ForgetPasswordControllerTest {

	private ForgetPasswordController controller;
	private KeychainManager keychain;
	private Vault vault;
	private BooleanProperty confirmedResult;
	private Stage window;

	@BeforeEach
	public void setUp() {
		keychain = Mockito.mock(KeychainManager.class);
		vault = Mockito.mock(Vault.class);
		confirmedResult = new SimpleBooleanProperty(false);
		window = Mockito.mock(Stage.class);

		controller = new ForgetPasswordController(window, vault, keychain, confirmedResult);
	}

	@Test
	@DisplayName("finish() should delete passphrase and set confirmedResult to true if keychain is supported")
	public void testFinishKeychainSupported() throws Exception {
		// Arrange
		Mockito.when(keychain.isSupported()).thenReturn(true);
		Mockito.when(vault.getId()).thenReturn("testVaultId");

		// Act
		controller.finish();

		// Assert
		Mockito.verify(keychain).deletePassphrase("testVaultId");
		Mockito.verify(window).close();
		assert(confirmedResult.get());
	}

	@Test
	@DisplayName("finish() should set confirmedResult to false if deletePassphrase throws KeychainAccessException")
	public void testFinishDeletePassphraseFails() throws Exception {
		// Arrange
		Mockito.when(keychain.isSupported()).thenReturn(true);
		Mockito.when(vault.getId()).thenReturn("testVaultId");
		Mockito.doThrow(new KeychainAccessException("Simulated failure")).when(keychain).deletePassphrase("testVaultId");

		// Act
		controller.finish();

		// Assert
		Mockito.verify(keychain).deletePassphrase("testVaultId");
		Mockito.verify(window).close();
		assert(!confirmedResult.get());
	}
}