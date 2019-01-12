package cz.srnet.pisqorker.game;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Coordinates;
import cz.srnet.pisqorker.game.GameState;
import cz.srnet.pisqorker.game.MakeMove;
import cz.srnet.pisqorker.game.Move;
import cz.srnet.pisqorker.game.Piece;

final class FakeMove implements Move {

	private @NonNull Optional<Piece> piece = Optional.empty();
	private @NonNull Optional<Coordinates> xy = Optional.empty();
	private @NonNull Optional<Supplier<Stream<Move>>> previousStreamSupplier = Optional.empty();
	private @NonNull Optional<GameState> state = Optional.empty();

	public @NonNull FakeMove _piece(@NonNull Piece piece) {
		this.piece = Optional.of(piece);
		return this;
	}

	public @NonNull FakeMove _xy(@NonNull Coordinates xy) {
		this.xy = Optional.of(xy);
		return this;
	}

	public @NonNull FakeMove _previousStreamSupplier(@NonNull Supplier<Stream<Move>> previousStreamSupplier) {
		this.previousStreamSupplier = Optional.of(previousStreamSupplier);
		return this;
	}

	public @NonNull FakeMove _state(@NonNull GameState state) {
		this.state = Optional.of(state);
		return this;
	}

	@Override
	public int turn() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public GameState state() {
		return Objects.requireNonNull(state.orElseThrow(() -> new UnsupportedOperationException()));
	}

	@Override
	@NonNull
	public Piece piece() {
		return Objects.requireNonNull(piece.orElseThrow(() -> new UnsupportedOperationException()));
	}

	@Override
	@NonNull
	public Coordinates xy() {
		return Objects.requireNonNull(xy.orElseThrow(() -> new UnsupportedOperationException()));
	}

	@Override
	@NonNull
	public Optional<Move> previous() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Optional<Move> next() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Stream<Move> previousStream() {
		return Objects
				.requireNonNull(previousStreamSupplier.orElseThrow(() -> new UnsupportedOperationException()).get());
	}

	@Override
	@NonNull
	public MakeMove move() {
		throw new UnsupportedOperationException();
	}

}
