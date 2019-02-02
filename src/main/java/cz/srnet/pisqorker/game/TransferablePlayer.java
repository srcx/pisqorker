package cz.srnet.pisqorker.game;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srnet.pisqorker.rest.TransferableIn;
import cz.srnet.pisqorker.users.User;
import cz.srnet.pisqorker.users.Users;

public final class TransferablePlayer implements TransferableIn<Player> {

	@Autowired
	private @NonNull Users users;

	static enum PlayerType {
		human;
	}

	private final @NonNull Piece piece;
	private final @NonNull PlayerType type;
	private final @NonNull Optional<User> user;

	@JsonCreator
	public TransferablePlayer(@JsonProperty(value = "piece", required = true) @NonNull Piece piece,
			@JsonProperty(value = "type", required = true) @NonNull PlayerType type,
			@JsonProperty(value = "user", required = false) @Nullable User user) {
		this.piece = piece;
		this.type = type;
		this.user = Optional.ofNullable(user);
	}

	@Override
	@NonNull
	public Player transferIn() {
		switch (type) {
		case human:
			return HumanPlayer.create(piece, user.get(), users);
		}
		throw new IllegalArgumentException("Missing player type");
	}

	public @NonNull Piece getPiece() {
		return piece;
	}

	public @NonNull PlayerType getType() {
		return type;
	}

	public @Nullable User getUser() {
		return user.orElse(null);
	}

}
