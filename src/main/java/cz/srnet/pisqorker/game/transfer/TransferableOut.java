package cz.srnet.pisqorker.game.transfer;

import org.springframework.lang.NonNull;

public interface TransferableOut<T> {

	@NonNull
	T transferOut();

}
