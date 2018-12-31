package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Coordinates;
import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.GameState;
import cz.srnet.pisqorker.game.Games;
import cz.srnet.pisqorker.game.GamesImpl;
import cz.srnet.pisqorker.game.IteratingWinConditionChecker;
import cz.srnet.pisqorker.game.Move;
import cz.srnet.pisqorker.game.Player;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.RulesImpl;
import cz.srnet.pisqorker.game.WinConditionCheckers;

final class GamesImplIntegrationTest {

	@Test
	void test3x3Draw() {
		WinConditionCheckers winConditionCheckers = IteratingWinConditionChecker::new;
		Rules rules = new RulesImpl(3, 3);

		Games impl = new GamesImpl(winConditionCheckers);
		Game game = impl.newGame(rules);

		assertEquals(GameState.notStarted, game.state());
		doMove(game, 1, Player.X, 0, 0, GameState.started);
		doMove(game, 2, Player.O, -1, -1, GameState.started);
		doMove(game, 3, Player.X, 1, -1, GameState.started);
		doMove(game, 4, Player.O, -1, 1, GameState.started);
		doMove(game, 5, Player.X, -1, 0, GameState.started);
		doMove(game, 6, Player.O, 1, 0, GameState.started);
		doMove(game, 7, Player.X, 0, 1, GameState.started);
		doMove(game, 8, Player.O, 0, -1, GameState.started);
		doMove(game, 9, Player.X, 1, 1, GameState.draw);
	}

	@Test
	void test3x3WonByX() {
		WinConditionCheckers winConditionCheckers = IteratingWinConditionChecker::new;
		Rules rules = new RulesImpl(3, 3);

		Games impl = new GamesImpl(winConditionCheckers);
		Game game = impl.newGame(rules);

		assertEquals(GameState.notStarted, game.state());
		doMove(game, 1, Player.X, 0, 0, GameState.started);
		doMove(game, 2, Player.O, -1, -1, GameState.started);
		doMove(game, 3, Player.X, 0, 1, GameState.started);
		doMove(game, 4, Player.O, 1, 1, GameState.started);
		doMove(game, 5, Player.X, 0, -1, GameState.wonByX);
	}

	private void doMove(@NonNull Game game, int turn, @NonNull Player player, int x, int y, @NonNull GameState state) {
		assertEquals(player, game.moves().nextPlayer());
		game.moves().move().to(x, y);
		Move move = game.moves().lastMove().get();
		assertEquals(turn, move.turn());
		assertEquals(player, move.player());
		assertEquals(Coordinates.of(x, y), move.xy());
		assertEquals(state, game.state());
	}

}
