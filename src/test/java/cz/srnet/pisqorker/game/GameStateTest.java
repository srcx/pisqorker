package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cz.srnet.pisqorker.game.GameState;
import cz.srnet.pisqorker.game.Piece;

class GameStateTest {

	@Test
	void testWonBy() {
		assertEquals(GameState.wonByO, GameState.wonBy(Piece.O));
		assertEquals(GameState.wonByX, GameState.wonBy(Piece.X));
	}

}
