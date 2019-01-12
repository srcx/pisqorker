package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Piece;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class PieceTest {

	@ParameterizedTest
	@CsvSource({ "X, O", "O, X" })
	void testNext(@NonNull Piece from, @NonNull Piece to) {
		assertEquals(to, from.other());
	}

}
