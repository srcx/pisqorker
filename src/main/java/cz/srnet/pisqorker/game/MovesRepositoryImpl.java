package cz.srnet.pisqorker.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;

final class MovesRepositoryImpl implements MovesRepository {

	private final @NonNull GameContext context;
	private final @NonNull Piece firstPiece;

	private @NonNull Lock movesLock = new ReentrantLock();
	private @NonNull List<Move> moves = new ArrayList<>();

	public MovesRepositoryImpl(@NonNull GameContext context, @NonNull Piece firstPiece) {
		this.context = context;
		this.firstPiece = firstPiece;
	}

	@Override
	@NonNull
	public Piece nextPiece() {
		try {
			movesLock.lock();
			return moves.isEmpty() ? firstPiece : lastMoveNoLockNotNull().piece().other();
		} finally {
			movesLock.unlock();
		}
	}

	@Override
	@NonNull
	public Optional<Move> firstMove() {
		try {
			movesLock.lock();
			return moves.isEmpty() ? Optional.empty() : Optional.of(firstMoveNoLockNotNull());
		} finally {
			movesLock.unlock();
		}
	}

	private @NonNull Move firstMoveNoLockNotNull() {
		return Objects.requireNonNull(moves.get(0));
	}

	@Override
	@NonNull
	public Optional<Move> lastMove() {
		try {
			movesLock.lock();
			return moves.isEmpty() ? Optional.empty() : Optional.of(lastMoveNoLockNotNull());
		} finally {
			movesLock.unlock();
		}
	}

	private @NonNull Move lastMoveNoLockNotNull() {
		return Objects.requireNonNull(moves.get(moves.size() - 1));
	}

	@Override
	@NonNull
	public Optional<Move> previousMove(@NonNull Move move) {
		return moveAtTurn(move.turn() - 1);
	}

	@Override
	@NonNull
	public Optional<Move> nextMove(@NonNull Move move) {
		return moveAtTurn(move.turn() + 1);
	}

	private @NonNull Optional<Move> moveAtTurn(int turn) {
		try {
			movesLock.lock();
			if (turn >= 1 && turn <= moves.size()) {
				return Optional.of(moves.get(turn - 1));
			}
			return Optional.empty();
		} finally {
			movesLock.unlock();
		}
	}

	@Override
	@NonNull
	public MakeMove move() {
		return new MakeMove() {

			private @NonNull Optional<Piece> pieceToCheck = Optional.empty();

			@Override
			@NonNull
			public Move to(int x, int y) {
				return to(Coordinates.of(x, y));
			}

			@Override
			@NonNull
			public Move to(@NonNull Coordinates xy) {
				try {
					movesLock.lock();
					checkPlayer();
					Move move = new MoveImpl(context, MovesRepositoryImpl.this, firstPiece, xy);
					moves.add(move);
					return move;
				} finally {
					movesLock.unlock();
				}
			}

			private void checkPlayer() {
				pieceToCheck.ifPresent(piece -> {
					Piece nextPiece = nextPiece();
					if (piece != nextPiece) {
						throw new IllegalArgumentException(
								"Attempting to play piece " + piece + ", but next piece played must be " + nextPiece);
					}
				});
			}

			@Override
			@NonNull
			public MakeMove as(@NonNull Piece piece) {
				pieceToCheck = Optional.of(piece);
				return this;
			}
		};
	}

	@Override
	@NonNull
	public Stream<Move> stream() {
		movesLock.lock();
		try {
			return Objects.requireNonNull(new ArrayList<>(moves).stream());
		} finally {
			movesLock.unlock();
		}
	}

}
