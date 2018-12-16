package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

final class FakeMove implements Move {

	private @NonNull Optional<Player> player = Optional.empty();
	private @NonNull Optional<Coordinates> xy = Optional.empty();
	private @NonNull Optional<Supplier<Stream<Move>>> previousStreamSupplier = Optional.empty();
	private @NonNull Optional<GameState> state = Optional.empty();

	public @NonNull FakeMove _player(@NonNull Player player) {
		this.player = Optional.of(player);
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
	public Player player() {
		return Objects.requireNonNull(player.orElseThrow(() -> new UnsupportedOperationException()));
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
