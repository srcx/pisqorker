package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.GameState;
import cz.srnet.pisqorker.game.Games;
import cz.srnet.pisqorker.game.GamesImpl;
import cz.srnet.pisqorker.game.Player;
import cz.srnet.pisqorker.game.WinConditionCheckers;

final class GamesImplTest {

	@Test
	void testNewGame() {
		WinConditionCheckers winConditionCheckers = (rules) -> (move) -> true;
		FakeRules rules = new FakeRules(3, 3);

		Games impl = new GamesImpl(winConditionCheckers);
		Game game = impl.newGame(rules);

		assertEquals(GameState.notStarted, game.state());
		game.moves().move().to(0, 0);
		assertEquals(GameState.wonBy(Player.defaultFirst()), game.state());
	}

}
