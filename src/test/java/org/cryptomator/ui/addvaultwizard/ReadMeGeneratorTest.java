package org.cryptomator.ui.addvaultwizard;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ReadMeGeneratorTest {

	@SuppressWarnings("SpellCheckingInspection")
	@ParameterizedTest
	@CsvSource({ //
			"test,test", //
			"t\u00E4st,t\\u228st", //
			"t\uD83D\uDE09st,t\\u55357\\u56841st", //
	})
	public void testEscapeNonAsciiChars(String input, String expectedResult) {
		ReadmeGenerator readmeGenerator = new ReadmeGenerator(null);

		String actualResult = readmeGenerator.escapeNonAsciiChars(input);

		Assertions.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCreateDocument() {
		ReadmeGenerator readmeGenerator = new ReadmeGenerator(null);
		Iterable<String> paragraphs = List.of( //
				"Dear User,", //
				"\\b please don't touch the \"d\" directory.", //
				"Thank you for your cooperation \uD83D\uDE09" //
		);

		String result = readmeGenerator.createDocument(paragraphs);

		MatcherAssert.assertThat(result, CoreMatchers.startsWith("{\\rtf1\\fbidis\\ansi\\uc0\\fs32"));
		MatcherAssert.assertThat(result, CoreMatchers.containsString("{\\sa80 Dear User,}\\par"));
		MatcherAssert.assertThat(result, CoreMatchers.containsString("{\\sa80 \\b please don't touch the \"d\" directory.}\\par "));
		MatcherAssert.assertThat(result, CoreMatchers.containsString("{\\sa80 Thank you for your cooperation \\u55357\\u56841}\\par"));
		MatcherAssert.assertThat(result, CoreMatchers.endsWith("}"));
	}

	private ReadmeGenerator readmeGenerator;


	@BeforeEach
	public void setUp() {
		// Simuler un ResourceBundle avec Mockito
		ResourceBundle mockResourceBundle = Mockito.mock(ResourceBundle.class);
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.1")).thenReturn("Vault Storage Location");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.2")).thenReturn("Some storage instructions");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.3")).thenReturn("Do not delete this folder");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.4")).thenReturn("Keep this directory intact");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.5")).thenReturn("Do not move or rename the directory");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.6")).thenReturn("Storage details");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.7")).thenReturn("Instructions part 1");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.8")).thenReturn("Instructions part 2");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.9")).thenReturn("Instructions part 3");
		when(mockResourceBundle.getString("addvault.new.readme.storageLocation.10")).thenReturn("For more information visit %s");

		readmeGenerator = new ReadmeGenerator(mockResourceBundle);
	}

	@Test
	public void testCreateVaultStorageLocationReadmeRtf() {
		// Test to ensure that createVaultStorageLocationReadmeRtf generates a correct RTF document containing all the expected sections and formatting.

		// Arrange in setUp()

		// Act
		String result = readmeGenerator.createVaultStorageLocationReadmeRtf();

		// Assert
		assertTrue(result.contains("{\\rtf1\\fbidis\\ansi\\uc0\\fs32"), "Le document RTF doit commencer par un en-tête RTF.");
		assertTrue(result.contains("{\\sa80 \\fs40\\qc Vault Storage Location}\\par \n"), "Le titre doit être présent dans le RTF.");
		assertTrue(result.contains("{\\sa80 Some storage instructions}\\par"), "Les instructions de stockage doivent être présentes.");
		assertTrue(result.contains("{\\sa80 }\\par"), "Les paragraphes vides doivent être présents.");
		assertTrue(result.contains("{\\sa80 \\b Do not delete this folder}\\par"), "Le texte en gras doit être présent.");
		assertTrue(result.contains("{\\sa80     Keep this directory intact}\\par"), "La première instruction indentée doit être présente.");
		assertTrue(result.contains("{\\sa80     Do not move or rename the directory}\\par"), "La deuxième instruction indentée doit être présente.");
		assertTrue(result.contains("{\\sa80 Storage details}\\par"), "Les détails de stockage doivent être présents.");
		assertTrue(result.contains("{\\sa80     Instructions part 1}\\par"), "Instruction partie 1 doit être présente.");
		assertTrue(result.contains("{\\sa80     Instructions part 2}\\par"), "Instruction partie 2 doit être présente.");
		assertTrue(result.contains("{\\sa80     Instructions part 3}\\par"), "Instruction partie 3 doit être présente.");
		assertTrue(result.contains("{\\sa80 For more information visit {\\field{\\*\\fldinst HYPERLINK \"http://docs.cryptomator.org/\"}{\\fldrslt http://docs.cryptomator.org}}}\\par"), "Le lien hypertexte doit être présent dans le RTF.");
		assertTrue(result.endsWith("}"), "Le document RTF doit se terminer par '}'.");
	}

}
