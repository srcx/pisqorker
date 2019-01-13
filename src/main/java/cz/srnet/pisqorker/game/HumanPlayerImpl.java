package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.TransferablePlayer.PlayerType;
import cz.srnet.pisqorker.users.User;
import cz.srnet.pisqorker.users.Users;

final class HumanPlayerImpl implements HumanPlayer {

	private final @NonNull Piece piece;
	private final @NonNull User user;
	private final @NonNull Users users;

	public HumanPlayerImpl(@NonNull Piece piece, @NonNull User user, @NonNull Users users) {
		this.piece = piece;
		this.user = user;
		this.users = users;
	}

	@Override
	@NonNull
	public Piece piece() {
		return piece;
	}

	@Override
	@NonNull
	public TransferablePlayer transferOut() {
		TransferablePlayer out = new TransferablePlayer();
		out.setPiece(piece);
		out.setUser(user);
		out.setType(PlayerType.human);
		return out;
	}

	@Override
	@NonNull
	public User user() {
		return user;
	}

	@Override
	public boolean isPlayableNow() {
		return users.current().canPlayAs(this);
	}

}
