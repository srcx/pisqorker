package cz.srnet.pisqorker;

import java.util.Objects;

import org.springframework.lang.NonNull;

final class GameImpl implements Game {

	private final @NonNull MovesRepository moves;

	public GameImpl(@NonNull MovesRepository moves) {
		this.moves = moves;
	}

	@Override
	@NonNull
	public Moves moves() {
		return moves;
	}

	@Override
	@NonNull
	public GameState state() {
		return Objects.requireNonNull(moves.lastMove().map(Move::state).orElse(GameState.notStarted));
	}

}
