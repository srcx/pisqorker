package cz.srnet.pisqorker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameStateTest {

	@Test
	void testWonBy() {
		assertEquals(GameState.wonByO, GameState.wonBy(Player.O));
		assertEquals(GameState.wonByX, GameState.wonBy(Player.X));
	}

}
