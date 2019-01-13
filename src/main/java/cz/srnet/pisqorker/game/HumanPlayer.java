package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableOut;
import cz.srnet.pisqorker.users.User;
import cz.srnet.pisqorker.users.Users;

public interface HumanPlayer extends Player, TransferableOut<TransferablePlayer> {

	static @NonNull HumanPlayer create(@NonNull Piece piece, @NonNull User user, @NonNull Users users) {
		return new HumanPlayerImpl(piece, user, users);
	}

	@NonNull
	User user();

}
