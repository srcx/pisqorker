package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.lang.NonNull;

final class MoveImpl implements Move {

	private final @NonNull Rules rules;
	private final @NonNull Optional<Move> previous;
	private final @NonNull NextMoves nextMoves;
	private final int turn;
	private final @NonNull Player player;
	private final int x;
	private final int y;

	public MoveImpl(@NonNull Rules rules, @NonNull NextMoves nextMoves, int x, int y) {
		this(rules, Optional.empty(), nextMoves, x, y);
	}

	private MoveImpl(@NonNull Rules rules, @NonNull Optional<Move> previous, @NonNull NextMoves nextMoves, int x,
			int y) {
		checkIfOutOfBounds(rules, x, y);
		checkIfOccupied(previous, x, y);
		this.rules = rules;
		this.previous = previous;
		this.nextMoves = nextMoves;
		this.x = x;
		this.y = y;
		turn = previous().map(Move::turn).map(i -> i + 1).orElse(1);
		player = Objects.requireNonNull(previous().map(Move::player).map(Player::next).orElse(Player.X));
	}

	@Override
	@NonNull
	public GameState state() {
		if (isDraw()) {
			return GameState.draw;
		}
		return GameState.started;
	}

	private boolean isDraw() {
		return turn == rules.boardSize() * rules.boardSize();
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
		return nextMoves.next(this);
	}

	@Override
	@NonNull
	public Move move(int x, int y) {
		return new MoveImpl(rules, Optional.of(this), nextMoves, x, y);
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
		forGivenMoveAndEachPrevious(move, m -> {
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
	public void forEachPrevious(@NonNull Consumer<Move> action) {
		forGivenMoveAndEachPrevious(previous(), action);
	}

	private static void forGivenMoveAndEachPrevious(@NonNull Optional<Move> move, @NonNull Consumer<Move> action) {
		while (move.isPresent()) {
			move.ifPresent(action);
			move = Objects.requireNonNull(move.map(Move::previous).orElse(Optional.empty()));
		}
	}

}
