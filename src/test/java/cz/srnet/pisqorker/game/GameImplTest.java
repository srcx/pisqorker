package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.GameImpl;
import cz.srnet.pisqorker.game.GameState;

final class GameImplTest {

	@Test
	void testMoves() {
		FakeMovesRepository moves = new FakeMovesRepository();

		Game impl = new GameImpl(moves);

		assertSame(moves, impl.moves());
	}

	@ParameterizedTest
	@EnumSource(GameState.class)
	void testState(@NonNull GameState state) {
		FakeMove firstMove = new FakeMove();
		FakeMove lastMove = new FakeMove()._state(state);
		FakeMovesRepository moves = new FakeMovesRepository();
		if (state != GameState.notStarted) {
			moves._firstMove(firstMove)._addMove(lastMove);
		}

		Game impl = new GameImpl(moves);

		assertEquals(state, impl.state());
	}

}
