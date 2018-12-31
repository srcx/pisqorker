package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.transfer.TransferableOut;

public interface Game extends TransferableOut<TransferableGame> {

	@NonNull
	Moves moves();

	@NonNull
	GameState state();

}
