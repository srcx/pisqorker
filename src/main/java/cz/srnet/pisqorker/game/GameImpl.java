package cz.srnet.pisqorker.game;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

final class GameImpl implements Game {

	private final @NonNull GameContext context;
	private final @NonNull MovesRepository moves;

	public GameImpl(@NonNull GameContext context, @NonNull MovesRepository moves) {
		this.context = context;
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

	@Override
	@NonNull
	public TransferableGame transferOut() {
		TransferableGame out = new TransferableGame();
		out.setRules(context.rules());
		out.setState(state());
		out.setMoves(moves.stream().map(Move::xy).collect(Collectors.toList()));
		return out;
	}

}
