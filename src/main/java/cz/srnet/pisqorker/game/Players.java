package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableOut;

public interface Players extends TransferableOut<TransferablePlayers> {

	static @NonNull Players create(@NonNull Player first, @NonNull Player second) {
		return new PlayersImpl(first, second);
	}

	@NonNull
	Player first();

	@NonNull
	Player second();

	default @NonNull Player with(@NonNull Piece piece) {
		return first().piece() == piece ? first() : second();
	}

}
