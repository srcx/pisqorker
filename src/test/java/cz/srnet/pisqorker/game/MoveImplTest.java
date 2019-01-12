package cz.srnet.pisqorker.game;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.lang.NonNull;

final class MoveImplTest {

	private @NonNull FakeGameContext context = new FakeGameContext();
	private @NonNull FakeMovesRepository movesRepository = new FakeMovesRepository()
			._moveWithPreviousFunction(previous -> new MakeMove() {

				@Override
				@NonNull
				public Move to(int x, int y) {
					return to(Coordinates.of(x, y));
				}

				@Override
				@NonNull
				public Move to(@NonNull Coordinates xy) {
					Move move = new MoveImpl(context, movesRepository, Piece.X, xy);
					movesRepository._addMove(move);
					return move;
				}

				@Override
				@NonNull
				public MakeMove as(@NonNull Piece piece) {
					if (piece != previous.piece().other()) {
						throw new IllegalArgumentException();
					}
					return this;
				}
			});

	@Test
	void testFirstMove() {
		Move firstMove = firstMove();

		new MoveAssert(firstMove).turn(1).state(GameState.started).player(Piece.X).xy(Coordinates.of(0, 0))
				.previous(Optional.empty()).next(Optional.empty());
	}

	private @NonNull MoveImpl firstMove() {
		return registerAsFirstMove(new MoveImpl(context, movesRepository, Piece.X, Coordinates.of(0, 0)));
	}

	private @NonNull MoveImpl firstMove(@NotNull Piece player) {
		return registerAsFirstMove(new MoveImpl(context, movesRepository, player, Coordinates.of(0, 0)));
	}

	private @NonNull MoveImpl registerAsFirstMove(@NonNull MoveImpl move) {
		movesRepository._firstMove(move);
		return move;
	}

	@Test
	void testSecondMove() {
		doSecondMove(firstMove -> firstMove.move().to(1, 1));
	}

	@Test
	void testSecondMoveWithPlayer() {
		doSecondMove(firstMove -> firstMove.move().as(Piece.O).to(1, 1));
	}

	private void doSecondMove(@NonNull UnaryOperator<Move> moveCall) {
		Move firstMove = firstMove();

		Move secondMove = Objects.requireNonNull(moveCall.apply(firstMove));

		new MoveAssert(firstMove).next(secondMove);
		new MoveAssert(secondMove).turn(2).state(GameState.started).player(Piece.O).xy(Coordinates.of(1, 1))
				.previous(firstMove).next(Optional.empty());
	}

	@Test
	void testIllegalPlayerSecondMove() {
		Move firstMove = firstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move().as(Piece.X).to(1, 1));
	}

	@Test
	void testOccupiedPositionSecondMove() {
		Move firstMove = firstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move().to(0, 0));
	}

	@Test
	void testOutOfBoardPositionSecondMove() {
		context._rules(new FakeRules(2, 5));

		Move firstMove = firstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move().to(3, 3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move().to(-3, -3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move().to(3, -3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move().to(-3, 3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move().to(4, 4));
	}

	@Test
	void testDrawOnFirstMove() {
		context._rules(new FakeRules(1, 5));

		Move firstMove = firstMove();

		new MoveAssert(firstMove).state(GameState.draw);
	}

	@Test
	void testDrawOn3x3() {
		context._rules(new FakeRules(3, 5));

		Move lastMove = firstMove().move().to(-1, -1).move().to(-1, 0).move().to(-1, 1).move().to(0, -1).move().to(0, 1)
				.move().to(1, -1).move().to(1, 0).move().to(1, 1);

		new MoveAssert(lastMove).allPreviousStarted().state(GameState.draw);
	}

	@Test
	void testPreviousStream() {
		Move firstMove = firstMove();
		Move secondMove = firstMove.move().to(1, 1);
		Move thirdMove = secondMove.move().to(-1, -1);

		List<Move> actual = thirdMove.previousStream().collect(toList());

		assertEquals(Arrays.asList(secondMove, firstMove), actual);
	}

	@ParameterizedTest
	@EnumSource(Piece.class)
	void testWinOnFirstMove(@NonNull Piece player) {
		context._rules(new FakeRules(1, 1));
		context._winConditionChecker(whatever -> true);

		Move firstMove = firstMove(player);

		new MoveAssert(firstMove).state(GameState.wonBy(player));
	}

	@Test
	void testWinOn3x3() {
		Move lastMove = win3x3();

		new MoveAssert(lastMove).allPreviousStarted().state(GameState.wonBy(Piece.X));
	}

	private @NonNull Move win3x3() {
		context._rules(new FakeRules(3, 3));
		context._winConditionChecker(move -> move.turn() == 5);

		return firstMove().move().to(-1, -1).move().to(-1, 0).move().to(-1, 1).move().to(1, 0);
	}

	@Test
	void testGameAlreadyEndedAfterWinOn3x3() {
		Move lastMove = win3x3();

		assertThrows(IllegalStateException.class, () -> lastMove.move().to(1, 1));
	}

}
