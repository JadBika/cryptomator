package org.cryptomator.ui.recoverykey;

import com.google.common.base.Splitter;
import org.cryptomator.cryptolib.api.CryptoException;
import org.cryptomator.cryptolib.api.Masterkey;
import org.cryptomator.cryptolib.common.MasterkeyFileAccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import java.nio.file.Path;

public class RecoveryKeyFactoryTest {

	private final WordEncoder wordEncoder = new WordEncoder();
	private final MasterkeyFileAccess masterkeyFileAccess = Mockito.mock(MasterkeyFileAccess.class);
	private final RecoveryKeyFactory inTest = new RecoveryKeyFactory(wordEncoder, masterkeyFileAccess);

	@Test
	@DisplayName("createRecoveryKey() creates 44 words")
	public void testCreateRecoveryKey() throws IOException, CryptoException {
		Path pathToVault = Path.of("path/to/vault");
		Masterkey masterkey = Mockito.mock(Masterkey.class);
		Mockito.when(masterkeyFileAccess.load(pathToVault.resolve("masterkey.cryptomator"), "asd")).thenReturn(masterkey);

		Mockito.when(masterkey.getEncoded()).thenReturn(new byte[64]);

		String recoveryKey = inTest.createRecoveryKey(pathToVault, "asd");
		Assertions.assertNotNull(recoveryKey);
		Assertions.assertEquals(44, Splitter.on(' ').splitToList(recoveryKey).size()); // 66 bytes encoded as 44 words
	}

	@Test
	@DisplayName("validateRecoveryKey() with odd number of words")
	public void testValidateValidateRecoveryKeyWithOddNumberOfWords() {
		boolean result = inTest.validateRecoveryKey("pathway");
		Assertions.assertFalse(result);
	}

	@Test
	@DisplayName("validateRecoveryKey() with words not in dictionary")
	public void testValidateValidateRecoveryKeyWithGarbageInput() {
		boolean result = inTest.validateRecoveryKey("Backpfeifengesicht Schweinehund"); // according to le internet these are typical German words
		Assertions.assertFalse(result);
	}

	@Test
	@DisplayName("validateRecoveryKey() with too short input")
	public void testValidateValidateRecoveryKeyWithTooShortInput() {
		boolean result = inTest.validateRecoveryKey("pathway lift");
		Assertions.assertFalse(result);
	}

	@Test
	@DisplayName("validateRecoveryKey() with invalid checksum")
	public void testValidateValidateRecoveryKeyWithInvalidCrc() {
		boolean result = inTest.validateRecoveryKey("""
				pathway lift abuse plenty export texture gentleman landscape beyond ceiling around leaf cafe charity \
				border breakdown victory surely computer cat linger restrict infer crowd live computer true written amazed \
				investor boot depth left theory snow whereby terminal weekly reject happiness circuit partial cup wrong \
				""");
		Assertions.assertFalse(result);
	}

	@Test
	@DisplayName("validateRecoveryKey() with valid input")
	public void testValidateValidateRecoveryKeyWithValidKey() {
		boolean result = inTest.validateRecoveryKey("""
				pathway lift abuse plenty export texture gentleman landscape beyond ceiling around leaf cafe charity \
				border breakdown victory surely computer cat linger restrict infer crowd live computer true written amazed \
				investor boot depth left theory snow whereby terminal weekly reject happiness circuit partial cup ad \
				""");
		Assertions.assertTrue(result);
	}

	@ParameterizedTest(name = "passing validation = {0}")
	@DisplayName("validateRecoveryKey() with extended validation")
	@ValueSource(booleans = {true, false})
	public void testValidateValidateRecoveryKeyWithValidKey(boolean extendedValidationResult) {
		Predicate<byte[]> validator = Mockito.mock(Predicate.class);
		Mockito.doReturn(extendedValidationResult).when(validator).test(Mockito.any());
		boolean result = inTest.validateRecoveryKey("""
				pathway lift abuse plenty export texture gentleman landscape beyond ceiling around leaf cafe charity \
				border breakdown victory surely computer cat linger restrict infer crowd live computer true written amazed \
				investor boot depth left theory snow whereby terminal weekly reject happiness circuit partial cup ad \
				""", validator);
		Mockito.verify(validator).test(Mockito.any());
		Assertions.assertEquals(extendedValidationResult, result);
	}

	@Test
	@DisplayName("newMasterkeyFileWithPassphrase() should create new masterkey and persist it")
	public void testNewMasterkeyFileWithPassphrase() throws IOException {
		// Arrange
		Path vaultPath = Path.of("path/to/vault");
		String recoveryKey = """
        pathway lift abuse plenty export texture gentleman landscape beyond ceiling around leaf cafe charity \
        border breakdown victory surely computer cat linger restrict infer crowd live computer true written amazed \
        investor boot depth left theory snow whereby terminal weekly reject happiness circuit partial cup ad \
        """;
		CharSequence newPassword = "newSecretPassphrase";

		// Simule la récupération d'une clé brute à partir de la clé de récupération
		byte[] rawKey = new byte[64];
		Masterkey masterkeyMock = mock(Masterkey.class);
		when(masterkeyMock.getEncoded()).thenReturn(rawKey);

		// Act
		inTest.newMasterkeyFileWithPassphrase(vaultPath, recoveryKey, newPassword);

		// Assert
		// Vérifie que la méthode persist a été appelée avec les bons arguments
		ArgumentCaptor<Masterkey> masterkeyCaptor = ArgumentCaptor.forClass(Masterkey.class);
		ArgumentCaptor<Path> pathCaptor = ArgumentCaptor.forClass(Path.class);
		ArgumentCaptor<CharSequence> passwordCaptor = ArgumentCaptor.forClass(CharSequence.class);

		verify(masterkeyFileAccess).persist(masterkeyCaptor.capture(), pathCaptor.capture(), passwordCaptor.capture());

		// Vérifie que le chemin du fichier est correct
		Assertions.assertEquals(vaultPath.resolve("masterkey.cryptomator"), pathCaptor.getValue());

		// Vérifie que le mot de passe utilisé est correct
		Assertions.assertEquals(newPassword, passwordCaptor.getValue());
	}
}