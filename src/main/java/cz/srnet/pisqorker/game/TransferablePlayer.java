package cz.srnet.pisqorker.game;

import java.util.Objects;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableIn;
import cz.srnet.pisqorker.users.User;
import cz.srnet.pisqorker.users.Users;

public final class TransferablePlayer implements TransferableIn<Player> {

	static enum PlayerType {
		human;
	}

	private Piece piece;
	private PlayerType type;
	private User user;

	private transient Users users;

	public void injectUsers(Users users) {
		this.users = users;
	}

	@Override
	@NonNull
	public Player transferIn() {
		// TODO better validation
		switch (type) {
		case human:
			return HumanPlayer.create(Objects.requireNonNull(piece), Objects.requireNonNull(user),
					Objects.requireNonNull(users));
		}
		throw new IllegalArgumentException("Missing player type");
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public PlayerType getType() {
		return type;
	}

	public void setType(PlayerType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
