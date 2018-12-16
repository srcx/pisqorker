package cz.srnet.pisqorker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class GamesImplTest {

	@Test
	void testNewGame() {
		WinConditionCheckerFactory winConditionCheckerFactory = (rules) -> (move) -> true;
		FakeRules rules = new FakeRules(3, 3);

		Games impl = new GamesImpl(winConditionCheckerFactory);
		Game game = impl.newGame(rules);

		assertEquals(GameState.notStarted, game.state());
		game.moves().move().to(0, 0);
		assertEquals(GameState.wonBy(Player.defaultFirst()), game.state());
	}

}
