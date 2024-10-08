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
		// Arrange
		doNothing().when(vaultServiceMock).reveal(any(Vault.class));

		// Act
		controller.revealAccessLocation();

		// Assert
		verify(vaultServiceMock).reveal(vaultMock);

		// Intention : Vérifier que la méthode revealAccessLocation appelle correctement vaultService.reveal avec le bon Vault.
	}

	@Test
	public void testLock() {
		// Intention : Vérifier que la méthode lock appelle startLockWorkflow avec le bon Vault et Stage.

		// Arrange
		when(appWindowsMock.startLockWorkflow(any(Vault.class), any(Stage.class))).thenReturn(CompletableFuture.completedFuture(null));

		// Act
		controller.lock();

		// Assert
		verify(appWindowsMock, times(1)).startLockWorkflow(vaultMock, mainWindowMock);
		verifyNoMoreInteractions(appWindowsMock);

		// Oracle : Confirme que startLockWorkflow est appelé une seule fois avec les bons paramètres.
	}
}