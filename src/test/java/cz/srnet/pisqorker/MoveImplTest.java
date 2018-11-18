package cz.srnet.pisqorker;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.GameState;
import cz.srnet.pisqorker.Move;
import cz.srnet.pisqorker.MoveImpl;
import cz.srnet.pisqorker.Player;

final class MoveImplTest {

	private @NonNull FakeNextMoves nextMoves = new FakeNextMoves()._next(whatever -> Optional.empty());

	@Test
	void firstMove() {
		Move firstMove = newFirstMove();

		new MoveAssert(firstMove).turn(1).state(GameState.started).player(Player.X).nextPlayer(Player.O).x(0).y(0)
				.previous(Optional.empty()).next(Optional.empty());
	}

	private @NonNull MoveImpl newFirstMove() {
		return new MoveImpl(nextMoves, 0, 0);
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
	void illegalPositionSecondMove() {
		Move firstMove = newFirstMove();

		assertThrows(IllegalArgumentException.class, () -> firstMove.move(0, 0));
	}
}
