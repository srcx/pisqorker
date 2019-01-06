package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.lang.NonNull;

final class GameImplTest {

	@Test
	void testIdAndMoves() {
		String id = "id";
		FakeGameContext context = new FakeGameContext();
		FakeMovesRepository moves = new FakeMovesRepository();

		Game impl = new GameImpl(id, context, moves);

		assertEquals(id, impl.id());
		assertSame(moves, impl.moves());
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

		Game impl = new GameImpl("id", context, moves);

		assertEquals(state, impl.state());
	}

}
