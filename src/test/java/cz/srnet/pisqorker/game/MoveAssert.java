package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Coordinates;
import cz.srnet.pisqorker.game.GameState;
import cz.srnet.pisqorker.game.Move;
import cz.srnet.pisqorker.game.Piece;

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
	MoveAssert player(@NonNull Piece expected) {
		assertEquals(expected, move.piece());
		return this;
	}

	@NonNull
	MoveAssert xy(@NonNull Coordinates expected) {
		assertEquals(expected, move.xy());
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
	MoveAssert allPreviousStarted() {
		move.previousStream().forEach(m -> assertEquals(GameState.started, m.state()));
		return this;
	}

}
