package cz.srnet.pisqorker;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.lang.NonNull;

final class MoveImplTest {

	private @NonNull FakeGameContext context = new FakeGameContext();

	@BeforeEach
	void setUp() {
		context.nextMoves()._next(whatever -> Optional.empty());
	}

	@Test
	void testFirstMove() {
		Move firstMove = firstMove();

		new MoveAssert(firstMove).turn(1).state(GameState.started).player(Player.X).nextPlayer(Player.O).x(0).y(0)
				.previous(Optional.empty()).next(Optional.empty());
	}

	private @NonNull MoveImpl firstMove() {
		return new MoveImpl(context, 0, 0);
	}

	private @NonNull MoveImpl firstMove(@NotNull Player player) {
		return new MoveImpl(context, player, 0, 0);
	}

	@Test
	void testSecondMove() {
		doSecondMove(firstMove -> firstMove.move(1, 1));
	}

	@Test
	void testSecondMoveWithPlayer() {
		doSecondMove(firstMove -> firstMove.move(Player.O, 1, 1));
	}

	private void doSecondMove(@NonNull UnaryOperator<Move> moveCall) {
		Move firstMove = firstMove();

		Move secondMove = Objects.requireNonNull(moveCall.apply(firstMove));
		context.nextMoves()._next(move -> {
			if (move == firstMove) {
				return Optional.of(secondMove);
			}
			if (move == secondMove) {
				return Optional.empty();
			}
			fail("Unknown move " + move);
			return Optional.empty();
		});

		new MoveAssert(firstMove).next(secondMove);
		new MoveAssert(secondMove).turn(2).state(GameState.started).player(Player.O).nextPlayer(Player.X).x(1).y(1)
				.previous(firstMove).next(Optional.empty());
	}

	@Test
	void testIllegalPlayerSecondMove() {
		Move firstMove = firstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move(Player.X, 1, 1));
	}

	@Test
	void testOccupiedPositionSecondMove() {
		Move firstMove = firstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move(0, 0));
	}

	@Test
	void testOutOfBoardPositionSecondMove() {
		context._rules(new FakeRules(2, 5));

		Move firstMove = firstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move(3, 3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(-3, -3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(3, -3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(-3, 3));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(4, 4));
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

		Move lastMove = firstMove().move(-1, -1).move(-1, 0).move(-1, 1).move(0, -1).move(0, 1).move(1, -1).move(1, 0)
				.move(1, 1);

		new MoveAssert(lastMove).allPreviousStarted().state(GameState.draw);
	}

	@Test
	void testPreviousStream() {
		Move firstMove = firstMove();
		Move secondMove = firstMove.move(1, 1);
		Move thirdMove = secondMove.move(-1, -1);

		List<Move> actual = thirdMove.previousStream().collect(toList());

		assertEquals(Arrays.asList(secondMove, firstMove), actual);
	}

	@ParameterizedTest
	@EnumSource(Player.class)
	void testWinOnFirstMove(@NonNull Player player) {
		context._rules(new FakeRules(1, 1));
		context._winConditionChecker(whatever -> true);

		Move firstMove = firstMove(player);

		new MoveAssert(firstMove).state(GameState.wonBy(player));
	}

	@Test
	void testWinOn3x3() {
		Move lastMove = win3x3();

		new MoveAssert(lastMove).allPreviousStarted().state(GameState.wonBy(Player.X));
	}

	private @NonNull Move win3x3() {
		context._rules(new FakeRules(3, 3));
		context._winConditionChecker(move -> move.turn() == 5);

		return firstMove().move(-1, -1).move(-1, 0).move(-1, 1).move(0, -1);
	}

	@Test
	void testGameAlreadyEndedAfterWinOn3x3() {
		Move lastMove = win3x3();

		assertThrows(IllegalStateException.class, () -> lastMove.move(1, 1));
	}

}
