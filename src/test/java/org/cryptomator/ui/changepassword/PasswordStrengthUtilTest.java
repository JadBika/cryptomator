package org.cryptomator.ui.changepassword;

import com.google.common.base.Strings;
import org.cryptomator.common.Environment;
import org.cryptomator.ui.changepassword.PasswordStrengthUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.ResourceBundle;

public class PasswordStrengthUtilTest {

	@Test
	public void testLongPasswords() {
		PasswordStrengthUtil util = new PasswordStrengthUtil(Mockito.mock(ResourceBundle.class), Mockito.mock(Environment.class));
		String longPw = Strings.repeat("x", 10_000);
		Assertions.assertTimeout(Duration.ofSeconds(5), () -> {
			util.computeRate(longPw);
		});
	}

	@Test
	public void testIssue979() {
		PasswordStrengthUtil util = new PasswordStrengthUtil(Mockito.mock(ResourceBundle.class), Mockito.mock(Environment.class));
		int result1 = util.computeRate("backed derrick buckling mountains glove client procedures desire destination sword hidden ram");
		int result2 = util.computeRate("backed derrick buckling mountains glove client procedures desire destination sword hidden ram escalation");
		Assertions.assertEquals(4, result1);
		Assertions.assertEquals(4, result2);
	}

	@Test
	public void testFulfillsMinimumRequirements_PasswordTooShort() {
		// Test to ensure that a password shorter than the minimum length (8 characters)
		// fails the minimum requirements check.

		// Arrange
		Environment mockEnv = Mockito.mock(Environment.class);
		Mockito.when(mockEnv.getMinPwLength()).thenReturn(8);
		PasswordStrengthUtil util = new PasswordStrengthUtil(Mockito.mock(ResourceBundle.class), mockEnv);

		// Act
		boolean result = util.fulfillsMinimumRequirements("short");

		// Assert
		Assertions.assertFalse(result, "Expected password to fail minimum length requirement.");
	}

	@Test
	public void testFulfillsMinimumRequirements_PasswordMeetsRequirement() {
		// Test to ensure that a password meeting the minimum length requirement (8 characters)
		// passes the minimum requirements check.

		// Arrange
		Environment mockEnv = Mockito.mock(Environment.class);
		Mockito.when(mockEnv.getMinPwLength()).thenReturn(8);
		PasswordStrengthUtil util = new PasswordStrengthUtil(Mockito.mock(ResourceBundle.class), mockEnv);

		// Act
		boolean result = util.fulfillsMinimumRequirements("longenoughpassword");

		// Assert
		Assertions.assertTrue(result, "Expected password to meet minimum length requirement.");
	}

	@Test
	public void testGetStrengthDescription_PasswordTooShort() {
		// Test to ensure that the method returns the correct "too short" message
		// when the password does not meet the minimum length requirement.

		// Arrange
		ResourceBundle mockBundle = Mockito.mock(ResourceBundle.class);
		Mockito.when(mockBundle.getString("passwordStrength.messageLabel.tooShort"))
				.thenReturn("Password is too short. Minimum length: %d");
		Environment mockEnv = Mockito.mock(Environment.class);
		Mockito.when(mockEnv.getMinPwLength()).thenReturn(8);
		PasswordStrengthUtil util = new PasswordStrengthUtil(mockBundle, mockEnv);

		// Act
		String description = util.getStrengthDescription(-1);

		// Assert
		Assertions.assertEquals("Password is too short. Minimum length: 8", description);
	}

	@Test
	public void testGetStrengthDescription_ValidScore() {
		// Test to ensure that a valid password score (3) returns the correct strength description
		// from the ResourceBundle ("Password strength: Fair").

		// Arrange
		ResourceBundle mockBundle = Mockito.mock(ResourceBundle.class);
		String key = "passwordStrength.messageLabel.3";
		// Configurer le mock pour containsKey()
		Mockito.when(mockBundle.containsKey(key)).thenReturn(true);
		// Configurer le mock pour getString()
		Mockito.when(mockBundle.getString(key)).thenReturn("Password strength: Fair");
		Environment mockEnv = Mockito.mock(Environment.class);
		PasswordStrengthUtil util = new PasswordStrengthUtil(mockBundle, mockEnv);

		// Act
		String description = util.getStrengthDescription(3);

		// Assert
		Assertions.assertEquals("Password strength: Fair", description);
	}

	@Test
	public void testGetStrengthDescription_InvalidScore() {
		// Test to ensure that an invalid password score (99) returns an empty string,
		// as there is no description available for this score in the ResourceBundle.

		// Arrange
		ResourceBundle mockBundle = Mockito.mock(ResourceBundle.class);
		Environment mockEnv = Mockito.mock(Environment.class);
		PasswordStrengthUtil util = new PasswordStrengthUtil(mockBundle, mockEnv);

		// Act
		String description = util.getStrengthDescription(99);

		// Assert
		Assertions.assertEquals("", description, "Expected an empty string for invalid score.");
	}
}
