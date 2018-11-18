package cz.srnet.pisqorker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.GameState;
import cz.srnet.pisqorker.Move;
import cz.srnet.pisqorker.Player;

final class MoveAssert {

	private @NonNull Move move;

	public MoveAssert(@NonNull Move move) {
		this.move = move;
	}

	@NonNull
	MoveAssert turn(int expected) {
		assertEquals(expected, move.turn());
		return this;
	}

	@NonNull
	MoveAssert state(@NonNull GameState expected) {
		assertEquals(expected, move.state());
		return this;
	}

	@NonNull
	MoveAssert player(@NonNull Player expected) {
		assertEquals(expected, move.player());
		return this;
	}

	@NonNull
	MoveAssert x(int expected) {
		assertEquals(expected, move.x());
		return this;
	}

	@NonNull
	MoveAssert y(int expected) {
		assertEquals(expected, move.y());
		return this;
	}

	@NonNull
	MoveAssert previous(@NonNull Optional<Move> expected) {
		assertEquals(expected, move.previous());
		return this;
	}

	@NonNull
	MoveAssert next(@NonNull Optional<Move> expected) {
		assertEquals(expected, move.next());
		return this;
	}

	@NonNull
	MoveAssert previous(@NonNull Move expected) {
		return previous(Optional.of(expected));
	}

	@NonNull
	MoveAssert next(@NonNull Move expected) {
		return next(Optional.of(expected));
	}

	@NonNull
	MoveAssert noPrevious() {
		return previous(Optional.empty());
	}

	@NonNull
	MoveAssert noNext() {
		return next(Optional.empty());
	}

	@NonNull
	MoveAssert nextPlayer(@NonNull Player expected) {
		assertEquals(expected, move.nextPlayer());
		return this;
	}

}
