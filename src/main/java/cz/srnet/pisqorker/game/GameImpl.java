package cz.srnet.pisqorker.game;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

final class GameImpl implements Game {

	private final @NonNull String id;
	private final @NonNull GameContext context;
	private final @NonNull MovesRepository moves;
	private final @NonNull Player firstPlayer;
	private final @NonNull Player secondPlayer;

	public GameImpl(@NonNull String id, @NonNull GameContext context, @NonNull MovesRepository moves,
			@NonNull Player firstPlayer, @NonNull Player secondPlayer) {
		this.id = id;
		this.context = context;
		this.moves = moves;
		if (firstPlayer.piece() == secondPlayer.piece()) {
			throw new IllegalArgumentException(
					"Players need to use different game pieces: " + firstPlayer + " vs " + secondPlayer);
		}
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
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
		out.setFirstPlayer(firstPlayer);
		out.setSecondPlayer(secondPlayer);
		return out;
	}

	@Override
	@NonNull
	public Player firstPlayer() {
		return firstPlayer;
	}

	@Override
	@NonNull
	public Player secondPlayer() {
		return secondPlayer;
	}

	private @NonNull Player playerWith(@NonNull Piece piece) {
		return firstPlayer.piece() == piece ? firstPlayer : secondPlayer;
	}

	@Override
	@NonNull
	public Player currentPlayer() {
		return playerWith(moves().nextPiece());
	}

}
