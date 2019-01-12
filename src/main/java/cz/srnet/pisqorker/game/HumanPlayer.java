package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableOut;

public interface HumanPlayer extends Player, TransferableOut<TransferableHumanPlayer> {

	static @NonNull HumanPlayer create(@NonNull Piece piece) {
		return new HumanPlayerImpl(piece);
	}

}
