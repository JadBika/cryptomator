/*******************************************************************************
 * Copyright (c) 2016, 2017 Sebastian Stenzel and others.
 * All rights reserved.
 * This program and the accompanying materials are made available under the terms of the accompanying LICENSE file.
 *
 * Contributors:
 *     Sebastian Stenzel - initial API and implementation
 *******************************************************************************/
package org.cryptomator.common.settings;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VaultSettingsTest {

	@ParameterizedTest(name = "VaultSettings.normalizeDisplayName({0}) = {1}")
	@CsvSource(value = {
			"a\u000Fa,a_a",
			": \\,_ _",
			"汉语,汉语",
			"..,_",
			"a\ta,a\u0020a",
			"'\t\n\r',_"
	})
	public void testNormalize(String test, String expected) {
		assertEquals(expected, VaultSettings.normalizeDisplayName(test));
	}

	private final Faker faker = new Faker();

	@Test
	public void testSerializedWithFaker() {
		// Test to ensure that the serialized() method correctly serializes all fields of VaultSettings,
		// using Faker to generate realistic test data.

		// Arrange
		VaultSettingsJson json = new VaultSettingsJson();
		json.id = faker.internet().uuid();
		json.path = "/path/to/" + faker.file().fileName(); // Générer un nom de fichier complet
		json.displayName = faker.lorem().sentence(3);
		json.unlockAfterStartup = faker.bool().bool();
		json.revealAfterMount = faker.bool().bool();
		json.usesReadOnlyMode = faker.bool().bool();
		json.mountFlags = faker.lorem().word();
		json.maxCleartextFilenameLength = faker.number().numberBetween(1, 256);
		json.actionAfterUnlock = WhenUnlocked.ASK; // Remplacer par une valeur valide de l'énumération
		json.autoLockWhenIdle = faker.bool().bool();
		json.autoLockIdleSeconds = faker.number().numberBetween(10, 6000);
		json.mountPoint = "/mnt/vault/" + faker.file().fileName();
		json.mountService = faker.app().name();
		json.port = faker.number().numberBetween(1024, 65535);

		VaultSettings vaultSettings = new VaultSettings(json);

		// Act
		VaultSettingsJson serializedJson = vaultSettings.serialized();

		// Assert
		assertEquals(json.id, serializedJson.id, "Expected the ID to be serialized correctly");
		assertEquals(json.path, serializedJson.path, "Expected the path to be serialized correctly");
		assertEquals(json.displayName, serializedJson.displayName, "Expected the display name to be serialized correctly");
		assertEquals(json.unlockAfterStartup, serializedJson.unlockAfterStartup, "Expected unlockAfterStartup to be serialized correctly");
		assertEquals(json.revealAfterMount, serializedJson.revealAfterMount, "Expected revealAfterMount to be serialized correctly");
		assertEquals(json.usesReadOnlyMode, serializedJson.usesReadOnlyMode, "Expected usesReadOnlyMode to be serialized correctly");
		assertEquals(json.mountFlags, serializedJson.mountFlags, "Expected mountFlags to be serialized correctly");
		assertEquals(json.maxCleartextFilenameLength, serializedJson.maxCleartextFilenameLength, "Expected maxCleartextFilenameLength to be serialized correctly");
		assertEquals(json.actionAfterUnlock, serializedJson.actionAfterUnlock, "Expected actionAfterUnlock to be serialized correctly");
		assertEquals(json.autoLockWhenIdle, serializedJson.autoLockWhenIdle, "Expected autoLockWhenIdle to be serialized correctly");
		assertEquals(json.autoLockIdleSeconds, serializedJson.autoLockIdleSeconds, "Expected autoLockIdleSeconds to be serialized correctly");
		assertEquals(json.mountPoint, serializedJson.mountPoint, "Expected mountPoint to be serialized correctly");
		assertEquals(json.mountService, serializedJson.mountService, "Expected mountService to be serialized correctly");
		assertEquals(json.port, serializedJson.port, "Expected port to be serialized correctly");
	}
}
