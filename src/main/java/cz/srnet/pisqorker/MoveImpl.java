package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.lang.NonNull;

final class MoveImpl implements Move {

	private final @NonNull GameContext context;
	private final @NonNull Optional<Move> previous;
	private final int turn;
	private final @NonNull Player player;
	private final int x;
	private final int y;
	private final @NonNull GameState state;

	public MoveImpl(@NonNull GameContext context, int x, int y) {
		this(context, Optional.empty(), x, y);
	}

	private MoveImpl(@NonNull GameContext context, @NonNull Optional<Move> previous, int x, int y) {
		checkIfOutOfBounds(context.rules(), x, y);
		checkIfOccupied(previous, x, y);
		this.context = context;
		this.previous = previous;
		this.x = x;
		this.y = y;
		turn = previous().map(Move::turn).map(i -> i + 1).orElse(1);
		player = Objects.requireNonNull(previous().map(Move::player).map(Player::next).orElse(Player.X));
		state = determineState();
	}

	@Override
	@NonNull
	public GameState state() {
		return state;
	}

	private @NonNull GameState determineState() {
		if (isDraw()) {
			return GameState.draw;
		}
		if (isWon()) {
			return GameState.wonBy(player);
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
	public int x() {
		return x;
	}

	@Override
	public int y() {
		return y;
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
	public Move move(int x, int y) {
		return new MoveImpl(context, Optional.of(this), x, y);
	}

	@Override
	@NonNull
	public Move move(@NonNull Player player, int x, int y) {
		if (player != nextPlayer()) {
			throw new IllegalArgumentException(
					"Attempting to play as " + player + ", but it is " + nextPlayer() + "'s turn");
		}
		return move(x, y);
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

	private static void checkIfOccupied(@NonNull Optional<Move> move, int x, int y) {
		givenAndPreviousStream(move).forEach(m -> {
			if (m.x() == x && m.y() == y) {
				throw new IllegalArgumentException("Position [" + x + ", " + y + "] is already occupied");
			}
		});
	}

	private static void checkIfOutOfBounds(@NonNull Rules rules, int x, int y) {
		if (!rules.legalCoordinates(x, y)) {
			throw new IllegalArgumentException("Position [" + x + ", " + y + "] is out of bounds");
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
