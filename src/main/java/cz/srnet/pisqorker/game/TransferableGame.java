package cz.srnet.pisqorker.game;

import java.util.List;

import org.springframework.lang.NonNull;

public final class TransferableGame {

	private final @NonNull String id;
	private final @NonNull Rules rules;
	private final @NonNull GameState state;
	private final @NonNull List<Coordinates> moves;
	private final @NonNull Players players;

	public TransferableGame(@NonNull String id, @NonNull Rules rules, @NonNull GameState state,
			@NonNull List<Coordinates> moves, @NonNull Players players) {
		this.id = id;
		this.rules = rules;
		this.state = state;
		this.moves = moves;
		this.players = players;
	}

	public @NonNull String getId() {
		return id;
	}

	public @NonNull Rules getRules() {
		return rules;
	}

	public @NonNull GameState getState() {
		return state;
	}

	public @NonNull List<Coordinates> getMoves() {
		return moves;
	}

	public @NonNull Players getPlayers() {
		return players;
	}

}
