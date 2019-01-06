package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.rest.TransferableOut;

public interface Game extends TransferableOut<TransferableGame> {

	@NonNull
	String id();

	@NonNull
	Moves moves();

	@NonNull
	GameState state();

}
