package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cz.srnet.pisqorker.game.GameState;
import cz.srnet.pisqorker.game.Player;

class GameStateTest {

	@Test
	void testWonBy() {
		assertEquals(GameState.wonByO, GameState.wonBy(Player.O));
		assertEquals(GameState.wonByX, GameState.wonBy(Player.X));
	}

}
