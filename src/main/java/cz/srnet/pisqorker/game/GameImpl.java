package cz.srnet.pisqorker.game;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

final class GameImpl implements Game {

	private final @NonNull String id;
	private final @NonNull GameContext context;
	private final @NonNull MovesRepository moves;
	private final @NonNull Players players;

	public GameImpl(@NonNull String id, @NonNull GameContext context, @NonNull MovesRepository moves,
			@NonNull Players players) {
		this.id = id;
		this.context = context;
		this.moves = moves;
		this.players = players;
	}

	@Override
	@NonNull
	public String id() {
		return id;
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
		out.setId(id());
		out.setRules(context.rules());
		out.setState(state());
		out.setMoves(moves.stream().map(Move::xy).collect(Collectors.toList()));
		out.setPlayers(players);
		return out;
	}

	@Override
	@NonNull
	public Players players() {
		return players;
	}

	@Override
	@NonNull
	public Player currentPlayer() {
		return players.with(moves().nextPiece());
	}

}
