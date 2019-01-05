package cz.srnet.pisqorker.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

final class FakeMovesRepository implements MovesRepository {

	private @NonNull List<Move> moves = new ArrayList<>();
	private @Nullable Function<Move, MakeMove> moveWithPreviousFunction;

	public @NonNull FakeMovesRepository _firstMove(@NonNull Move move) {
		assertTrue(moves.isEmpty());
		moves.add(move);
		return this;
	}

	public @NonNull FakeMovesRepository _addMove(@NonNull Move move) {
		assertFalse(moves.isEmpty());
		moves.add(move);
		return this;
	}

	public @NonNull FakeMovesRepository _moveWithPreviousFunction(
			@NonNull Function<Move, MakeMove> moveWithPreviousFunction) {
		this.moveWithPreviousFunction = moveWithPreviousFunction;
		return this;
	}

	@Override
	@NonNull
	public Optional<Move> previousMove(@NonNull Move move) {
		Optional<Move> previous = Optional.empty();
		for (int i = 0; i < moves.size(); i++) {
			Move current = moves.get(i);
			if (move.equals(current)) {
				return previous;
			}
			previous = Optional.of(current);
		}
		fail("Move " + move + " not in a list of moves: " + moves);
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Optional<Move> nextMove(@NonNull Move move) {
		Optional<Move> next = Optional.empty();
		for (int i = moves.size() - 1; i >= 0; i--) {
			Move current = moves.get(i);
			if (move.equals(current)) {
				return next;
			}
			next = Optional.of(current);
		}
		fail("Move " + move + " not in a list of moves: " + moves);
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Optional<Move> firstMove() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Optional<Move> lastMove() {
		return moves.isEmpty() ? Optional.empty() : Optional.of(moves.get(moves.size() - 1));
	}

	@Override
	@NonNull
	public Player nextPlayer() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public MakeMove move() {
		return Objects.requireNonNull(
				lastMove().map(moveWithPreviousFunction).orElseThrow(() -> new UnsupportedOperationException()));
	}

	@Override
	@NonNull
	public Stream<Move> stream() {
		return Objects.requireNonNull(new ArrayList<>(moves).stream());
	}

}
