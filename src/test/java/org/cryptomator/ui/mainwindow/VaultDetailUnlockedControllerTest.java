package org.cryptomator.ui.mainwindow;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;
import org.cryptomator.common.vaults.Vault;
import org.cryptomator.integrations.revealpath.RevealPathService;
import org.cryptomator.ui.common.VaultService;
import org.cryptomator.ui.fxapp.FxApplicationWindows;
import org.cryptomator.ui.stats.VaultStatisticsComponent;
import org.cryptomator.ui.wrongfilealert.WrongFileAlertComponent;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class VaultDetailUnlockedControllerTest {

	@Mock
	private Vault vaultMock;

	private ObjectProperty<Vault> vaultProperty;

	@Mock
	private FxApplicationWindows appWindowsMock;

	@Mock
	private VaultService vaultServiceMock;

	@Mock
	private VaultStatisticsComponent.Builder vaultStatsBuilderMock;

	@Mock
	private WrongFileAlertComponent.Builder wrongFileAlertBuilderMock;

	@Mock
	private Stage mainWindowMock;

	@Mock
	private RevealPathService revealPathServiceMock;

	@Mock
	private ResourceBundle resourceBundleMock;

	private VaultDetailUnlockedController controller;

	@BeforeEach
	void setUp() {
		// Initialiser vaultProperty avec vaultMock
		vaultProperty = new SimpleObjectProperty<>(vaultMock);

		// Créer un Optional pour revealPathService
		Optional<RevealPathService> revealPathServiceOptional = Optional.of(revealPathServiceMock);

		// Instancier le contrôleur avec les mocks
		controller = new VaultDetailUnlockedController(
				vaultProperty,
				appWindowsMock,
				vaultServiceMock,
				vaultStatsBuilderMock,
				wrongFileAlertBuilderMock,
				mainWindowMock,
				revealPathServiceOptional,
				resourceBundleMock
		);
	}

	@Test
	public void testRevealAccessLocation() {
		// Test to ensure that the revealAccessLocation method correctly calls
		// vaultService.reveal with the appropriate Vault.

		// Arrange
		doNothing().when(vaultServiceMock).reveal(any(Vault.class));

		// Act
		controller.revealAccessLocation();

		// Assert
		verify(vaultServiceMock).reveal(vaultMock);

	}

	@Test
	public void testLock() {
		// Test to ensure that the method lock calls startLockWorkflow with the correct Vault and Stage (main window),
		// and that no other interactions with appWindowsMock occur.

		// Arrange
		when(appWindowsMock.startLockWorkflow(any(Vault.class), any(Stage.class))).thenReturn(CompletableFuture.completedFuture(null));

		// Act
		controller.lock();

		// Assert
		verify(appWindowsMock, times(1)).startLockWorkflow(vaultMock, mainWindowMock);
		verifyNoMoreInteractions(appWindowsMock);

	}
}