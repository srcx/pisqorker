package cz.srnet.pisqorker.users;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.HumanPlayer;
import cz.srnet.pisqorker.rest.TransferableOut;

public interface User extends TransferableOut<TransferableUser> {

	@NonNull
	String id();

	boolean canPlayAs(@NonNull HumanPlayer player);

}
