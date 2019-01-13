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

		Game impl = new GameImpl(id, context, moves, FakePlayers.XO);

		assertEquals(id, impl.id());
		assertSame(moves, impl.moves());
	}

	@Test
	void testPlayers() {
		String id = "id";
		FakeGameContext context = new FakeGameContext();
		FakeMovesRepository moves = new FakeMovesRepository();
		FakePlayers players = FakePlayers.XO;

		Game impl = new GameImpl(id, context, moves, players);

		assertEquals(players, impl.players());

		assertEquals(players.first(), impl.currentPlayer());
		moves._firstMove(new FakeMove()._piece(Piece.X));
		assertEquals(players.second(), impl.currentPlayer());
		moves._addMove(new FakeMove()._piece(Piece.O));
		assertEquals(players.first(), impl.currentPlayer());
		moves._addMove(new FakeMove()._piece(Piece.X));
		assertEquals(players.second(), impl.currentPlayer());
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

		Game impl = new GameImpl("id", context, moves, FakePlayers.XO);

		assertEquals(state, impl.state());
	}

}
