package cz.srnet.pisqorker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

final class MoveImplTest {

	private @NonNull FakeRules rules = new FakeRules(21, 5);
	private @NonNull FakeNextMoves nextMoves = new FakeNextMoves()._next(whatever -> Optional.empty());

	@Test
	void firstMove() {
		Move firstMove = newFirstMove();

		new MoveAssert(firstMove).turn(1).state(GameState.started).player(Player.X).nextPlayer(Player.O).x(0).y(0)
				.previous(Optional.empty()).next(Optional.empty());
	}

	private @NonNull MoveImpl newFirstMove() {
		return new MoveImpl(rules, nextMoves, 0, 0);
	}

	@Test
	void secondMove() {
		doSecondMove(firstMove -> firstMove.move(1, 1));
	}

	@Test
	void secondMoveWithPlayer() {
		doSecondMove(firstMove -> firstMove.move(Player.O, 1, 1));
	}

	private void doSecondMove(@NonNull UnaryOperator<Move> moveCall) {
		Move firstMove = newFirstMove();

		Move secondMove = Objects.requireNonNull(moveCall.apply(firstMove));
		nextMoves._next(move -> {
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
	void illegalPlayerSecondMove() {
		Move firstMove = newFirstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move(Player.X, 1, 1));
	}

	@Test
	void occupiedPositionSecondMove() {
		Move firstMove = newFirstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move(0, 0));
	}

	@Test
	void outOfBoardPositionSecondMove() {
		rules = new FakeRules(1, 5);

		Move firstMove = newFirstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move(1, 1));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(-1, -1));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(1, -1));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(-1, 1));
		assertThrows(IllegalArgumentException.class, () -> firstMove.move(2, 2));
	}

	@Test
	void drawOnFirstMove() {
		rules = new FakeRules(1, 5);

		Move firstMove = newFirstMove();

		new MoveAssert(firstMove).state(GameState.draw);
	}

	@Test
	void drawOn3x3() {
		rules = new FakeRules(3, 5);

		Move lastMove = newFirstMove().move(-1, -1).move(-1, 0).move(-1, 1).move(0, -1).move(0, 1).move(1, -1)
				.move(1, 0).move(1, 1);

		new MoveAssert(lastMove).allPreviousStarted().state(GameState.draw);
	}

	@Test
	void forEachPrevious() {
		Move firstMove = newFirstMove();
		Move secondMove = firstMove.move(1, 1);
		Move thirdMove = secondMove.move(-1, -1);

		List<Move> consumed = new ArrayList<>();
		thirdMove.forEachPrevious(consumed::add);

		assertEquals(Arrays.asList(secondMove, firstMove), consumed);
	}

}
