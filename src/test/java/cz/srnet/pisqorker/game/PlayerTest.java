package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Player;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class PlayerTest {

	@ParameterizedTest
	@CsvSource({ "X, O", "O, X" })
	void testNext(@NonNull Player from, @NonNull Player to) {
		assertEquals(to, from.next());
	}

}
