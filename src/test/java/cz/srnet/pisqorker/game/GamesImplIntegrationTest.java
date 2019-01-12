package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

final class GamesImplIntegrationTest {

	@Test
	void test3x3Draw() {
		WinConditionCheckers winConditionCheckers = IteratingWinConditionChecker::new;
		Rules rules = new RulesImpl(3, 3);

		Games impl = new GamesImpl(winConditionCheckers);
		Game game = impl.newGame(rules, FakePlayer.X, FakePlayer.O);

		assertEquals(GameState.notStarted, game.state());
		doMove(game, 1, Piece.X, 0, 0, GameState.started);
		doMove(game, 2, Piece.O, -1, -1, GameState.started);
		doMove(game, 3, Piece.X, 1, -1, GameState.started);
		doMove(game, 4, Piece.O, -1, 1, GameState.started);
		doMove(game, 5, Piece.X, -1, 0, GameState.started);
		doMove(game, 6, Piece.O, 1, 0, GameState.started);
		doMove(game, 7, Piece.X, 0, 1, GameState.started);
		doMove(game, 8, Piece.O, 0, -1, GameState.started);
		doMove(game, 9, Piece.X, 1, 1, GameState.draw);
	}

	@Test
	void test3x3WonByX() {
		WinConditionCheckers winConditionCheckers = IteratingWinConditionChecker::new;
		Rules rules = new RulesImpl(3, 3);

		Games impl = new GamesImpl(winConditionCheckers);
		Game game = impl.newGame(rules, FakePlayer.X, FakePlayer.O);

		assertEquals(GameState.notStarted, game.state());
		doMove(game, 1, Piece.X, 0, 0, GameState.started);
		doMove(game, 2, Piece.O, -1, -1, GameState.started);
		doMove(game, 3, Piece.X, 0, 1, GameState.started);
		doMove(game, 4, Piece.O, 1, 1, GameState.started);
		doMove(game, 5, Piece.X, 0, -1, GameState.wonByX);
	}

	private void doMove(@NonNull Game game, int turn, @NonNull Piece player, int x, int y, @NonNull GameState state) {
		assertEquals(player, game.moves().nextPiece());
		game.moves().move().to(x, y);
		Move move = game.moves().lastMove().get();
		assertEquals(turn, move.turn());
		assertEquals(player, move.piece());
		assertEquals(Coordinates.of(x, y), move.xy());
		assertEquals(state, game.state());
	}

}
