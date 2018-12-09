package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

final class FakeMove implements Move {

	private final @NonNull Player player;
	private final @NonNull Coordinates xy;
	private final @NonNull Supplier<Stream<Move>> previousStreamSupplier;

	public FakeMove(@NonNull Player player, @NonNull Coordinates xy,
			@NonNull Supplier<Stream<Move>> previousStreamSupplier) {
		this.player = player;
		this.xy = xy;
		this.previousStreamSupplier = previousStreamSupplier;
	}

	@Override
	public int turn() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public GameState state() {
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Player player() {
		return player;
	}

	@Override
	@NonNull
	public Coordinates xy() {
		return xy;
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
		return Objects.requireNonNull(previousStreamSupplier.get());
	}

	@Override
	@NonNull
	public MakeMove move() {
		throw new UnsupportedOperationException();
	}

}
