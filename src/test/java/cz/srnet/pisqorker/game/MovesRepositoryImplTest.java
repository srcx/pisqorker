package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

final class MovesRepositoryImplTest {

	private static final @NonNull Consumer<Piece> voidChecker = piece -> {
	};

	@Test
	void testNoMoves() {
		FakeGameContext context = new FakeGameContext();

		Moves impl = new MovesRepositoryImpl(context, Piece.X, voidChecker);

		assertFalse(impl.firstMove().isPresent());
		assertFalse(impl.lastMove().isPresent());
		assertEquals(Piece.X, impl.nextPiece());
	}

	@Test
	void testOneMove1() {
		doTestOneMove(impl -> impl.move().to(0, 0));
	}

	@Test
	void testOneMove2() {
		doTestOneMove(impl -> impl.move().to(Coordinates.of(0, 0)));
	}

	@Test
	void testOneMove3() {
		doTestOneMove(impl -> impl.move().as(Piece.X).to(0, 0));
	}

	@Test
	void testOneMove4() {
		doTestOneMove(impl -> impl.move().as(Piece.X).to(Coordinates.of(0, 0)));
	}

	@Test
	void testOneMoveWithWrongPlayer1() {
		assertThrows(IllegalPieceException.class,
				() -> doTestOneMove(impl -> impl.move().as(Piece.X.other()).to(0, 0)));
	}

	@Test
	void testOneMoveWithWrongPlayer2() {
		assertThrows(IllegalPieceException.class,
				() -> doTestOneMove(impl -> impl.move().as(Piece.X.other()).to(Coordinates.of(0, 0))));
	}

	private void doTestOneMove(@NonNull Function<Moves, Move> moveFunction) {
		FakeGameContext context = new FakeGameContext();

		Moves impl = new MovesRepositoryImpl(context, Piece.X, voidChecker);
		Move first = moveFunction.apply(impl);

		assertEquals(Coordinates.of(0, 0), first.xy());
		assertEquals(first, impl.firstMove().get());
		assertEquals(first, impl.lastMove().get());
		assertFalse(first.previous().isPresent());
		assertEquals(0, first.previousStream().count());
		assertEquals(Piece.X.other(), impl.nextPiece());
	}

	@Test
	void testTwoMoves() {
		FakeGameContext context = new FakeGameContext();

		Moves impl = new MovesRepositoryImpl(context, Piece.X, voidChecker);
		Move first = impl.move().to(0, 0);
		Move second = impl.move().to(1, 1);

		assertEquals(Coordinates.of(0, 0), first.xy());
		assertEquals(Coordinates.of(1, 1), second.xy());
		assertEquals(first, impl.firstMove().get());
		assertFalse(first.previous().isPresent());
		assertEquals(0, first.previousStream().count());
		assertEquals(second, impl.lastMove().get());
		assertEquals(first, second.previous().get());
		assertEquals(1, second.previousStream().count());
		assertEquals(Piece.X, impl.nextPiece());
	}

}
