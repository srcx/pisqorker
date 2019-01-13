package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.lang.NonNull;

final class PlayersImplTest {

	@ParameterizedTest
	@CsvSource({ //
			"X, X, true", //
			"X, O, false", //
			"O, X, false", //
			"O, O, true", //
	})
	void testInvalidPlayers(@NonNull Piece first, @NonNull Piece second, boolean expectedFailed) throws Throwable {
		Executable call = () -> new PlayersImpl(new FakePlayer(first), new FakePlayer(second));
		if (expectedFailed) {
			assertThrows(IllegalArgumentException.class, call);
		} else {
			call.execute();
		}
	}

}
