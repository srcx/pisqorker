package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.lang.NonNull;

final class GameImplTest {

	@ParameterizedTest
	@CsvSource({ //
			"X, X, true", //
			"X, O, false", //
			"O, X, false", //
			"O, O, true", //
	})
	void testInvalidPlayers(@NonNull Piece first, @NonNull Piece second, boolean expectedFailed) throws Throwable {
		FakeGameContext context = new FakeGameContext();
		FakeMovesRepository moves = new FakeMovesRepository();

		Executable call = () -> new GameImpl("id", context, moves, new FakePlayer(first), new FakePlayer(second));
		if (expectedFailed) {
			assertThrows(IllegalArgumentException.class, call);
		} else {
			call.execute();
		}
	}

	@Test
	void testIdAndMoves() {
		String id = "id";
		FakeGameContext context = new FakeGameContext();
		FakeMovesRepository moves = new FakeMovesRepository();

		Game impl = new GameImpl(id, context, moves, FakePlayer.X, FakePlayer.O);

		assertEquals(id, impl.id());
		assertSame(moves, impl.moves());
	}

	@Test
	void testPlayers() {
		String id = "id";
		FakeGameContext context = new FakeGameContext();
		FakeMovesRepository moves = new FakeMovesRepository();
		FakePlayer firstPlayer = FakePlayer.X;
		FakePlayer secondPlayer = FakePlayer.O;

		Game impl = new GameImpl(id, context, moves, firstPlayer, secondPlayer);

		assertEquals(firstPlayer, impl.firstPlayer());
		assertEquals(secondPlayer, impl.secondPlayer());

		assertEquals(firstPlayer, impl.currentPlayer());
		moves._firstMove(new FakeMove()._piece(Piece.X));
		assertEquals(secondPlayer, impl.currentPlayer());
		moves._addMove(new FakeMove()._piece(Piece.O));
		assertEquals(firstPlayer, impl.currentPlayer());
		moves._addMove(new FakeMove()._piece(Piece.X));
		assertEquals(secondPlayer, impl.currentPlayer());
	}

	@ParameterizedTest
	@EnumSource(GameState.class)
	void testState(@NonNull GameState state) {
		FakeGameContext context = new FakeGameContext();
		FakeMove firstMove = new FakeMove();
		FakeMove lastMove = new FakeMove()._state(state);
		FakeMovesRepository moves = new FakeMovesRepository();
		if (state != GameState.notStarted) {
			moves._firstMove(firstMove)._addMove(lastMove);
		}

		Game impl = new GameImpl("id", context, moves, FakePlayer.X, FakePlayer.O);

		assertEquals(state, impl.state());
	}

}
