package cz.srnet.pisqorker;

import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;

final class MoveImpl implements Move {

	private final @NonNull Optional<Move> previous;
	private final @NonNull NextMoves nextMoves;
	private final int turn;
	private final @NonNull Player player;
	private final int x;
	private final int y;

	public MoveImpl(@NonNull NextMoves nextMoves, int x, int y) {
		this(Optional.empty(), nextMoves, x, y);
	}

	private MoveImpl(@NonNull Optional<Move> previous, @NonNull NextMoves nextMoves, int x, int y) {
		checkIfOccupied(previous, x, y);
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
		return GameState.started;
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
		return new MoveImpl(Optional.of(this), nextMoves, x, y);
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
		while (move.isPresent()) {
			if (move.map(m -> m.x() == x && m.y() == y).orElse(false)) {
				throw new IllegalArgumentException("Position [" + x + ", " + y + "] is already occupied");
			}
			move = Objects.requireNonNull(move.map(Move::previous).orElse(Optional.empty()));
		}
	}

}
