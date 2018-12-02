package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

final class MoveImpl implements Move {

	private final @NonNull GameContext context;
	private final @NonNull Optional<Move> previous;
	private final int turn;
	private final @NonNull Player player;
	private final @NonNull Coordinates xy;
	private final @NonNull GameState state;

	public MoveImpl(@NonNull GameContext context, @NonNull Coordinates xy) {
		this(context, Optional.empty(), Player.defaultFirst(), xy);
	}

	public MoveImpl(@NonNull GameContext context, @NotNull Player firstPlayer, @NonNull Coordinates xy) {
		this(context, Optional.empty(), firstPlayer, xy);
	}

	private MoveImpl(@NonNull GameContext context, @NonNull Optional<Move> previous, @NonNull Coordinates xy) {
		this(context, previous, Player.defaultFirst(), xy);
	}

	private MoveImpl(@NonNull GameContext context, @NonNull Optional<Move> previous, @NotNull Player firstPlayer,
			@NonNull Coordinates xy) {
		checkIfValidState(previous);
		checkIfOutOfBounds(context.rules(), xy);
		checkIfOccupied(previous, xy);
		this.context = context;
		this.previous = previous;
		this.xy = xy;
		turn = previous().map(Move::turn).map(i -> i + 1).orElse(1);
		player = Objects.requireNonNull(previous().map(Move::player).map(Player::next).orElse(firstPlayer));
		state = determineState();
	}

	@Override
	@NonNull
	public GameState state() {
		return state;
	}

	private @NonNull GameState determineState() {
		if (isWon()) {
			return GameState.wonBy(player);
		}
		if (isDraw()) {
			return GameState.draw;
		}
		return GameState.started;
	}

	private boolean isDraw() {
		int boardSize = context.rules().boardSize();
		return turn == boardSize * boardSize;
	}

	private boolean isWon() {
		return context.winConditionChecker().isWon(this);
	}

	@Override
	@NonNull
	public Coordinates xy() {
		return xy;
	}

	@Override
	@NonNull
	public Optional<Move> previous() {
		return previous;
	}

	@Override
	@NonNull
	public Optional<Move> next() {
		return context.nextMoves().next(this);
	}

	@Override
	@NonNull
	public Move move(@NonNull Coordinates xy) {
		return new MoveImpl(context, Optional.of(this), xy);
	}

	@Override
	@NonNull
	public Move move(int x, int y) {
		return move(Coordinates.of(x, y));
	}

	@Override
	@NonNull
	public Move move(@NonNull Player player, @NonNull Coordinates xy) {
		if (player != nextPlayer()) {
			throw new IllegalArgumentException(
					"Attempting to play as " + player + ", but it is " + nextPlayer() + "'s turn");
		}
		return move(xy);
	}

	@Override
	@NonNull
	public Move move(@NonNull Player player, int x, int y) {
		return move(player, Coordinates.of(x, y));
	}

	@Override
	public int turn() {
		return turn;
	}

	@Override
	@NonNull
	public Player player() {
		return player;
	}

	@Override
	@NonNull
	public Player nextPlayer() {
		return player.next();
	}

	private static void checkIfOccupied(@NonNull Optional<Move> move, @NonNull Coordinates xy) {
		givenAndPreviousStream(move).forEach(m -> {
			if (m.xy().equals(xy)) {
				throw new IllegalArgumentException("Position " + xy + " is already occupied");
			}
		});
	}

	private static void checkIfOutOfBounds(@NonNull Rules rules, @NonNull Coordinates xy) {
		if (!rules.legalCoordinates(xy)) {
			throw new IllegalArgumentException("Position " + xy + " is out of bounds");
		}
	}

	private static void checkIfValidState(@NonNull Optional<Move> previous) {
		if (previous.map(Move::state).map(GameState::isEndState).orElse(false)) {
			throw new IllegalStateException("Game has already ended");
		}
	}

	@Override
	@NonNull
	public Stream<Move> previousStream() {
		return givenAndPreviousStream(previous());
	}

	private static @NonNull Stream<Move> givenAndPreviousStream(@NonNull Optional<Move> move) {
		Spliterator<Move> spliterator = new PreviousSpliterator(move);
		return Objects.requireNonNull(StreamSupport.stream(spliterator, false));
	}

	private static final class PreviousSpliterator implements Spliterator<Move> {

		private @NonNull Optional<Move> current;

		public PreviousSpliterator(@NonNull Optional<Move> current) {
			this.current = current;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Move> action) {
			if (current.isPresent()) {
				current.ifPresent(action);
				current = Objects.requireNonNull(current.map(Move::previous).orElse(Optional.empty()));
				return true;
			}
			return false;
		}

		@Override
		public Spliterator<Move> trySplit() {
			return null;
		}

		@Override
		public long estimateSize() {
			return current.map(Move::turn).orElse(0);
		}

		@Override
		public int characteristics() {
			return Spliterator.DISTINCT | Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED
					| Spliterator.SIZED | Spliterator.SUBSIZED;
		}
	}

}
