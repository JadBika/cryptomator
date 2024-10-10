package org.cryptomator.ui.keyloading.hub;

import com.auth0.jwt.JWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URI;

public class HubConfigTest {

	@Test
	@DisplayName("can parse JWT with unknown fields in header claim \"hub\"")
	public void testParseJWTWithUnknownFields() {
		var jwt = JWT.decode("eyJraWQiOiIxMjMiLCJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiIsImh1YiI6eyJ1bmtub3duRmllbGQiOjQyLCJjbGllbnRJZCI6ImNyeXB0b21hdG9yIn19.eyJqdGkiOiI0NTYifQ.e1CStFf5fdh9ofX_6O8_LfbHfHEJZqUpuYNWz9xZp0I");
		var claim = jwt.getHeaderClaim("hub");
		var hubConfig = Assertions.assertDoesNotThrow(() -> claim.as(HubConfig.class));
		Assertions.assertEquals("cryptomator", hubConfig.clientId);
	}

	@Test
	public void testGetApiBaseUrlWithDevicesResourceUrl() {
		// Test to ensure that getApiBaseUrl returns the correct base URL when apiBaseUrl is null
		// but devicesResourceUrl is set. The base URL should be derived from devicesResourceUrl.

		// Arrange
		HubConfig config = new HubConfig();
		config.apiBaseUrl = null;
		config.devicesResourceUrl = "https://example.com/devices";

		// Act
		URI apiBaseUrl = config.getApiBaseUrl();

		// Assert
		Assertions.assertEquals("https://example.com/", apiBaseUrl.toString(), "Expected API base URL to resolve based on devicesResourceUrl");
	}
}